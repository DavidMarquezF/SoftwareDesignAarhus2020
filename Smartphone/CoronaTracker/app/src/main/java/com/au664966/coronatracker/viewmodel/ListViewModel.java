package com.au664966.coronatracker.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.au664966.coronatracker.model.Country;
import com.au664966.coronatracker.model.Repository;

import java.util.List;

public class ListViewModel extends AndroidViewModel {
    private final Repository repository;

    public ListViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
    }

    public LiveData<List<Country>> getCountries() {
        return repository.getCountries();
    }

    public LiveData<Repository.InitializingStatus> getInitializingDatabse() {
        return repository.getInitializingDatabase();
    }

    public void addCountry(String name, Repository.LoadingStatusCallback callback){
        repository.findAndAddCountryByName(name, callback);
    }

    public void addDefaultCountries(){
        repository.addDefaultCountries();
    }
}
