package com.starstudios.podcastaudio;

import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starstudios.podcastaudio.network.BitmapLruCache;
import com.starstudios.podcastaudio.network.NetworkFacade;

import java.io.IOException;

/**
 * Created by delgadem on 11/14/14.
 */
public class PodcastAudioApplication extends Application {
    private ImageLoader mImageLoader;
    private BitmapLruCache mBitmapLruCache;
    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        mBitmapLruCache = new BitmapLruCache();
//        String startupUrl = getString(R.string.startup);

        //Initialize request queue
        NetworkFacade.INSTANCE.initializeRequestQueue(this);

        //Initialize image loader
        mImageLoader = new ImageLoader(NetworkFacade.INSTANCE.getRequestQueue(), mBitmapLruCache);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
    public BitmapLruCache getBitmapLruCache() {
        return mBitmapLruCache;
    }

    public Bitmap getBitmapFromCache(String key) {
        return mBitmapLruCache.getBitmap(key);
    }

    private static PodcastAudioApplication singleton;

    // Returns the application instance
    public static PodcastAudioApplication getInstance() {
        return singleton;
    }
}
