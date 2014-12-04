package com.starstudios.podcastaudio.main.composites;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class RecentEpisode implements Parcelable {
    @JsonProperty("audio_url")
    public String audioUrl;
    @JsonProperty("title")
    public String title;
    @JsonIgnore
    public Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonIgnore
    public String audioImage;

    @JsonIgnore
    public String author;

    public RecentEpisode() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.audioUrl);
        dest.writeString(this.title);
        dest.writeString(this.audioImage);
        dest.writeString(this.author);
    }

    private RecentEpisode(Parcel in) {
        this.audioUrl = in.readString();
        this.title = in.readString();
        this.audioImage = in.readString();
        this.author = in.readString();
    }

    public static final Creator<RecentEpisode> CREATOR = new Creator<RecentEpisode>() {
        public RecentEpisode createFromParcel(Parcel source) {
            return new RecentEpisode(source);
        }

        public RecentEpisode[] newArray(int size) {
            return new RecentEpisode[size];
        }
    };
}