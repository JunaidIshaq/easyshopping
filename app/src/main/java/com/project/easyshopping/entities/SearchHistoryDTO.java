package com.project.easyshopping.entities;

import java.io.Serializable;
import java.util.Date;

public class SearchHistoryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Date currentDate;

    private String searchLink;

    public SearchHistoryDTO(Date currentDate, String searchLink) {
        this.currentDate = currentDate;
        this.searchLink = searchLink;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public String getSearchLink() {
        return searchLink;
    }

    public void setSearchLink(String searchLink) {
        this.searchLink = searchLink;
    }
}
