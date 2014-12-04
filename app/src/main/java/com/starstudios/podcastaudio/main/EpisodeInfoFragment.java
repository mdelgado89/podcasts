package com.starstudios.podcastaudio.main;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.fasterxml.jackson.databind.JsonNode;
import com.starstudios.podcastaudio.R;
import com.starstudios.podcastaudio.jsonparsers.MyJsonParser;
import com.starstudios.podcastaudio.main.adapters.PodcastInfoAdapter;
import com.starstudios.podcastaudio.main.composites.PodcastInfoComposites;
import com.starstudios.podcastaudio.main.composites.RecentEpisode;
import com.starstudios.podcastaudio.media.PodcastMediaPlayer;
import com.starstudios.podcastaudio.network.NetworkFacade;
import com.starstudios.podcastaudio.utils.RecyclerViewOnItemClickListener;
import com.starstudios.podcastaudio.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by delgadem on 11/17/14.
 */
public class EpisodeInfoFragment extends Fragment implements Response.ErrorListener, Response.Listener {

    @InjectView(R.id.main_recycler)
    RecyclerView mRecyclerView;

    private int mPodcastId;
    private String mFormattedUrl;
    private String mNodeImageUrl;
    private RecentEpisode mCurrentEpisode;
    private PodcastMediaPlayer mMediaPlayer;
    private boolean mIsBound = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDetach() {
        if(mIsBound) {
            getActivity().unbindService(mMusicConnection);
            mMusicConnection = null;
        }

        super.onDetach();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Intent intent = new Intent(activity, PodcastMediaPlayer.class);
        activity.bindService(intent, mMusicConnection, Context.BIND_AUTO_CREATE);
        activity.startService(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_activity, null, false);
        ButterKnife.inject(this, rootView);

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnItemTouchListener(new RecyclerViewOnItemClickListener(mRecyclerView) {
            @Override
            public boolean performItemClick(RecyclerView parent, View view, int position, long id) {
                if(parent.getAdapter() instanceof PodcastInfoAdapter) {
                    final RecentEpisode item = ((PodcastInfoAdapter) parent.getAdapter()).getItem(position);
                    if(item !=  null && !TextUtils.isEmpty(item.audioUrl)) {
                        mMediaPlayer.updateCurrentEpisode(item);
                    }
                }
                return false;
            }

            @Override
            public boolean performItemLongClick(RecyclerView parent, View view, int position, long id) {
                return false;
            }
        });

        mPodcastId = getArguments().getInt(Utils.ARG_PODCAST_ID);
        mFormattedUrl = getString(R.string.podcast_info_url, mPodcastId);
        mNodeImageUrl = getArguments().getString(Utils.ARG_IMAGE_URL);
        NetworkFacade.INSTANCE.makeGetRequest(mFormattedUrl, this, this);

        return rootView;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    //connect to the service
    private ServiceConnection mMusicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PodcastMediaPlayer.MusicBinder binder = (PodcastMediaPlayer.MusicBinder)service;
            //get service
            mMediaPlayer = binder.getService();
            mIsBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIsBound = false;
        }
    };

    @Override
    public void onResponse(Object response) {
        try {
            JsonNode rootNode = MyJsonParser.INSTANCE.getObjectMapper().readTree(response.toString());
            if (rootNode.has("podcast")) {
                JsonNode node = rootNode.get("podcast");
                PodcastInfoComposites composite = (PodcastInfoComposites) MyJsonParser.INSTANCE.parseJson(node.toString(), PodcastInfoComposites.class);
                if(composite.recent_episodes != null) {
                    for(RecentEpisode episode : composite.recent_episodes) {
                        episode.author = composite.title;
                        episode.audioImage = mNodeImageUrl;
                    }
                }

                mRecyclerView.setAdapter(new PodcastInfoAdapter(getActivity(), composite.recent_episodes, mNodeImageUrl));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
