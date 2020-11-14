package com.example.foodfast.Category;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodfast.R;
import com.example.foodfast.RecipesActivity;

import androidx.recyclerview.widget.RecyclerView;

public class CategoryHolder extends RecyclerView.ViewHolder {
    public Context context;
    public String id;
    public ImageView image;
    public TextView name;
    public ImageView button;

    public CategoryHolder(View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.category_image);
        name = itemView.findViewById(R.id.category_name);
        button = itemView.findViewById(R.id.category_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), RecipesActivity.class);
                intent.putExtra("EXTRA_CATEGORY_ID", id);
                intent.putExtra("EXTRA_CATEGORY_NAME", name.getText());

                ActivityOptions options = ActivityOptions.makeCustomAnimation(context, R.anim.lefttoright, R.anim.fadeout);
                context.startActivity(intent, options.toBundle());
            }
        });
    }
}
