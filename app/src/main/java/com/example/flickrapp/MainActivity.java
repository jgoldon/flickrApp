package com.example.flickrapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//TODO: rename it
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetData service = RetrofitClient.getRetrofitInstance().create(GetData.class);
        Call<List<Photo>> call = service.getPhotos();
        call.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                loadData(response.body());
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                //TODO: add exception or smth
            }
        });
    }

    private void loadData(List<Photo> body) {
        //TODO: check specification
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        RecyclerView recyclerView = findViewById(R.id.rv_images);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        GalleryAdapter galleryAdapter = new GalleryAdapter(this, body);
        recyclerView.setAdapter(galleryAdapter);
    }

    private class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MViewHolder> {

        private List<Photo> mPhotos;
        private Context mContext;

        public GalleryAdapter(Context context, List<Photo> photos) {
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
            Photo photo = mPhotos.get(i);
            ImageView imageView = mViewHolder.imageView;
            //TODO: add another things - placeholder and error
            Picasso.get()
                    .load(photo.getmUrl())
                    .fit()
                    .into(imageView);
        }

        @Override
        public int getItemCount() {
            return mPhotos.size();
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
                    Photo photo = mPhotos.get(pos);
                    Intent intent = new Intent(mContext, PhotoActivity.class);
                    //TODO: add to activity
                    intent.putExtra(PhotoActivity.EXTRA_PHOTO, photo);
                    startActivity(intent);
                }
            }
        }
    }
}
