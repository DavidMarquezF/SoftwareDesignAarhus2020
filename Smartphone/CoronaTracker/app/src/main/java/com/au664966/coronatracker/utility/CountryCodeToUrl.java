package com.au664966.coronatracker.utility;

import android.content.Context;

public class CountryCodeToUrl {
    /**
     * Obtains the url of the flag image from the country code
     *
     * @param code Country code
     * @return The url to get the flag image
     */
    public static String convert(String code){
        return "https://www.countryflags.io/" + code + "/flat/64.png" ;
    }
}
