package com.starstudios.podcastaudio.main.composites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by delgadem on 11/17/14.
 */
public class PodcastInfoComposites {
    @JsonProperty("feed_url")
    public String feed_url;
    @JsonProperty("image_url")
    public String image_url;
    @JsonProperty("title")
    public String title;
    @JsonProperty("summary")
    public String summary;
    @JsonProperty("podcast_id")
    private Integer podcast_id;
    @JsonProperty("recent_episodes")
    public List<RecentEpisode> recent_episodes = new ArrayList<RecentEpisode>();
    @JsonIgnore
    public Map<String, Object> additionalProperties = new HashMap<String, Object>();
}
