package com.example.flickrapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

public class PhotoActivity extends AppCompatActivity {

    public static final String EXTRA_PHOTO = "PhotoActivity.PHOTO";
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        mImageView = findViewById(R.id.image);
        final Photo photo = getIntent().getParcelableExtra(EXTRA_PHOTO);

        //TODO: aldo add error handling and placeholder
        Handler uiHandler = new Handler(Looper.getMainLooper());
        uiHandler.post(new Runnable(){
            @Override
            public void run() {
                Picasso.get().load(photo.getMedia().getM())
                        .into(mImageView);
            }
        });
//                    .
//                    .into(mImageView);
    }
}
