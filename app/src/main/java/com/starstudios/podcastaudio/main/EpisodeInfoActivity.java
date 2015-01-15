package com.starstudios.podcastaudio.main;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.fasterxml.jackson.databind.JsonNode;
import com.manuelpeinado.fadingactionbar.extras.actionbarcompat.FadingActionBarHelper;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.starstudios.podcastaudio.R;
import com.starstudios.podcastaudio.data.composites.EpisodeItemComposite;
import com.starstudios.podcastaudio.data.composites.PodcastItemComposite;
import com.starstudios.podcastaudio.jsonparsers.MyJsonParser;
import com.starstudios.podcastaudio.main.adapters.EpisodeInfoAdapter;
import com.starstudios.podcastaudio.network.NetworkFacade;
import com.starstudios.podcastaudio.utils.Utils;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by delgadem on 12/17/14.
 */
public class EpisodeInfoActivity extends ActionBarActivity implements Response.Listener, Response.ErrorListener, View.OnClickListener {

//    @InjectView(R.id.toolbar)
//    Toolbar mToolbar;

    @InjectView(R.id.podcast_info_recycler)
    RecyclerView mRecyclerView;

    private EpisodeItemComposite mItemComposite;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Toolbar toolbar = new Toolbar(this);
        if(getIntent().hasExtra("bitmap")) {
            Bitmap bitmap = getIntent().getParcelableExtra("bitmap");
        } else {
            String url = getIntent().getStringExtra("bitmapUrl");
        }

        ButterKnife.inject(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        mToolbar.setHorizontalFadingEdgeEnabled(true);
//        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
//        mToolbar.setNavigationOnClickListener(this);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        int podcastId = getIntent().getIntExtra(Utils.ARG_PODCAST_ID, 0);
        NetworkFacade.INSTANCE.makeRequest(Request.Method.GET, String.format(getString(R.string.episodes_url), podcastId), this, this);
    }

    @Override
    public void onResponse(Object response) {
        if (response != null) {
            try {
                JsonNode rootNode = MyJsonParser.INSTANCE.getObjectMapper().readTree(response.toString());
                JsonNode podcastNode = rootNode.get("podcast");
                mItemComposite = new EpisodeItemComposite(podcastNode);
                EpisodeInfoAdapter adapter = new EpisodeInfoAdapter(this, mItemComposite.mEpisodes);
                mRecyclerView.setAdapter(adapter);

                getSupportActionBar().setTitle(mItemComposite.mPodcastTitle);
//                mToolbar.setTitle(mItemComposite.mPodcastTitle);

//                Picasso.with(EpisodeInfoActivity.this).load(mItemComposite.mPodcastImage).into();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
