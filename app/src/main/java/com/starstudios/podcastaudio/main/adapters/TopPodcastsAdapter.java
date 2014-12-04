package com.starstudios.podcastaudio.main.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;
import com.starstudios.podcastaudio.PodcastAudioApplication;
import com.starstudios.podcastaudio.R;
import com.starstudios.podcastaudio.main.EpisodeInfoFragment;
import com.starstudios.podcastaudio.main.EpisodesList;
import com.starstudios.podcastaudio.main.composites.PodcastComposites;
import com.starstudios.podcastaudio.main.composites.PodcastInfoComposites;
import com.starstudios.podcastaudio.media.composites.JSGPodderPodcastEpisode;
import com.starstudios.podcastaudio.utils.BundleBuilder;
import com.starstudios.podcastaudio.utils.Utils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by delgadem on 11/14/14.
 */
public class TopPodcastsAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<JSGPodderPodcastEpisode> mPodcastCompositesList;

    public TopPodcastsAdapter(Context context, List<JSGPodderPodcastEpisode> compositesList) {
        mContext = context;
        mPodcastCompositesList = compositesList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        return TopPodcastsViewHolder.inflateView(mContext);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof TopPodcastsViewHolder) {
            ((TopPodcastsViewHolder) viewHolder).update(mContext, mPodcastCompositesList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mPodcastCompositesList.size();
    }

    public JSGPodderPodcastEpisode getItem(int position) {
        return mPodcastCompositesList.get(position);
    }


    public static class TopPodcastsViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.main_image)
        ImageView mImageView;

        @InjectView(R.id.title)
        TextView mTextView;

        public TopPodcastsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);}

        public void update(final Context context, final JSGPodderPodcastEpisode composite) {
            mTextView.setText(composite.title);
            if(!TextUtils.isEmpty(composite.logoUrl)) {
                Picasso.with(context)
                        .load(composite.logoUrl)
                        .tag(context)
                        .resizeDimen(R.dimen.default_image_width, R.dimen.default_image_height)
                        .centerCrop()
                        .into(mImageView);
                mImageView.setVisibility(View.VISIBLE);
            } else {
                mImageView.setVisibility(View.GONE);
            }

            if(composite.subscribersLastWeek > 2000) {
                final StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams.setFullSpan(true);
                itemView.setLayoutParams(layoutParams);
            } else {
                final StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams.setFullSpan(false);
                itemView.setLayoutParams(layoutParams);
            }

            if(!itemView.hasOnClickListeners()) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(context instanceof ActionBarActivity) {
                            EpisodesList fragment = (EpisodesList) EpisodesList.instantiate(context, "com.starstudios.podcastaudio.main.EpisodesList", new BundleBuilder().putString(Utils.ARG_PODCAST_ID, composite.url).build());
                            FragmentManager manager = ((ActionBarActivity) context).getSupportFragmentManager();
                            manager.beginTransaction()
                                    .add(android.R.id.content, fragment, "tag")
                                    .addToBackStack(null)
                                    .setTransition(R.anim.abc_fade_in)
                                    .commitAllowingStateLoss();
                        }
                    }
                });
            }
        }

        public static RecyclerView.ViewHolder inflateView(Context context) {
            View itemView = View.inflate(context, R.layout.podcast_item_view, null);

            TopPodcastsViewHolder viewHolder = new TopPodcastsViewHolder(itemView);
            itemView.setTag(viewHolder);

            return viewHolder;
        }
    }
}
