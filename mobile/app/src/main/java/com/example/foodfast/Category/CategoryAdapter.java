package com.example.foodfast.Category;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.foodfast.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder> {
    private final ArrayList<CategoryModel> categories;

    public CategoryAdapter(ArrayList<CategoryModel> categories) {
        this.categories = categories;
    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CategoryHolder categoryHolder =  new CategoryHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item, parent, false));

        categoryHolder.context = parent.getContext();

        return  categoryHolder;
    }

    @Override
    public void onBindViewHolder(CategoryHolder holder, int position) {
        holder.id = categories.get(position).getId();
        holder.name.setText(categories.get(position).getName());

        if (!categories.get(position).getImage_url().equals("")) {
            Picasso.get().load(categories.get(position).getImage_url()).into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
        return categories != null ? categories.size() : 0;
    }
}
