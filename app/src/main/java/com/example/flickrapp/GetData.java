package com.example.flickrapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetData {

    //TODO: do it better
    @GET("/photos_public.gne?format=json")
    Call<List<Photo>> getPhotos();
}
