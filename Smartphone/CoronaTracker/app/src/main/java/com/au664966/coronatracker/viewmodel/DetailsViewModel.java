package com.au664966.coronatracker.viewmodel;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.au664966.coronatracker.model.Country;
import com.au664966.coronatracker.model.Repository;
import com.au664966.coronatracker.utility.Constants;

public class DetailsViewModel extends ViewModel {
    private LiveData<Country> country;
    private Repository repository;

    public LiveData<Country> getCountry(){
        return country;
    }

    public DetailsViewModel(@NonNull SavedStateHandle savedStateHandle){
        repository = Repository.getInstance();

        // A way to obtain the Intent data from the ViewModel
        // https://stackoverflow.com/a/63082928
        country = Transformations.switchMap(savedStateHandle.<String>getLiveData(Constants.EXTRA_COUNTRY_CODE), new Function<String, LiveData<Country>>() {
            @Override
            public LiveData<Country> apply(String code) {
                return repository.getCountryByCode(code);
            }
        });
    }


}
