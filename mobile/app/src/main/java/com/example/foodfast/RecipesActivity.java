package com.example.foodfast;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class RecipesActivity extends AppCompatActivity {
    ImageView button_back_recipes;
    String category_id, category_name;

    String base_url = "https://foodfast.api-sact-test.com/";

    ArrayList<CategoryModel> recipes_data_response = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        category_id = getIntent().getStringExtra("EXTRA_CATEGORY_ID");
        category_name = getIntent().getStringExtra("EXTRA_CATEGORY_NAME");

        button_back_recipes = findViewById(R.id.button_back_recipes);

        button_back_recipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.righttoleft_2, R.anim.fadeout);
                startActivity(intent, options.toBundle());
            }
        });
    }
}
