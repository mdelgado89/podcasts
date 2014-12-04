package com.starstudios.podcastaudio.utils;

import android.content.Context;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by delgadem on 11/15/14.
 */
public class Utils {

    public final static String ARG_PODCAST_ID = "podcast_id";
    public static final String ARG_IMAGE_URL = "node_image_url";

    public static void setMediaImageView(final Context context, final View view, float horizontalPadding, final int rationWidth, final int ratioHeight) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null) {
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        final ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        final ViewGroup.LayoutParams finalParams = params;
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    view.getViewTreeObserver().removeOnPreDrawListener(this);
                    int viewWidth = view.getMeasuredWidth();
                    finalParams.height = getRatioForHeight(viewWidth, rationWidth, ratioHeight);
                    view.setLayoutParams(finalParams);

                    return true;
                }
            });
        }

    }

    public static int getRatioForHeight(int width, int ratioWidth, int ratioHeight) {
        return (int) (Math.ceil((float)width / (float)ratioWidth) * ratioHeight);
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);

        return dm.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);

        return dm.heightPixels;
    }

    public enum DeviceType {
        HANDSET, MIDSIZE, TABLET
    }

    /**
     * Converts pixels to dip
     *
     * @param pixelWidth The pixel width to use to convert
     * @param pixelHeight The pixel height to use to convert
     */
    public static ViewGroup.LayoutParams convertFromPixelsToDip(int pixelWidth, int pixelHeight, DisplayMetrics display) {
        float dipWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixelWidth, display);
        float dipHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixelHeight, display);
        return new ViewGroup.LayoutParams((int) dipWidth, (int) dipHeight);
    }

    /**
     * Recursively iterate through the dir and all its sub-dir's to delete all files
     *
     * @param dir
     * @return
     */
    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null) {
                for (String element : children) {
                    boolean success = deleteDir(new File(dir, element));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        return dir.delete();
    }

    public static Object deserializeObject(byte[] byteArray) {
        try {
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(byteArray));
            Object object = in.readObject();
            in.close();

            return object;
        } catch (ClassNotFoundException cnfe) {
            Log.e("deserializeObject", "class not found error", cnfe);

            return null;
        } catch (IOException ioe) {
            Log.e("deserializeObject", "io error", ioe);

            return null;
        }
    }

    /**
     * Obtains the device's current offset from GMT in seconds
     *
     * @return The GMT offset in seconds
     */
    public static String getCurrentGMTOffsetInSeconds() {
        Time now = new Time();
        now.set(System.currentTimeMillis());
        return String.valueOf(now.gmtoff);
    }

    /**
     * Returns the type of device being used. Potential types are HANDSET, MIDSIZE, and TABLET.
     *
     * @param context The caller's Context
     * @return The appropriate DeviceType, based on the device screen width
     */
    public static DeviceType getDeviceType(Context context) {
        int screenWidth = Math.min(context.getResources().getDisplayMetrics().widthPixels, context.getResources()
                .getDisplayMetrics().heightPixels);
        float screenWidthDp = screenWidth / context.getResources().getDisplayMetrics().density;
        if (screenWidthDp < 600) {
            // Handset
            return DeviceType.HANDSET;
        } else if (screenWidthDp >= 600 && screenWidthDp < 720) {
            // Mid-size device
            return DeviceType.MIDSIZE;
        } else {
            // Tablet
            return DeviceType.TABLET;
        }
    }
}
