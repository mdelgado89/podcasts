package com.starstudios.podcastaudio.data.composites;

import com.fasterxml.jackson.databind.JsonNode;
import com.starstudios.podcastaudio.data.pojo.RecentEpisodes;
import com.starstudios.podcastaudio.jsonparsers.MyJsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by delgadem on 12/17/14.
 */
public class EpisodeItemComposite {
    public int mPodcastId;
    public String mPodcastTitle;
    public String mPodcastSummary;
    public String mPodcastImage;
    public List<RecentEpisodes> mEpisodes;

    public EpisodeItemComposite(JsonNode node) {
        mEpisodes = new ArrayList<>();
        try {
            JsonNode recentsNodes = node.get("recent_episodes");
            for (JsonNode recentNode : recentsNodes) {
                RecentEpisodes episode = (RecentEpisodes) MyJsonParser.INSTANCE.parseJson(recentNode.toString(), RecentEpisodes.class);
                mEpisodes.add(episode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        mPodcastTitle = node.get("title").asText();
        mPodcastSummary = node.get("summary").asText();
        mPodcastImage = node.get("image_url").asText();
        mPodcastId = node.get("podcast_id").asInt();
    }
}
