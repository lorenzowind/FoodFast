package com.example.foodfast.Recipe;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodfast.Category.CategoryModel;
import com.example.foodfast.R;
import com.example.foodfast.RecipeDetailActivity;
import com.google.gson.Gson;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeHolder extends RecyclerView.ViewHolder {
    public Context context;
    public RecipeModel recipe;

    public FrameLayout layout;
    public ImageView image;
    public TextView name;
    public ImageView button;

    public RecipeHolder(View itemView) {
        super(itemView);
        layout = itemView.findViewById(R.id.frame_recipe_model);
        image = itemView.findViewById(R.id.recipe_image);
        name = itemView.findViewById(R.id.recipe_name);
        button = itemView.findViewById(R.id.recipe_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String recipe_json = gson.toJson(recipe);

                Intent intent = new Intent(context.getApplicationContext(), RecipeDetailActivity.class);
                intent.putExtra("EXTRA_RECIPE", recipe_json);
                ActivityOptions options = ActivityOptions.makeCustomAnimation(context, R.anim.lefttoright, R.anim.fadeout);
                context.startActivity(intent, options.toBundle());
            }
        });
    }
}
