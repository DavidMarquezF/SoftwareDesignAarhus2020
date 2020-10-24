package com.au664966.coronatracker.utility;

import com.au664966.coronatracker.R;
import com.au664966.coronatracker.model.Repository;

public class ErrorCodeToResourceId {
    public static int convert(ErrorCodes error_code){
        switch (error_code){
            case NO_EXIST:
                return R.string.error_cant_find;
            case ALREADY_EXISTS:
                return R.string.error_country_exists;
            default:
                return -1;
        }
    }
}
