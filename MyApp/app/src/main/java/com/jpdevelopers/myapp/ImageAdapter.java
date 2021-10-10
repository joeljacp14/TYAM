package com.jpdevelopers.myapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageGridVH> {
    private Context context;
    private ArrayList<ImageView> images;

    public ImageAdapter (Context context, ArrayList<ImageView> images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public ImageGridVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.image_grid, parent, false);
        return new ImageGridVH (view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageGridVH holder, int position) {
        ImageView image = images.get(position);
        holder.bind(image.getDrawingCache());
    }

    @Override
    public int getItemCount() {
        return images.size();
    }
}
