package com.au664966.coronatracker.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.au664966.coronatracker.model.Country;
import com.au664966.coronatracker.model.Repository;

import java.util.List;

public class ListViewModel extends AndroidViewModel {
    private LiveData<List<Country>> countries;
    private Repository repository;

    public ListViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
        countries = repository.getCountries();
    }

    public LiveData<List<Country>> getCountries() {
        return countries;
    }


}
