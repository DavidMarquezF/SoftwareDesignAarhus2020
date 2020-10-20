package com.au664966.coronatracker.model;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
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
    /**
     * Singleton pattern implemented following
     * https://code.tutsplus.com/tutorials/android-design-patterns-the-singleton-pattern--cms-
     * <p>
     * This way we ensure that only one instance of the Repository exists during runtime
     */
    private static Repository instance;
    private MutableLiveData<List<Country>> countries;


    private Repository() {
    }

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    public LiveData<List<Country>> getCountries() {
        if (countries == null) {
            countries = new MutableLiveData<>();
            loadCountries();
        }

        return countries;
    }

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

    public LiveData<Country> getCountryByCode(final String code) {
        /*
        This is a workaround to get the "Database behavior". When we modify an item we will update
        The whole LiveData list and thus the observers of this will get notified. Even though this
        is not really optimal, when the database is introduced in the next assignment, we will directly
        obtain the LiveData object from the DAO. All this work is done so that I will have to do minimum
        changes to get the database working in the next assignment.
         */
        return Transformations.map(countries, new Function<List<Country>, Country>() {
            @Override
            public Country apply(List<Country> count) {
                for (int i = 0; i < count.size(); i++) {
                    if (count.get(i).getCode().equals(code)) {
                        return count.get(i);
                    }
                }
                return null;
            }
        });

    }

    /*
    This function will be removed in the next assignment when we introduce the database, since updating
    the items can be done directly from the ViewModel. I do it here so that we can notify the modification
    of the list and the live data can be updated.
     */
    public void updateCountry(Country c, Float rating, String notes){
        c.setNotes(notes);
        c.setRating(rating);
        countries.setValue(countries.getValue());
    }
}
