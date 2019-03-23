package com.example.flickrapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

//TODO: rename it
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MViewHolder> {

        private Photo[] mPhotos;
        private Context mContext;

        public GalleryAdapter(Context context, Photo[] photos) {
            mContext = context;
            mPhotos = photos;
        }

        @NonNull
        @Override
        public GalleryAdapter.MViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view = layoutInflater.inflate(R.layout.image_view, parent, false);
            GalleryAdapter.MViewHolder viewHolder = new GalleryAdapter.MViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MViewHolder mViewHolder, int i) {
            Photo photo = mPhotos[i];
            ImageView imageView = mViewHolder.imageView;
            //TODO: add another things - placeholder and error
            Picasso.get()
                    .load(photo.getmUrl())
                    .fit()
                    .into(imageView);
        }

        @Override
        public int getItemCount() {
            return mPhotos.length;
        }


        public class MViewHolder extends RecyclerView.ViewHolder implements View

                .OnClickListener {

            public ImageView imageView;

            public MViewHolder(View view) {
                super(view);
                imageView = itemView.findViewById(R.id.iv_photo);
                view.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                int pos = getAdapterPosition();
                if(pos != RecyclerView.NO_POSITION) {
                    Photo photo = mPhotos[pos];
                    Intent intent = new Intent(mContext, PhotoActivity.class);
                    //TODO: add to activity
                    intent.putExtra(PhotoActivity.EXTRA_PHOTO, photo);
                    startActivity(intent);
                }
            }
        }
    }
}
