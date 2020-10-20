package com.au664966.coronatracker.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity()
public class Country {
    private String name;

    @PrimaryKey()
    @NonNull()
    private String code;

    private int cases;
    private int deaths;

    private Float rating;
    private String notes;

    @Ignore
    public Country(String name, String code, int cases, int deaths) {
        this(name, code, cases, deaths, null, null);
    }
    public Country(String name, String code, int cases, int deaths, Float rating, String notes) {
        this.name = name;
        this.code = code;
        this.cases = cases;
        this.deaths = deaths;
        this.rating = rating;
        this.notes = notes;
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
}