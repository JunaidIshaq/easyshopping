package com.project.easyshopping.data.model;

public class HistoryRowItem {

    private String title;
    private String link;
    private String dateTime;
    private int image;

    public HistoryRowItem() {
    }

    public HistoryRowItem(String title, String link, String dateTime, int image) {
        this.setTitle(title);
        this.setLink(link);
        this.setDateTime(dateTime);
        this.setImage(image);
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "HistoryRowItem{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", image=" + image +
                '}';
    }
}
