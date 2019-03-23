package com.example.flickrapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PhotoActivity extends AppCompatActivity {

    public static final String EXTRA_PHOTO = "PhotoActivity.PHOTO";
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        mImageView = findViewById(R.id.image);
        Photo photo = getIntent().getParcelableExtra(EXTRA_PHOTO);

        //TODO: aldo add error handling and placeholder
        Picasso.get()
                .load(photo.getmUrl())
                .fit()
                .into(mImageView);
    }
}
