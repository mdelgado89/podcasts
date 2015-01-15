package com.starstudios.podcastaudio.main.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.starstudios.podcastaudio.R;
import com.starstudios.podcastaudio.data.composites.PodcastItemComposite;
import com.starstudios.podcastaudio.data.pojo.RecentEpisodes;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by delgadem on 1/11/15.
 */
public class EpisodeInfoAdapter extends RecyclerView.Adapter {

    List<RecentEpisodes> mRecentEpisodes;
    private Context mContext;

    public EpisodeInfoAdapter(Context context, List<RecentEpisodes> recentEpisodes) {
        mRecentEpisodes = recentEpisodes;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return RecentEpisodesViewHolder.inflateView(mContext, parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof RecentEpisodesViewHolder) {
            ((RecentEpisodesViewHolder) holder).update(getItem(position));
        }
    }

    @Override
    public int getItemCount() {
        return mRecentEpisodes.size();
    }

    public RecentEpisodes getItem(int position) {
        return mRecentEpisodes.get(position);
    }

    public static class RecentEpisodesViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.title)
        TextView mTitle;

        @InjectView(R.id.description)
        TextView mDescription;

        public RecentEpisodesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        public void update(RecentEpisodes episode) {
            mTitle.setText(episode.title);
            mDescription.setText(episode.audio_url);
        }

        public static RecentEpisodesViewHolder inflateView(Context context, ViewGroup parent, int viewType) {
            View itemView = View.inflate(context, R.layout.podcast_info_item_view, null);
            RecentEpisodesViewHolder vh = new RecentEpisodesViewHolder(itemView);
            itemView.setTag(vh);

            return vh;
        }
    }
}
