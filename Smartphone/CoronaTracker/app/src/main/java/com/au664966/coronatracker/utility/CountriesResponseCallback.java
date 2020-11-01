package com.au664966.coronatracker.utility;

import com.au664966.coronatracker.model.covid19api.CountryAPI;

public interface CountriesResponseCallback {
    void success(CountryAPI response);
    void error(Exception error);
}
