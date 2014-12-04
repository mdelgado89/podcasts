package com.starstudios.podcastaudio.main.composites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by delgadem on 11/14/14.
 */
public class PodcastComposites {

    @JsonProperty("title")
    public String title;
    @JsonProperty("podcast_id")
    public Integer podcastId;
    @JsonProperty("image_url")
    public String imageUrl;
    @JsonProperty("feed_url")
    public String feedUrl;
    @JsonIgnore
    public Map<String, Object> additionalProperties = new HashMap<String, Object>();
}
