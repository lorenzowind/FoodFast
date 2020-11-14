package com.example.foodfast.Recipe;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.foodfast.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeHolder> {
    private final ArrayList<RecipeModel> recipes;

    public RecipeAdapter(ArrayList<RecipeModel> recipes) {
        this.recipes = recipes;
    }

    @Override
    public RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecipeHolder recipeHolder =  new RecipeHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_item, parent, false));

        recipeHolder.context = parent.getContext();

        return  recipeHolder;
    }

    @Override
    public void onBindViewHolder(RecipeHolder holder, int position) {
        holder.name.setText(recipes.get(position).getName());
        holder.recipe = recipes.get(position);
    }

    @Override
    public int getItemCount() {
        return recipes != null ? recipes.size() : 0;
    }
}
