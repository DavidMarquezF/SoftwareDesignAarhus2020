package com.au664966.coronatracker.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.au664966.coronatracker.model.Country;
import com.au664966.coronatracker.model.Repository;
import com.au664966.coronatracker.utility.Constants;

public class DetailsViewModel extends AndroidViewModel {
    private LiveData<Country> country;
    private Repository repository;

    public LiveData<Country> getCountry(){
        return country;
    }

    public DetailsViewModel(@NonNull Application application, @NonNull SavedStateHandle savedStateHandle){
        super(application);
        repository = Repository.getInstance(application);

        // A way to obtain the Intent data from the ViewModel
        // https://stackoverflow.com/a/63082928
        country = Transformations.switchMap(savedStateHandle.<String>getLiveData(Constants.EXTRA_COUNTRY_CODE), new Function<String, LiveData<Country>>() {
            @Override
            public LiveData<Country> apply(String code) {
                return repository.getCountryByCode(code);
            }
        });
    }

    public void deleteCountry(Repository.StatusCallback callback){
        repository.deleteCountry(country.getValue(), callback);
    }



}
