package com.example.flickrapp.rest.to;

import java.util.List;

public class Feed {

    private List<Photo> items;

    public List<Photo> getItems() {
        return items;
    }

    public void setItems(List<Photo> items) {
        this.items = items;
    }
}
