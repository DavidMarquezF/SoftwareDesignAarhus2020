package com.au664966.coronatracker.utility;

import com.au664966.coronatracker.R;
import com.au664966.coronatracker.model.Repository;

/**
 * This class is used so that we can get the correct string resource from the code
 * It has been done like this because the repository shouldn't even know the existence of these strings
 * and its certainly not its job to handle them. Instead it just has a set of error codes that it can send
 */
public class ErrorCodeToResourceId {
    public static int convert(ErrorCodes error_code){
        switch (error_code){
            case NO_EXIST:
                return R.string.error_cant_find;
            case ALREADY_EXISTS:
                return R.string.error_country_exists;
            case DELETE_ERROR:
                return R.string.error_delete;
            case NETWORK_ERROR:
                return R.string.error_network;
            case INITIALIZING_ERROR:
                return R.string.error_initializing;
            default:
                return -1;
        }
    }
}
