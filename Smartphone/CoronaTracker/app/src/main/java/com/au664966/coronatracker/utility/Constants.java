package com.au664966.coronatracker.utility;

import com.au664966.coronatracker.R;
import com.bumptech.glide.request.RequestOptions;

public class Constants {
    public static final String EXTRA_COUNTRY_CODE = "EXTRA_COUNTRY_CODE";
    public static final String EXTRA_NOTES = "EXTRA_NOTES";
    public static final String EXTRA_RATING = "EXTRA_RATING";
    private static RequestOptions flagDefaultOptions;

    public static RequestOptions getFlagDefualtOptions() {
        if (Constants.flagDefaultOptions == null)
            flagDefaultOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_baseline_outlined_flag_24)
                    .error(R.drawable.ic_baseline_outlined_flag_24);
        return flagDefaultOptions;
    }

}
