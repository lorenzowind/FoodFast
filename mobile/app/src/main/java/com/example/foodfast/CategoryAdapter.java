package com.example.foodfast;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder> {
    private final ArrayList<CategoryModel> categories;

    public CategoryAdapter(ArrayList<CategoryModel> categories) {
        this.categories = categories;
    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(CategoryHolder holder, int position) {
        holder.name.setText(categories.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return categories != null ? categories.size() : 0;
    }
}
