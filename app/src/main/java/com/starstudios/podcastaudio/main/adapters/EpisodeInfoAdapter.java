package com.starstudios.podcastaudio.main.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.starstudios.podcastaudio.R;
import com.starstudios.podcastaudio.adapter.ParallaxRecyclerAdapter;
import com.starstudios.podcastaudio.data.pojo.RecentEpisodes;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by delgadem on 1/11/15.
 */
public class EpisodeInfoAdapter extends ParallaxRecyclerAdapter {

    int HEADER_VIEW_TYPE = 0x1;
    int EPISODE_VIEW_TYPE = 0x2;

    List<RecentEpisodes> mRecentEpisodes;
    private Context mContext;

    public String mUrl;

    public EpisodeInfoAdapter(Context context, List<RecentEpisodes> recentEpisodes, String url) {
        super(recentEpisodes);
        mRecentEpisodes = recentEpisodes;
        mContext = context;
        mUrl = url;

        implementRecyclerAdapterMethods(new RecyclerAdapterMethods() {
            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
                ((RecentEpisodesViewHolder) viewHolder).update(getItem(position));
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return RecentEpisodesViewHolder.inflateView(mContext, parent, viewType);
            }

            @Override
            public int getItemCount() {
                return mRecentEpisodes.size();
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);//position == 0 ? EPISODE_VIEW_TYPE : EPISODE_VIEW_TYPE;
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
            View itemView = LayoutInflater.from(context).inflate(R.layout.podcast_info_item_view, parent, false);
            RecentEpisodesViewHolder vh = new RecentEpisodesViewHolder(itemView);
            itemView.setTag(vh);

            return vh;
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.action_bar_image)
        ImageView view;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        public void update(String url) {
            Picasso.with(itemView.getContext()).load(url).into(view);
        }
        public static HeaderViewHolder inflateView(Context context, ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.action_bar_header_image, parent, false);
            HeaderViewHolder vh = new HeaderViewHolder(itemView);
            itemView.setTag(vh);

            return vh;
        }
    }
}
