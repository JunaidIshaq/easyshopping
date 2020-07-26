package com.project.easyshopping.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SearchHistoryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dateTime, title, link;

    public SearchHistoryDTO(Date dateTime,String title, String link) {
        this.setDateTime(dateTime);
        this.setTitle(title);
        this.setLink(link);
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        this.dateTime = simpleDateFormat.format(dateTime);
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
}
