package com.au664966.coronatracker.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.au664966.coronatracker.model.Country;
import com.au664966.coronatracker.model.Repository;

import java.util.List;

public class ListViewModel extends ViewModel {


    public LiveData<List<Country>> getCountries(){
        return Repository.getInstance().getCountries();
    }



}
