package com.starstudios.podcastaudio.main;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.fasterxml.jackson.databind.JsonNode;
import com.starstudios.podcastaudio.R;
import com.starstudios.podcastaudio.jsonparsers.MyJsonParser;
import com.starstudios.podcastaudio.main.composites.PodcastComposites;
import com.starstudios.podcastaudio.main.adapters.TopPodcastsAdapter;
import com.starstudios.podcastaudio.media.composites.JSGPodderPodcastEpisode;
import com.starstudios.podcastaudio.network.NetworkFacade;
import com.starstudios.podcastaudio.utils.BundleBuilder;
import com.starstudios.podcastaudio.utils.RecyclerViewOnItemClickListener;
import com.starstudios.podcastaudio.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by delgadem on 11/14/14.
 */
public class MainActivity extends ActionBarActivity implements Response.ErrorListener, Response.Listener {

    @InjectView(R.id.main_recycler)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ButterKnife.inject(this);

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        NetworkFacade.INSTANCE.makeRequest(Request.Method.GET, String.format(getString(R.string.popular_url_v2), 50), this, this);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
//        Log.d("MainActivity", error.getMessage());
    }

    @Override
    public void onResponse(Object response) {
        try {
            JsonNode rootNode = MyJsonParser.INSTANCE.getObjectMapper().readTree(response.toString());

                List<JSGPodderPodcastEpisode> composites = new ArrayList<JSGPodderPodcastEpisode>();
                for (JsonNode podcast : rootNode) {
                    JSGPodderPodcastEpisode composite = (JSGPodderPodcastEpisode) MyJsonParser.INSTANCE.parseJson(podcast.toString(), JSGPodderPodcastEpisode.class);

                    if(TextUtils.isEmpty(composite.logoUrl) || composites.contains(composite)) {
                        continue;
                    }

                    if(composite.title.contains("(")) {
                        //Strip everything after the (
                        composite.title = composite.title.substring(0, composite.title.indexOf("("));
                    }

                    composites.add(composite);
                }

                mRecyclerView.setAdapter(new TopPodcastsAdapter(this, composites));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
