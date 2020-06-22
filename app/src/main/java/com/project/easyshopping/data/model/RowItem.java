package com.project.easyshopping.data.model;

import com.google.api.services.customsearch.model.Result;
import com.project.easyshopping.util.Utility;

public class RowItem {

    private String title;
    private int image;
    private String link;

    public RowItem(String title, int image, String link) {
        this.title = title;
        this.image = image;
        this.link = Utility.getBaseUrl(link);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
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
                ", image='" + image + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
