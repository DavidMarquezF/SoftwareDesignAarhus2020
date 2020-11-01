package com.au664966.coronatracker.service;

import android.app.Application;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.au664966.coronatracker.model.covid19api.CountryAPI;
import com.au664966.coronatracker.utility.CountriesResponseCallback;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WebService {
    private static final String TAG = "WebService";
    private final RequestQueue queue;
    final String URL = "https://api.covid19api.com/summary";

    public WebService(Application app){
        queue = Volley.newRequestQueue(app);
    }

    public void getAllCountriesSummaries(final CountriesResponseCallback callback) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new GsonBuilder().create();
                CountryAPI res = gson.fromJson(response, CountryAPI.class);
                callback.success(res);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.getMessage());
                callback.error(error);
            }
        });
        queue.add(stringRequest);

    }
}
