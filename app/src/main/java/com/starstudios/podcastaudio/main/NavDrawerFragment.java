package com.starstudios.podcastaudio.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.starstudios.podcastaudio.R;

/**
 * Created by delgadem on 1/11/15.
 */
public class NavDrawerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.drawer_layout, container, false);
        return rootView;
    }
}
