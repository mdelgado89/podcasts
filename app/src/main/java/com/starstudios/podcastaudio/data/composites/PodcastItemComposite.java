package com.starstudios.podcastaudio.data.composites;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created by delgadem on 12/17/14.
 */
public class PodcastItemComposite {
    public int mId;
    public String mImageUrl;
    public String mTitle;


    public PodcastItemComposite() {

    }

    public PodcastItemComposite(@NonNull JsonNode node) {
        if(node.has("image_url")) {
            mImageUrl = node.get("image_url").asText();
        }

        if(node.has("title")) {
            mTitle = node.get("title").asText();
        }

        if(node.has("podcast_id")) {
            mId = node.get("podcast_id").asInt(-1);
        }
    }
}
