package com.au664966.coronatracker.utility;

/**
 * Error codes used to decouple the repository from the resources and strings
 * This gives us a common way to know the different type of errors in the view
 */
public enum ErrorCodes {
    NO_EXIST,
    ALREADY_EXISTS,
    DELETE_ERROR,
    NETWORK_ERROR,
    INITIALIZING_ERROR
}
