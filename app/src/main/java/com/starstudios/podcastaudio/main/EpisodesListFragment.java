package com.starstudios.podcastaudio.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.fasterxml.jackson.databind.JsonNode;
import com.starstudios.podcastaudio.R;
import com.starstudios.podcastaudio.jsonparsers.MyJsonParser;
import com.starstudios.podcastaudio.media.PodcastMediaPlayer;
import com.starstudios.podcastaudio.network.NetworkFacade;
import com.starstudios.podcastaudio.utils.Utils;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by delgadem on 11/17/14.
 */
public class EpisodesListFragment extends Fragment implements Response.ErrorListener, Response.Listener {

    @InjectView(R.id.main_recycler)
    RecyclerView mRecyclerView;

    private String mFormattedUrl;
    private String mNodeImageUrl;
    private int mPodcastId;
    private PodcastMediaPlayer mMediaPlayer;
    private boolean mIsBound = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_activity, null, false);
        ButterKnife.inject(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mPodcastId = getArguments().getInt(Utils.ARG_PODCAST_ID);
        mFormattedUrl = getString(R.string.episodes_url, mPodcastId);
//        mNodeImageUrl = getArguments().getString(Utils.ARG_IMAGE_URL);
        NetworkFacade.INSTANCE.makeGetRequest(mFormattedUrl, this, this);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    //connect to the service
//    private ServiceConnection mMusicConnection = new ServiceConnection(){
//
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            PodcastMediaPlayer.MusicBinder binder = (PodcastMediaPlayer.MusicBinder)service;
//            //get service
//            mMediaPlayer = binder.getService();
//            mIsBound = true;
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            mIsBound = false;
//        }
//    };

    @Override
    public void onResponse(Object response) {
        try {
            JsonNode rootNode = MyJsonParser.INSTANCE.getObjectMapper().readTree(response.toString());
            Log.d("TAG", rootNode.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
