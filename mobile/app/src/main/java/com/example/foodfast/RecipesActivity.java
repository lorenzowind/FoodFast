package com.example.foodfast;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodfast.Recipe.RecipeAdapter;
import com.example.foodfast.Recipe.RecipeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecipesActivity extends AppCompatActivity {
    private ProgressBar progress_recipes;
    protected ImageView button_back_recipes;
    protected TextView text_category_recipes, text_main_recipes, text_empty_recipes;
    public String category_id, category_name, search_recipe;

    protected LinearLayout frame_empty_recipes;
    protected NestedScrollView frame_non_empty_recipes;
    public static RecyclerView recycler_view_recipes;

    protected String base_url = "https://foodfast.api-sact-test.com/";
    protected String retrieved_token;

    public static ArrayList<RecipeModel> recipes_data_response = new ArrayList<>();
    private Integer previous_recipes_size = 0, current_page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("@FoodFast", Context.MODE_PRIVATE);
        retrieved_token = preferences.getString("TOKEN","");

        category_id = getIntent().getStringExtra("EXTRA_CATEGORY_ID");
        category_name = getIntent().getStringExtra("EXTRA_CATEGORY_NAME");
        search_recipe = getIntent().getStringExtra("EXTRA_SEARCH_RECIPE");

        progress_recipes = findViewById(R.id.progress_recipes);

        button_back_recipes = findViewById(R.id.button_back_recipes);

        text_category_recipes = findViewById(R.id.text_category_recipes);
        text_main_recipes = findViewById(R.id.text_main_recipes);
        text_empty_recipes = findViewById(R.id.text_empty_recipes);

        if (category_name != null) {
            text_category_recipes.setText(category_name);
            text_empty_recipes.setText("No recipes...");
        } else if (search_recipe != null) {
            text_category_recipes.setText("Search result: " + search_recipe);
            text_main_recipes.setText("Recipes");
        }

        frame_empty_recipes = findViewById(R.id.frame_empty_recipes);
        frame_non_empty_recipes = findViewById(R.id.frame_non_empty_recipes);

        recycler_view_recipes = findViewById(R.id.recycler_view_recipes);
        recycler_view_recipes.setLayoutManager(new LinearLayoutManager(this));

        load_recipes();

        button_back_recipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.righttoleft_2, R.anim.fadeout);
                startActivity(intent, options.toBundle());
            }
        });

        frame_non_empty_recipes.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                double sum = frame_non_empty_recipes.getHeight() + frame_non_empty_recipes.getScrollY();

                if (frame_non_empty_recipes.getChildAt(0).getBottom() <= sum && recipes_data_response.size() >= 10 && previous_recipes_size < recipes_data_response.size()) {
                    previous_recipes_size = recipes_data_response.size();
                    current_page++;
                    load_recipes();
                }
            }
        });
    }

    private void load_recipes() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        show_progress();

        String custom_url;

        if (search_recipe != null) {
            custom_url = base_url + "recipes/all" + "?page=" + current_page.toString() + "&search=" + search_recipe;
        } else {
            custom_url = base_url + "recipes/filtered/" + category_id + "?page=" + current_page.toString();
        }

        JsonArrayRequest jsObjRequest = new JsonArrayRequest(Request.Method.GET, custom_url, null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                hide_progress();
                try {
                    for (int index=0; index<response.length(); index++){
                        JSONObject recipe = (JSONObject) response.get(index);

                        String id = recipe.getString("id");
                        String name = recipe.getString("name");
                        String description = recipe.getString("description");
                        String ingredients = recipe.getString("ingredients");
                        String steps = recipe.getString("steps");
                        String video_url = recipe.getString("video_url");
                        String image_url = "";

                        if (recipe.has("image_url")) {
                            image_url = recipe.getString("image_url");
                        }

                        RecipeModel recipeModel = new RecipeModel(id, image_url, name, description, ingredients, steps, video_url);

                        boolean found = false;

                        for (RecipeModel aux_recipe: recipes_data_response) {
                            if (aux_recipe.getId().equals(recipeModel.getId())) {
                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            recipes_data_response.add(recipeModel);

                            if (previous_recipes_size != 0) {
                                Objects.requireNonNull(recycler_view_recipes.getAdapter()).notifyItemInserted(recipes_data_response.size() - 1);
                            }
                        }
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                }

                if (previous_recipes_size == 0) {
                    final RecipeAdapter adapter = new RecipeAdapter(recipes_data_response);
                    recycler_view_recipes.setAdapter(adapter);
                }

                if (recipes_data_response.size() == 0) {
                    Animation animation_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein_2);
                    Animation animation_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeout_2);
                    frame_empty_recipes.startAnimation(animation_in);
                    frame_non_empty_recipes.startAnimation(animation_out);
                    frame_empty_recipes.setVisibility(View.VISIBLE);
                    frame_non_empty_recipes.setVisibility(View.GONE);
                }

                load_favorite_recipes();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hide_progress();
                Toast.makeText(getApplicationContext(), "An error occurred to load the recipes", Toast.LENGTH_SHORT).show();
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

    private void load_favorite_recipes() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        show_progress();

        JsonArrayRequest jsObjRequest = new JsonArrayRequest(Request.Method.GET, base_url + "user-favorites", null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                hide_progress();
                try {
                    for (int index=0; index<response.length(); index++){
                        JSONObject user_favorite = (JSONObject) response.get(index);

                        for (int position=0; position<recipes_data_response.size(); position++) {
                            if (recipes_data_response.get(position).getId().equals(user_favorite.getString("recipe_id"))) {
                                recipes_data_response.get(position).setIs_favorite(true);
                                recipes_data_response.get(position).setFavorite_id(user_favorite.getString("id"));
                                Objects.requireNonNull(recycler_view_recipes.getAdapter()).notifyItemChanged(position);
                                break;
                            }
                        }
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hide_progress();
                Toast.makeText(getApplicationContext(), "An error occurred to load the user favorite recipes", Toast.LENGTH_SHORT).show();
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

    private void show_progress() {
        Animation animation_in_progress = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein_2);
        progress_recipes.startAnimation(animation_in_progress);
        progress_recipes.setVisibility(View.VISIBLE);
    }

    private void hide_progress() {
        Animation animation_out_progress = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeout_2);
        progress_recipes.startAnimation(animation_out_progress);
        progress_recipes.setVisibility(View.GONE);
    }
}
