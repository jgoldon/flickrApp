package com.example.flickrapp.activity;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.flickrapp.rest.to.Photo;
import com.example.flickrapp.R;
import com.squareup.picasso.Picasso;

public class PhotoActivity extends AppCompatActivity {

    public static final String EXTRA_PHOTO = "PhotoActivity.PHOTO";
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        mImageView = findViewById(R.id.image);
        final Photo photo = getIntent().getParcelableExtra(EXTRA_PHOTO);

        Handler uiHandler = new Handler(Looper.getMainLooper());
        uiHandler.post(new Runnable(){
            @Override
            public void run() {
                Picasso.get()
                        .load(photo.getMedia().getM())
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(mImageView);
            }
        });
    }
}
