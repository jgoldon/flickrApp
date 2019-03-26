package com.example.flickrapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

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
            View view = layoutInflater.inflate(R.layout.image_text_view, parent, false);
            GalleryAdapter.MViewHolder viewHolder = new GalleryAdapter.MViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MViewHolder mViewHolder, int i) {
            Photo photo = mPhotos.get(i);
            ImageView imageView = mViewHolder.imageView;
            //TODO: add another things - placeholder and error
            Picasso.get()
                    .load(photo.getMedia().getM())
                    .fit()
                    .into(imageView);
            mViewHolder.textView.setText(String.format("Title: %s\nDate taken: %s\n" +
                    "Published: %s\nAuthor: %s", photo.getTitle(), photo.getDate_taken(),
                    photo.getPublished(), photo.getAuthor()));
        }

        @Override
        public int getItemCount() {
            return mPhotos.size();
        }

        public class MViewHolder extends RecyclerView.ViewHolder implements View

                .OnClickListener {

            public final ImageView imageView;
            public final TextView textView;

            public MViewHolder(View view) {
                super(view);
                imageView = itemView.findViewById(R.id.ivt_photo);
                textView = itemView.findViewById(R.id.ivt_text);
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
