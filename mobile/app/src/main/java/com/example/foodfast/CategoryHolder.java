package com.example.foodfast;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CategoryHolder extends RecyclerView.ViewHolder {
    public ImageView image;
    public TextView name;
    public ImageView button;

    public CategoryHolder(View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.category_image);
        name = itemView.findViewById(R.id.category_name);
        button = itemView.findViewById(R.id.category_button);
    }
}
