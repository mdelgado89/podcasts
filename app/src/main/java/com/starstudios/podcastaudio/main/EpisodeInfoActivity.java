package com.starstudios.podcastaudio.main;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.fasterxml.jackson.databind.JsonNode;
import com.poliveira.parallaxrecyclerview.adapter.ParallaxRecyclerAdapter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.starstudios.podcastaudio.R;
import com.starstudios.podcastaudio.data.composites.EpisodeItemComposite;
import com.starstudios.podcastaudio.jsonparsers.MyJsonParser;
import com.starstudios.podcastaudio.main.adapters.EpisodeInfoAdapter;
import com.starstudios.podcastaudio.network.NetworkFacade;
import com.starstudios.podcastaudio.utils.Utils;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by delgadem on 12/17/14.
 */
public class EpisodeInfoActivity extends ActionBarActivity implements Response.Listener, Response.ErrorListener, View.OnClickListener {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.podcast_info_recycler)
    RecyclerView mRecyclerView;

    int minHeaderTranslation;

    private EpisodeItemComposite mItemComposite;
    private int mLastDampedScroll = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.podcast_info_activity);

        if(getIntent().hasExtra("bitmap")) {
            Bitmap bitmap = getIntent().getParcelableExtra("bitmap");
        } else {
            String url = getIntent().getStringExtra("bitmapUrl");
        }

        ButterKnife.inject(this);

        setSupportActionBar(mToolbar);
        mToolbar.setCollapsible(true);
        mToolbar.setHorizontalFadingEdgeEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolbar.setNavigationOnClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

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
                EpisodeInfoAdapter adapter = new EpisodeInfoAdapter(this, mItemComposite.mEpisodes, mItemComposite.mPodcastImage);
                ImageView view = (ImageView) LayoutInflater.from(this).inflate(R.layout.action_bar_header_image, mRecyclerView, false);
                Picasso.with(EpisodeInfoActivity.this)
                        .load(mItemComposite.mPodcastImage)
                        .into(view);
                adapter.setParallaxHeader(view, mRecyclerView);
                adapter.setOnParallaxScroll(new ParallaxRecyclerAdapter.OnParallaxScroll() {
                    @Override
                    public void onParallaxScroll(float percentage, float offset, View parallax) {
                        mToolbar.setAlpha(percentage);
//                        Drawable c = mToolbar.getBackground();
//                        c.setAlpha(Math.round(percentage * 255));
//                        mToolbar.setBackground(c);
                    }
                });
                mRecyclerView.setAdapter(adapter);

                mToolbar.setTitle(mItemComposite.mPodcastTitle);

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
