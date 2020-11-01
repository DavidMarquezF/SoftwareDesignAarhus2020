package com.au664966.coronatracker.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.au664966.coronatracker.model.Country;

import java.util.List;

@Dao
public interface CountryDAO {
    @Query("SELECT * FROM country")
    LiveData<List<Country>> getAll();

    @Query("SELECT * FROM country WHERE code LIKE :code")
    LiveData<Country> findCountry(String code);

    @Query("UPDATE country SET notes = :notes, rating = :rating WHERE code LIKE :code")
    void updateCountry(String code, String notes, Float rating);


    @Insert(onConflict = OnConflictStrategy.ABORT)
    void addCountry(Country country);

    @Delete
    void deleteCountry(Country country);

    @Update
    void updateCountry(Country country);


}
