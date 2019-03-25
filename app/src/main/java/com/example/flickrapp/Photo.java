package com.example.flickrapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Photo implements Parcelable {

    private String title;
    private String link;
    private Media media;
    private String date_taken;
    private String description;
    private String published;
    private String author;
    private String author_id;
    private String tags;

    public Photo(Media url, String title) {
        this.media = url;
        this.title = title;
    }

    public Photo(String title, String link, Media media, String date_taken, String description, String published, String author, String author_id, String tags) {
        this.title = title;
        this.link = link;
        this.media = media;
        this.date_taken = date_taken;
        this.description = description;
        this.published = published;
        this.author = author;
        this.author_id = author_id;
        this.tags = tags;
    }

    protected Photo(Parcel in) {
        media = new Media();
        media.setM(in.readString());
        title = in.readString();
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(media.getM());
        dest.writeString(title);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public String getDate_taken() {
        return date_taken;
    }

    public void setDate_taken(String date_taken) {
        this.date_taken = date_taken;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
