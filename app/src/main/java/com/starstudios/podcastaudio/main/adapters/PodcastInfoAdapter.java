package com.starstudios.podcastaudio.main.adapters;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.starstudios.podcastaudio.PodcastAudioApplication;
import com.starstudios.podcastaudio.R;
import com.starstudios.podcastaudio.main.composites.PodcastComposites;
import com.starstudios.podcastaudio.main.composites.PodcastInfoComposites;
import com.starstudios.podcastaudio.main.composites.RecentEpisode;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by delgadem on 11/17/14.
 */
public class PodcastInfoAdapter extends RecyclerView.Adapter {

    private List<RecentEpisode> mCompositesLists;
    private Context mContext;
    private String mImageUrl;

    public PodcastInfoAdapter(Context context, List<RecentEpisode> compositesList, String imageUrl) {
        mContext = context;
        mCompositesLists = compositesList;
        mImageUrl = imageUrl;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return PodcastsInfoViewHolder.inflateView(mContext, mImageUrl);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ((PodcastsInfoViewHolder) viewHolder).update(getItem(position));
    }

    @Override
    public int getItemCount() {
        return mCompositesLists.size();
    }

    public RecentEpisode getItem(int position) {
        return mCompositesLists.get(position);
    }



    public static class PodcastsInfoViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.main_image)
        NetworkImageView mImageView;

        @InjectView(R.id.title)
        TextView mTextView;

        private String mImageUrl;

        public PodcastsInfoViewHolder(View itemView, String imageUrl) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            mImageUrl = imageUrl;
        }

        public void update(RecentEpisode composite) {
            mImageView.setImageUrl(mImageUrl, PodcastAudioApplication.getInstance().getImageLoader());
            mTextView.setText(composite.title);
            if ((getPosition() == 0 || getPosition() % 9 == 0) && itemView != null) {
                final StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams.setFullSpan(true);
                itemView.setLayoutParams(layoutParams);
            } else {
                final StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams.setFullSpan(false);
                itemView.setLayoutParams(layoutParams);
            }
        }

        public static RecyclerView.ViewHolder inflateView(Context context, String imageUrl) {
            View itemView = View.inflate(context, R.layout.small_item_view, null);

            PodcastsInfoViewHolder viewHolder = new PodcastsInfoViewHolder(itemView, imageUrl);
            itemView.setTag(viewHolder);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Do something here
                }
            });

            return viewHolder;
        }
    }
}
