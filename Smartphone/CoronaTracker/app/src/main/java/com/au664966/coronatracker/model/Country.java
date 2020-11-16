package com.au664966.coronatracker.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity()
public class Country {

    private String name;

    // The decision of using the code as the db primary code and not an id comes from the fact
    // that all the countries need to be identified by a code. No two countries will have the same
    // code and even if the API had a problem we shouldn't accept it at all. The only valid way
    // to check if two countries are different is by comparing their code so it makes sense that the
    // code is used as the identifier of each entry
    @PrimaryKey()
    @NonNull()
    private String code;

    private int cases;
    private int deaths;

    @ColumnInfo(name = "new_confirmed")
    private int newCases;

    @ColumnInfo(name ="new_deaths")
    private int newDeaths;

    private Float rating;
    private String notes;

    @Ignore
    public Country(String name, String code, int cases, int deaths, int newCases, int newDeaths) {
        this(name, code, cases, deaths, newCases, newDeaths, null, null);
    }
    public Country(String name, String code, int cases, int deaths, int newCases, int newDeaths, Float rating, String notes) {
        this.name = name;
        this.code = code;
        this.cases = cases;
        this.deaths = deaths;
        this.rating = rating;
        this.notes = notes;
        this.newCases = newCases;
        this.newDeaths = newDeaths;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCases() {
        return cases;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getNewCases() {
        return newCases;
    }

    public void setNewCases(int newCases) {
        this.newCases = newCases;
    }

    public int getNewDeaths() {
        return newDeaths;
    }

    public void setNewDeaths(int newDeaths) {
        this.newDeaths = newDeaths;
    }
}