package com.example.flickrapp.rest;

import com.example.flickrapp.rest.to.Feed;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetData {

    @GET("photos_public.gne?format=json&nojsoncallback=true")
    Call<Feed> getPhotos();
}
