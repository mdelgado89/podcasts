package com.starstudios.podcastaudio.main.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.starstudios.podcastaudio.R;
import com.starstudios.podcastaudio.data.composites.PodcastItemComposite;
import com.starstudios.podcastaudio.main.EpisodeInfoActivity;
import com.starstudios.podcastaudio.utils.Utils;

import java.io.IOException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by delgadem on 11/14/14.
 */
public class TopPodcastsAdapter extends RecyclerView.Adapter {

    private final static int SMALL_VIEW = 1;
    private final static int BIG_VIEW = 2;

    private Context mContext;
    private List<PodcastItemComposite> mPodcastCompositesList;

    public TopPodcastsAdapter(Context context, List<PodcastItemComposite> compositesList) {
        mContext = context;
        mPodcastCompositesList = compositesList;
    }

    @Override
    public int getItemViewType(int position) {
        if(position <= 5) {
            return BIG_VIEW;
        } else {
            return SMALL_VIEW;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        return TopPodcastsViewHolder.inflateView(mContext, type);
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

    public PodcastItemComposite getItem(int position) {
        return mPodcastCompositesList.get(position);
    }


    public static class TopPodcastsViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.card_view)
        CardView mParentView;

        @InjectView(R.id.main_image)
        ImageView mImageView;

        @InjectView(R.id.title)
        TextView mTextView;

        private boolean mBigView = false;

        Bitmap mBitmap;

        public TopPodcastsViewHolder(View itemView, boolean isBigView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            mBigView = isBigView;
        }

        public void update(final Context context, final PodcastItemComposite composite) {
            mTextView.setText(composite.mTitle);
            mImageView.setImageBitmap(null);
            if(!TextUtils.isEmpty(composite.mImageUrl) && mBigView) {
                Picasso.with(context)
                        .load(composite.mImageUrl)
                        .tag(context)
                        .resizeDimen(R.dimen.default_image_width, R.dimen.default_image_height)
                        .centerCrop()
                        .into(mImageView);
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {
                            try {
                                mBitmap = Picasso.with(context).load(composite.mImageUrl).get();
                                if (mBitmap != null) {
                                    Palette.generateAsync(mBitmap, new Palette.PaletteAsyncListener() {
                                        @Override
                                        public void onGenerated(Palette palette) {
                                            if (palette != null) {
                                                Palette.Swatch swatch = palette.getVibrantSwatch();
                                                    mTextView.setBackgroundColor(swatch.getRgb());
                                                    mTextView.setTextColor(swatch.getBodyTextColor());

                                            }
                                        }
                                    });
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }
                    }.execute();

                mImageView.setVisibility(View.VISIBLE);
            } else if(!TextUtils.isEmpty(composite.mImageUrl) ) {
                Picasso.with(context)
                        .load(composite.mImageUrl)
                        .tag(context)
                        .resizeDimen(R.dimen.small_image_width, R.dimen.small_image_height)
                        .centerCrop()
                        .into(mImageView);
                mImageView.setVisibility(View.VISIBLE);
            } else {
                mImageView.setVisibility(View.GONE);
            }

            if(mBigView) {
                final StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams.setFullSpan(true);
                itemView.setLayoutParams(layoutParams);
            } else {
                final StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setFullSpan(true);
                itemView.setLayoutParams(layoutParams);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, EpisodeInfoActivity.class);
                    intent.putExtra(Utils.ARG_PODCAST_ID, composite.mId);
                    context.startActivity(intent);
                }

            });
        }

        public static RecyclerView.ViewHolder inflateView(Context context, int type) {
            View itemView = null;
            if(type == BIG_VIEW) {
                itemView = View.inflate(context, R.layout.podcast_item_view, null);
                TopPodcastsViewHolder viewHolder = new TopPodcastsViewHolder(itemView, true);
                itemView.setTag(viewHolder);
                return viewHolder;
            } else {
                itemView = View.inflate(context, R.layout.small_item_view, null);
                TopPodcastsViewHolder viewHolder = new TopPodcastsViewHolder(itemView, false);
                itemView.setTag(viewHolder);
                return viewHolder;
            }
        }
    }
}
