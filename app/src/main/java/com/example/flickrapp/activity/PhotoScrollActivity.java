package com.example.flickrapp.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.flickrapp.rest.to.Feed;
import com.example.flickrapp.rest.GetData;
import com.example.flickrapp.rest.to.Photo;
import com.example.flickrapp.R;
import com.example.flickrapp.rest.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoScrollActivity extends AbstractGalleryActivity<PhotoScrollActivity.GalleryAdapter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);

        configureChangeLayoutButton(R.id.button, PhotoScrollActivity.this,
                PhotoWithDetailActivity.class);

        GetData service = RetrofitClient.getRetrofitInstance().create(GetData.class);
        Call<Feed> call = service.getPhotos();
        call.enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(@NonNull Call<Feed> call, @NonNull Response<Feed> response) {
                if (response.body() != null) {
                    loadData(response.body(), 2, R.id.rv_images);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Feed> call, @NonNull Throwable t) {
                Toast.makeText(PhotoScrollActivity.this, "Unable to load photos :(",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected GalleryAdapter getGalleryAdapter(Context context, List<Photo> photos) {
        return new GalleryAdapter(context, photos);
    }

    @Override
    protected void setAdapter(RecyclerView recyclerView, GalleryAdapter galleryAdapter) {
        recyclerView.setAdapter(galleryAdapter);
    }


    protected class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MViewHolder> {

        private List<Photo> mPhotos;
        private Context mContext;

        GalleryAdapter(Context context, List<Photo> photos) {
            mContext = context;
            mPhotos = photos;
        }

        @NonNull
        @Override
        public GalleryAdapter.MViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view = layoutInflater.inflate(R.layout.image_view, parent, false);
            return new MViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MViewHolder mViewHolder, int i) {
            Photo photo = mPhotos.get(i);
            ImageView imageView = mViewHolder.imageView;
            Picasso.get()
                    .load(photo.getMedia().getM())
                    .fit()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(imageView);
        }

        @Override
        public int getItemCount() {
            return mPhotos.size();
        }

        public class MViewHolder extends RecyclerView.ViewHolder implements View

                .OnClickListener {

            final ImageView imageView;

            MViewHolder(View view) {
                super(view);
                imageView = itemView.findViewById(R.id.iv_photo);
                view.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                int pos = getAdapterPosition();
                if(pos != RecyclerView.NO_POSITION) {
                    Photo photo = mPhotos.get(pos);
                    Intent intent = new Intent(mContext, PhotoActivity.class);
                    intent.putExtra(PhotoActivity.EXTRA_PHOTO, photo);
                    startActivity(intent);
                }
            }
        }
    }
}
