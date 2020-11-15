package com.example.foodfast.Recipe;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodfast.R;
import com.example.foodfast.RecipeDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeHolder> {
    private Context context;
    private final ArrayList<RecipeModel> recipes;

    public RecipeAdapter(ArrayList<RecipeModel> recipes) {
        this.recipes = recipes;
    }

    @Override
    public RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecipeHolder recipeHolder =  new RecipeHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_item, parent, false));

        recipeHolder.context = parent.getContext();
        context = parent.getContext();

        return  recipeHolder;
    }

    @Override
    public void onBindViewHolder(RecipeHolder holder, int position) {
        holder.name.setText(recipes.get(position).getName());
        holder.position = position;

        if (recipes.get(position).getIs_favorite()) {
            holder.layout.setBackground(ContextCompat.getDrawable(context, R.drawable.background_recipe_2));
        } else {
            holder.layout.setBackground(ContextCompat.getDrawable(context, R.drawable.background_recipe));
        }

        Picasso.get()
                .load(recipes.get(position).getImage_url())
                .placeholder(R.drawable.ic_default_image)
                .error(R.drawable.ic_default_image)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return recipes != null ? recipes.size() : 0;
    }
}
