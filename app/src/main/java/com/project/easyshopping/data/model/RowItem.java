package com.project.easyshopping.data.model;

import android.net.Uri;

import com.project.easyshopping.util.Utility;

public class RowItem {

    private String title;
    private int imageId;
    private String link;

    public RowItem(String title, int imageId, String link) {
        this.title = title;
        this.imageId = imageId;
        this.link = Utility.getBaseUrl(link);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "RowItem{" +
                "title='" + title + '\'' +
                ", imageId='" + imageId + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
