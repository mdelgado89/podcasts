package com.starstudios.podcastaudio.utils;

import android.os.Bundle;

import java.io.Serializable;

/**
 * Created by delgadem on 11/17/14.
 */
public class BundleBuilder {

    private Bundle mBundle;
    public BundleBuilder() {
        mBundle = new Bundle();
    }

    public BundleBuilder putInt(String key, int value) {
        mBundle.putInt(key, value);

        return this;
    }

    public BundleBuilder putString(String key, String value) {
        mBundle.putString(key, value);

        return this;
    }

    public BundleBuilder putSerializable(String key, Serializable value) {
        mBundle.putSerializable(key, value);

        return this;
    }

    public Bundle build() {
        return mBundle;
    }
}
