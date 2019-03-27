package com.example.flickrapp.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.flickrapp.rest.to.Feed;
import com.example.flickrapp.rest.to.Photo;

import java.util.List;

public abstract class AbstractGalleryActivity<T> extends AppCompatActivity {

    protected abstract T getGalleryAdapter(Context context, List<Photo> photos);

    protected abstract void setAdapter(RecyclerView recyclerView, T galleryAdapter);

    protected void loadData(Feed body, int columnsCount, int id) {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, columnsCount);
        RecyclerView recyclerView = findViewById(id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        T galleryAdapter = getGalleryAdapter(this, body.getItems());
        setAdapter(recyclerView, galleryAdapter);
    }

    protected void configureChangeLayoutButton(int buttonId, final Context context, final Class cl) {
        Button changeBtn = findViewById(buttonId);
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(context, cl));
            }
        });
    }


}
