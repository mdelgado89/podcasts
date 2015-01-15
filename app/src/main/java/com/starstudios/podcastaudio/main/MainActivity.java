package com.starstudios.podcastaudio.main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.fasterxml.jackson.databind.JsonNode;
import com.starstudios.podcastaudio.R;
import com.starstudios.podcastaudio.data.composites.PodcastItemComposite;
import com.starstudios.podcastaudio.jsonparsers.MyJsonParser;
import com.starstudios.podcastaudio.main.adapters.TopPodcastsAdapter;
import com.starstudios.podcastaudio.network.NetworkFacade;

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

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ButterKnife.inject(this);

        setSupportActionBar(mToolbar);
        setupDrawer();
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        NetworkFacade.INSTANCE.makeRequest(Request.Method.GET, String.format(getString(R.string.popular_url)), this, this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
//        Log.d("MainActivity", error.getMessage());
    }

    @Override
    public void onResponse(Object response) {
        try {
            JsonNode rootNode = MyJsonParser.INSTANCE.getObjectMapper().readTree(response.toString());

            List<PodcastItemComposite> composites = new ArrayList<PodcastItemComposite>();
            rootNode = rootNode.get("podcasts");
            for (JsonNode node : rootNode) {
                PodcastItemComposite composite = new PodcastItemComposite(node);
                composites.add(composite);
            }

            mRecyclerView.setAdapter(new TopPodcastsAdapter(this, composites));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
