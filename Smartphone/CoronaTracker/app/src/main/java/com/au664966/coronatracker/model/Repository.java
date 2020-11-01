package com.au664966.coronatracker.model;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.au664966.coronatracker.database.CountryDAO;
import com.au664966.coronatracker.database.CountryDatabase;
import com.au664966.coronatracker.model.covid19api.CountryAPI;
import com.au664966.coronatracker.service.WebService;
import com.au664966.coronatracker.utility.CountriesResponseCallback;
import com.au664966.coronatracker.utility.ErrorCodes;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Implemented Repository pattern so that when we need to add a database or access to the cloud the
 * rest of the app will not be affected, since they will keep using the same functions from the
 * repository.
 * <p>
 * This pattern is recommended by the "Guide to app architecture"
 * https://developer.android.com/jetpack/guide
 */
public class Repository {

    private static final String TAG = "Repository";
    /**
     * Singleton pattern implemented following
     * https://code.tutsplus.com/tutorials/android-design-patterns-the-singleton-pattern--cms-
     * <p>
     * This way we ensure that only one instance of the Repository exists during runtime
     */
    private static Repository instance;
    private final LiveData<List<Country>> countries;
    private final MutableLiveData<InitializingStatus> _initializingDatabase;
    private final CountryDAO _countryDao;
    private final WebService _webService;

    public enum InitializingStatus {
        SUCCESS_OPEN,
        SUCCESS_INIT,
        ERROR,
        LOADING,
        FINALIZE
    }

    public LiveData<InitializingStatus> getInitializingDatabase() {
        return _initializingDatabase;
    }

