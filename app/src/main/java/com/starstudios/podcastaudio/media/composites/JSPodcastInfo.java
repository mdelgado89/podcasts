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
public class JSPodcastInfo {

    @JsonProperty("website")
    private String website;
    @JsonProperty("mygpo_link")
    private String mygpoLink;
    @JsonProperty("description")
    private String description;
    @JsonProperty("subscribers")
    private Integer subscribers;
    @JsonProperty("title")
    private String title;
    @JsonProperty("url")
    private String url;
    @JsonProperty("subscribers_last_week")
    private Integer subscribersLastWeek;
    @JsonProperty("logo_url")
    private String logoUrl;
    @JsonProperty("scaled_logo_url")
    private String scaledLogoUrl;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The website
     */
    @JsonProperty("website")
    public String getWebsite() {
        return website;
    }

    /**
     * @param website The website
     */
    @JsonProperty("website")
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * @return The mygpoLink
     */
    @JsonProperty("mygpo_link")
    public String getMygpoLink() {
        return mygpoLink;
    }

    /**
     * @param mygpoLink The mygpo_link
     */
    @JsonProperty("mygpo_link")
    public void setMygpoLink(String mygpoLink) {
        this.mygpoLink = mygpoLink;
    }

    /**
     * @return The description
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The subscribers
     */
    @JsonProperty("subscribers")
    public Integer getSubscribers() {
        return subscribers;
    }

    /**
     * @param subscribers The subscribers
     */
    @JsonProperty("subscribers")
    public void setSubscribers(Integer subscribers) {
        this.subscribers = subscribers;
    }

    /**
     * @return The title
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The url
     */
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    /**
     * @param url The url
     */
    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return The subscribersLastWeek
     */
    @JsonProperty("subscribers_last_week")
    public Integer getSubscribersLastWeek() {
        return subscribersLastWeek;
    }

    /**
     * @param subscribersLastWeek The subscribers_last_week
     */
    @JsonProperty("subscribers_last_week")
    public void setSubscribersLastWeek(Integer subscribersLastWeek) {
        this.subscribersLastWeek = subscribersLastWeek;
    }

    /**
     * @return The logoUrl
     */
    @JsonProperty("logo_url")
    public String getLogoUrl() {
        return logoUrl;
    }

    /**
     * @param logoUrl The logo_url
     */
    @JsonProperty("logo_url")
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    /**
     * @return The scaledLogoUrl
     */
    @JsonProperty("scaled_logo_url")
    public String getScaledLogoUrl() {
        return scaledLogoUrl;
    }

    /**
     * @param scaledLogoUrl The scaled_logo_url
     */
    @JsonProperty("scaled_logo_url")
    public void setScaledLogoUrl(String scaledLogoUrl) {
        this.scaledLogoUrl = scaledLogoUrl;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
