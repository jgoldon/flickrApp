package com.example.flickrapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoWithDetailActivity extends AbstractGalleryActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_with_detail);

        configureChangeLayoutButton();

        GetData service = RetrofitClient.getRetrofitInstance().create(GetData.class);
        Call<Feed> call = service.getPhotos();
        call.enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                loadData(response.body());
            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                Toast.makeText(PhotoWithDetailActivity.this, "Unable to load photos :(", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void configureChangeLayoutButton() {
        Button changeBtn = findViewById(R.id.button2);
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PhotoWithDetailActivity.this, MainActivity.class));
            }
        });
    }

    private void loadData(Feed body) {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        RecyclerView recyclerView = findViewById(R.id.rv2_images);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        GalleryAdapter galleryAdapter = new GalleryAdapter(this, body.getItems());
        recyclerView.setAdapter(galleryAdapter);
    }
}