    private Repository(Application app) {
        _initializingDatabase = new MutableLiveData<>(InitializingStatus.LOADING);
        _webService = new WebService(app);


        final Handler mainHandler = new Handler(app.getMainLooper());
        // Since OnCreateDatabase is called on another thread, we cannot assure that _countryDao
        // will have a value, thus we use a lock to make sure that _countryDao has a correct value
        final ReentrantLock lock = new ReentrantLock();
        lock.lock();
        // There's no need to expose the entire database to the repository so we just expose the DAO
        CountryDatabase db = CountryDatabase.getDatabase(app.getApplicationContext(), new CountryDatabase.InitializeCallback() {
            private boolean isInitializing = false;

            @Override
            public void OnCreateDatabase() {
                isInitializing = true;
                lock.lock();
                addDefaultCountries();
            }

            @Override
            public void OnOpenDatabase() {
                if (!isInitializing)
                    setInitializationDone(InitializingStatus.SUCCESS_OPEN);
            }

            private void setInitializationDone(final InitializingStatus status) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        _initializingDatabase.setValue(status);
                        _initializingDatabase.setValue(InitializingStatus.FINALIZE);
                    }
                });
            }
        });

        _countryDao = db.countryDAO();
        countries = _countryDao.getAll();
        lock.unlock();
    }

    public static Repository getInstance(Application app) {
        if (instance == null) {
            instance = new Repository(app);
        }
        return instance;
    }

    public void addDefaultCountries(){
        final List<String> codes = Arrays.asList("CA", "CN", "DK", "DE", "FI", "IN", "JP", "NO", "RU", "SE", "US");

        final Handler mainHandler = new Handler(Looper.getMainLooper());

        findAndAddCountriesByCode(codes, new StatusCallback() {
            @Override
            public void success() {
                setInitializationDone(InitializingStatus.SUCCESS_INIT);
            }

            @Override
            public void error(ErrorCodes code) {
                setInitializationDone(InitializingStatus.ERROR);
            }

            private void setInitializationDone(final InitializingStatus status){
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        _initializingDatabase.setValue(status);
                        _initializingDatabase.setValue(InitializingStatus.FINALIZE);
                    }
                });
            }
        });
    }

    public LiveData<List<Country>> getCountries() {
        return countries;
    }

    public void updateCountries() {
        // It doesn't make sense to waste bandwidth if we are not going to be able to update any country
        if (countries.getValue() == null || countries.getValue().size() <= 0)
            return;

        this._webService.getAllCountriesSummaries(new CountriesResponseCallback() {
            @Override
            public void success(CountryAPI response) {
                if (countries.getValue() == null || response.getCountries() == null)
                    return;
                for (Country userCountry : countries.getValue()) {
                    for (com.au664966.coronatracker.model.covid19api.Country country : response.getCountries()) {
                        if (userCountry.getCode().equals(country.getCountryCode())) {
                            Country newCountry = countryFromAPI(country);
                            newCountry.setRating(userCountry.getRating());
                            newCountry.setNotes(userCountry.getNotes());
                            updateCountry(newCountry);
                        }
                    }
                }

            }

            @Override
            public void error(Exception error) {
                Log.e(TAG, "error: ", error);
            }
        });
    }

    // Room executes all queries on a separate thread, so we don't need to handle queries as
    // async tasks
    public LiveData<Country> getCountryByCode(final String code) {
        return this._countryDao.findCountry(code);
    }

    // Operations like inserting, updating and updating are executed on the same thread so we need
    // to separate them
    public void addCountry(final Country country, final @Nullable StatusCallback callback) {
        CountryDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    _countryDao.addCountry(country);
                } catch (Exception ex) {
                    Log.e(TAG, "run: Add country", ex);
                    if (callback != null) {
                        callback.error(ErrorCodes.ALREADY_EXISTS);
                    }
                    return;
                }
                if (callback != null) {
                    callback.success();
                }
            }
        });

    }


    public void deleteCountry(final Country country, final StatusCallback callback) {
        CountryDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    _countryDao.deleteCountry(country);
                } catch (Exception ex) {
                    callback.error(ErrorCodes.DELETE_ERROR);
                }
                callback.success();
            }
        });
    }

    public void updateCountry(final Country country) {
        CountryDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                _countryDao.updateCountry(country);
            }
        });
    }

    public void updateCountryUserData(final String code, final String notes, final Float rating) {
        CountryDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                _countryDao.updateCountry(code, notes, rating);
            }
        });
    }

    public interface StatusCallback {
        void success();

        void error(ErrorCodes code);
    }

    public interface LoadingStatusCallback extends StatusCallback {
        void loading();
    }

    public void findAndAddCountryByName(final String name, final LoadingStatusCallback callback) {
        callback.loading();
        this._webService.getAllCountriesSummaries(new CountriesResponseCallback() {
            @Override
            public void success(CountryAPI response) {
                for (com.au664966.coronatracker.model.covid19api.Country c : response.getCountries()) {
                    if (c.getCountry().toLowerCase().equals(name.toLowerCase())) {
                        addCountry(countryFromAPI(c), callback); //TODO: Check if the country is already
                        return;
                    }
                }
                callback.error(ErrorCodes.NO_EXIST);
            }

            @Override
            public void error(Exception error) {
                callback.error(ErrorCodes.NETWORK_ERROR);
            }
        });
    }

    public void findAndAddCountriesByCode(final List<String> codes, final StatusCallback callback) {
        _webService.getAllCountriesSummaries(new CountriesResponseCallback() {
            @Override
            public void success(CountryAPI response) {
                for (com.au664966.coronatracker.model.covid19api.Country c : response.getCountries()) {
                    String countryCode = c.getCountryCode().toLowerCase();
                    for (String code : codes) {
                        if (countryCode.equals(code.toLowerCase())) {
                            addCountry(countryFromAPI(c), null); //TODO: Check if the country is already
                        }
                    }
                }
                callback.success();
            }

            @Override
            public void error(Exception error) {
                callback.error(ErrorCodes.NETWORK_ERROR);
            }

        });
    }


    private Country countryFromAPI(com.au664966.coronatracker.model.covid19api.Country country) {
        return new Country(country.getCountry(), country.getCountryCode(), country.getTotalConfirmed(), country.getTotalDeaths(), country.getNewConfirmed(), country.getNewDeaths());
    }

    //Threads: https://www.codejava.net/java-core/concurrency/java-concurrency-understanding-thread-pool-and-executors
    // https://medium.com/@frank.tan/using-a-thread-pool-in-android-e3c88f59d07f
    //HAndlig live data state: https://medium.com/androidxx/propagate-data-and-state-using-mediatorlivedata-7ea25582fa29
}
