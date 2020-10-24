package com.au664966.coronatracker.model;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.au664966.coronatracker.database.CountryDAO;
import com.au664966.coronatracker.database.CountryDatabase;
import com.au664966.coronatracker.model.covid19api.CountryAPI;
import com.au664966.coronatracker.utility.ErrorCodes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

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
    private LiveData<List<Country>> countries;
    private CountryDAO _countryDao;
    private RequestQueue queue;


    private Repository(Application app) {

        // There's no need to expose the entire database to the repository so we just expose the DAO
        CountryDatabase db = CountryDatabase.getDatabase(app.getApplicationContext());
        _countryDao = db.countryDAO();
        queue = Volley.newRequestQueue(app);
        countries = _countryDao.getAll();
    }

    public static Repository getInstance(Application app) {
        if (instance == null) {
            instance = new Repository(app);
        }
        return instance;
    }

    public LiveData<List<Country>> getCountries() {
        return countries;
    }

    interface CountriesResponseCallback {
        void callback(CountryAPI response);
    }

    public void updateCountries() {
        this.getAllCountries(new CountriesResponseCallback() {
            @Override
            public void callback(CountryAPI response) {
                for (com.au664966.coronatracker.model.covid19api.Country country : response.getCountries()) {
                    //addCountry(new Country(country.getCountry(), country.getCountryCode(), country.getTotalConfirmed(), country.getTotalDeaths()));
                }
            }
        });
    }




    private void getAllCountries(final CountriesResponseCallback callback) {

        final String url = "https://api.covid19api.com/summary";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new GsonBuilder().create();
                CountryAPI res = gson.fromJson(response, CountryAPI.class);
                callback.callback(res);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.getMessage());
            }
        });
        queue.add(stringRequest);

    }

    /* //TODO: Delete this comment
        private void loadCountries() {
            List<Country> countries = new ArrayList<>();
            countries.add(new Country("Canada", "CA", 142866, 9248));
            countries.add(new Country("China", "CN", 90294, 4736));
            countries.add(new Country("Denmark", "DK", 21836, 635));
            countries.add(new Country("Germany", "DE", 269048, 9376));
            countries.add(new Country("Finland", "FI", 8799, 339));
            countries.add(new Country("India", "IN", 5118253, 83198));
            countries.add(new Country("Japan", "JP", 77488, 1490));
            countries.add(new Country("Norway", "NO", 12644, 266));
            countries.add(new Country("Russian", "RU", 1081152, 18996));
            countries.add(new Country("Sweden", "SE", 87885, 5864));
            countries.add(new Country("USA", "US", 6674411, 197633));
            this.countries.setValue(countries);
        }
    */

    // Room executes all queries on a separate thread, so we don't need to handle queries as
    // async tasks
    public LiveData<Country> getCountryByCode(final String code) {
        return this._countryDao.findCountry(code);
    }

    // Operations like inserting, updating and updating are executed on the same thread so we need
    // To separate them
    public void addCountry(final Country country, final StatusCallback callback) {
        CountryDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    _countryDao.addCountry(country);
                    callback.success();
                }
                catch (Exception ex){
                    Log.e(TAG, "run: Add country", ex);
                    callback.error(ErrorCodes.ALREADY_EXISTS);
                }
            }
        });

    }

    public void deleteCountry(final Country country) {
        CountryDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                _countryDao.deleteCountry(country);
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

    public interface StatusCallback {
        void success();
        void error(ErrorCodes code);
    }

    public interface LoadingStatusCallback extends StatusCallback{
        void loading();
    }

    public void findCountry(final String name, final LoadingStatusCallback callback) {
        callback.loading();
        this.getAllCountries(new CountriesResponseCallback() {
            @Override
            public void callback(CountryAPI response) {
                for(com.au664966.coronatracker.model.covid19api.Country c : response.getCountries()){
                    if(c.getCountry().toLowerCase().equals(name.toLowerCase())){
                        addCountry(countryFromAPI(c), callback); //TODO: Check if the country is already
                        return;
                    }
                }
                callback.error(ErrorCodes.NO_EXIST);
            }
        });
    }

    private Country countryFromAPI(com.au664966.coronatracker.model.covid19api.Country country){
        return new Country(country.getCountry(), country.getCountryCode(), country.getTotalConfirmed(), country.getTotalDeaths());
    }

    //Threads: https://www.codejava.net/java-core/concurrency/java-concurrency-understanding-thread-pool-and-executors
    // https://medium.com/@frank.tan/using-a-thread-pool-in-android-e3c88f59d07f
    //HAndlig live data state: https://medium.com/androidxx/propagate-data-and-state-using-mediatorlivedata-7ea25582fa29
}
