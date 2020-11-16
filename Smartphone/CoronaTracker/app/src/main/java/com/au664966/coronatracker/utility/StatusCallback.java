package com.au664966.coronatracker.utility;


/**
 * Basic callback to get the result of an operation
 */
public interface StatusCallback {
    void success();

    void error(ErrorCodes code);
}
