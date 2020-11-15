package com.example.foodfast;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodfast.Recipe.RecipeModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class RecipeDetailActivity extends AppCompatActivity {
    private ProgressBar progress_recipe_detail;
    private RecipeModel recipe;
    public int position;
    protected TextView text_name_recipe_detail, text_description_recipe_detail, text_ingredients_recipe_detail, text_steps_recipe_detail;
    protected ImageView button_back_recipe_detail, recipe_detail_image, button_favorite_recipe_detail;

    private String base_url = "https://foodfast.api-sact-test.com/";
    protected String retrieved_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("@FoodFast", Context.MODE_PRIVATE);
        retrieved_token = preferences.getString("TOKEN","");

        progress_recipe_detail = findViewById(R.id.progress_recipe_detail);

        position = getIntent().getIntExtra("EXTRA_RECIPE_POSITION", -1);

        text_name_recipe_detail = findViewById(R.id.text_name_recipe_detail);
        text_description_recipe_detail = findViewById(R.id.text_description_recipe_detail);
        text_ingredients_recipe_detail = findViewById(R.id.text_ingredients_recipe_detail);
        text_steps_recipe_detail = findViewById(R.id.text_steps_recipe_detail);

        button_back_recipe_detail = findViewById(R.id.button_back_recipe_detail);
        recipe_detail_image = findViewById(R.id.recipe_detail_image);
        button_favorite_recipe_detail = findViewById(R.id.button_favorite_recipe_detail);

        recipe = RecipesActivity.recipes_data_response.get(position);

        if (recipe != null) {
            text_name_recipe_detail.setText(recipe.getName());
            text_description_recipe_detail.setText(recipe.getDescription());
            text_ingredients_recipe_detail.setText(recipe.getIngredients());
            text_steps_recipe_detail.setText(recipe.getSteps());

            if (recipe.getIs_favorite()) {
                button_favorite_recipe_detail.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star_filled));
            }

            if (!recipe.getImage_url().equals("null")) {
                Picasso.get().load(recipe.getImage_url()).into(recipe_detail_image);
            }
        }

        button_back_recipe_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RecipesActivity.is_favorite && !recipe.getIs_favorite()) {
                    RecipesActivity.recipes_data_response.remove(position);
                    Objects.requireNonNull(RecipesActivity.recycler_view_recipes.getAdapter()).notifyItemRemoved(position);
                }

                finish();
            }
        });

        button_favorite_recipe_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recipe != null) {
                    if (recipe.getIs_favorite()) {
                        unfavorite_recipe();
                    } else {
                        favorite_recipe();
                    }
                }
            }
        });
    }
    
    private void unfavorite_recipe() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        show_progress();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.DELETE, base_url + "user-favorites/" + recipe.getFavorite_id(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                hide_progress();
                RecipesActivity.recipes_data_response.get(position).setIs_favorite(false);
                RecipesActivity.recipes_data_response.get(position).setFavorite_id("");

                recipe.setIs_favorite(false);
                recipe.setFavorite_id("");

                Objects.requireNonNull(RecipesActivity.recycler_view_recipes.getAdapter()).notifyItemChanged(position);

                button_favorite_recipe_detail.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star_outlined));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hide_progress();
                Toast.makeText(getApplicationContext(), "An error occurred to unfavorite the recipe", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + retrieved_token);
                return headers;
            }
        };

        requestQueue.add(jsObjRequest);
    }

    private void favorite_recipe() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        show_progress();

        Map<String, String> params = new HashMap<>();
        params.put("recipe_id", recipe.getId());

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, base_url + "user-favorites", parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                hide_progress();
                try {
                    RecipesActivity.recipes_data_response.get(position).setIs_favorite(true);
                    RecipesActivity.recipes_data_response.get(position).setFavorite_id(response.getString("id"));

                    recipe.setIs_favorite(true);
                    recipe.setFavorite_id(response.getString("id"));

                    Objects.requireNonNull(RecipesActivity.recycler_view_recipes.getAdapter()).notifyItemChanged(position);

                    button_favorite_recipe_detail.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star_filled));
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hide_progress();
                Toast.makeText(getApplicationContext(), "An error occurred to favorite the recipe", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + retrieved_token);
                return headers;
            }
        };

        requestQueue.add(jsObjRequest);}

    private void show_progress() {
        Animation animation_in_progress = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein_2);
        progress_recipe_detail.startAnimation(animation_in_progress);
        progress_recipe_detail.setVisibility(View.VISIBLE);
    }

    private void hide_progress() {
        Animation animation_out_progress = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeout_2);
        progress_recipe_detail.startAnimation(animation_out_progress);
        progress_recipe_detail.setVisibility(View.GONE);
    }
}
