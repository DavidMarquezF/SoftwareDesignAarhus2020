package com.au664966.coronatracker.utility;

import android.content.Context;

public class CountryCodeToResourceId {
    /**
     * Obtains the resource id from the country code
     *
     * Since all country codes map directly to the drawable names, obtaining it is pretty straight
     * forward
     * @param c The context
     * @param code Country cod
     * @return The resource identifier for the flag drawable
     */
    public static int convert(Context c, String code){
        return c.getResources().getIdentifier(code.toLowerCase(), "drawable", c.getPackageName());
    }
}
