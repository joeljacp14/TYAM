package com.jpdevelopers.myapp;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImageGridVH extends RecyclerView.ViewHolder {
    private ImageView gallery;

    public ImageGridVH(@NonNull View view) {
        super(view);
        gallery = (ImageView) view.findViewById(R.id.imgrid);
    }

    void bind(Bitmap gal1) {
        gallery.setImageBitmap(gal1);
    }
}
