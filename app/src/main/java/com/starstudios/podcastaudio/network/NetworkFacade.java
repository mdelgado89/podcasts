package com.starstudios.podcastaudio.network;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by delgadem on 11/14/14.
 */
public enum NetworkFacade {
    INSTANCE;

    private RequestQueue mRequestQueue;
    private NetworkFacade() {
    }

    public void initializeRequestQueue(Context context) {
        //Create network facade
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public void makeRequest(int method, @NonNull String url, @Nullable final Response.Listener listener, @Nullable Response.ErrorListener errorListener) {
        mRequestQueue.add(new StringRequest(method, url, listener, errorListener) {
            @Override
            protected void deliverResponse(String response) {
                super.deliverResponse(response);
                listener.onResponse(response);
            }
        });
    }

    public void makeGetRequest(@NonNull String url, @Nullable final Response.Listener listener, @Nullable Response.ErrorListener errorListener) {
        mRequestQueue.add(new StringRequest(Request.Method.GET, url, listener, errorListener) {
            @Override
            protected void deliverResponse(String response) {
                super.deliverResponse(response);
                listener.onResponse(response);
            }
        });
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}
