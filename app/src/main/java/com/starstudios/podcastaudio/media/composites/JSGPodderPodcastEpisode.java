package com.starstudios.podcastaudio.media.composites;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "website",
        "mygpo_link",
        "description",
        "subscribers",
        "title",
        "url",
        "subscribers_last_week",
        "logo_url",
        "scaled_logo_url"
})
public class JSGPodderPodcastEpisode {

    @JsonProperty("website")
    public String website;
    @JsonProperty("mygpo_link")
    public String mygpoLink;
    @JsonProperty("description")
    public String description;
    @JsonProperty("subscribers")
    public Integer subscribers;
    @JsonProperty("title")
    public String title;
    @JsonProperty("url")
    public String url;
    @JsonProperty("subscribers_last_week")
    public Integer subscribersLastWeek;
    @JsonProperty("logo_url")
    public String logoUrl;
    @JsonProperty("scaled_logo_url")
    public String scaledLogoUrl;
    @JsonIgnore
    public Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof JSGPodderPodcastEpisode) {
            return ((JSGPodderPodcastEpisode) o).url.equals(this.url);
        }

        return false;
    }
}

