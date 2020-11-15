package com.example.foodfast;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodfast.Category.CategoryAdapter;
import com.example.foodfast.Category.CategoryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DashboardActivity extends AppCompatActivity {
    private ProgressBar progress_dashboard;
    protected ImageView button_menu_dashboard, button_close_menu, button_search_dashboard;
    private LinearLayout option_favorite_menu;
    private RelativeLayout layout_menu, layout_dashboard;
    protected TextView text_logout_menu, text_username_dashboard, text_email_menu, text_username_menu;
    protected EditText search_dashboard;

    protected NestedScrollView frame_non_empty_categories;
    private RecyclerView recycler_view_categories;

    private String base_url = "https://foodfast.api-sact-test.com/";
    protected String retrieved_user, retrieved_token;

    private ArrayList<CategoryModel> categories_data_response = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        progress_dashboard = findViewById(R.id.progress_dashboard);

        button_menu_dashboard = findViewById(R.id.button_menu_dashboard);
        button_close_menu = findViewById(R.id.button_close_menu);
        button_search_dashboard = findViewById(R.id.button_search_dashboard);

        search_dashboard = findViewById(R.id.search_dashboard);
        text_logout_menu = findViewById(R.id.text_logout_menu);
        text_username_dashboard = findViewById(R.id.text_username_dashboard);
        text_email_menu = findViewById(R.id.text_email_menu);
        text_username_menu = findViewById(R.id.text_username_menu);

        layout_menu = findViewById(R.id.layout_menu);
        layout_dashboard = findViewById(R.id.layout_dashboard);

        frame_non_empty_categories = findViewById(R.id.frame_non_empty_categories);
        option_favorite_menu = findViewById(R.id.option_favorite_menu);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("@FoodFast",Context.MODE_PRIVATE);
        retrieved_user = preferences.getString("USER","");
        retrieved_token = preferences.getString("TOKEN","");

        recycler_view_categories = findViewById(R.id.recycler_view_categories);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycler_view_categories.setLayoutManager(horizontalLayoutManager);

        try {
            JSONObject user = new JSONObject(retrieved_user);

            text_username_dashboard.setText("Hi " + user.getString("name"));
            text_email_menu.setText(user.getString("email"));
            text_username_menu.setText(user.getString("name"));
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();
        }

        load_categories();

        button_menu_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.lefttoright);
                Animation animation_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeout_2);
                layout_menu.startAnimation(animation_in);
                layout_dashboard.startAnimation(animation_out);
                layout_menu.setVisibility(View.VISIBLE);
                layout_dashboard.setVisibility(View.GONE);
            }
        });

        button_close_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
                Animation animation_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.righttoleft);
                layout_dashboard.startAnimation(animation_in);
                layout_menu.startAnimation(animation_out);
                layout_dashboard.setVisibility(View.VISIBLE);
                layout_menu.setVisibility(View.GONE);
            }
        });

        button_search_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (search_dashboard.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Invalid search input", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), RecipesActivity.class);
                intent.putExtra("EXTRA_SEARCH_RECIPE", search_dashboard.getText().toString());

                ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.lefttoright, R.anim.fadeout);
                startActivity(intent, options.toBundle());
            }
        });

        text_logout_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("@FoodFast", Context.MODE_PRIVATE);
                preferences.edit().remove("TOKEN").apply();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        option_favorite_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecipesActivity.class);
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.lefttoright, R.anim.fadeout);
                startActivity(intent, options.toBundle());
            }
        });
    }

    private void load_categories() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        show_progress();

        JsonArrayRequest jsObjRequest = new JsonArrayRequest(Request.Method.GET, base_url + "categories/all", null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                hide_progress();
                try {
                    for (int index=0; index<response.length(); index++){
                        JSONObject category = (JSONObject) response.get(index);

                        String id = category.getString("id");
                        String name = category.getString("name");
                        String image_url = "";

                        if (category.has("image_url")) {
                            image_url = category.getString("image_url");
                        }

                        categories_data_response.add(new CategoryModel(id, image_url, name));
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                }

                final CategoryAdapter adapter = new CategoryAdapter(categories_data_response);
                recycler_view_categories.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hide_progress();
                Toast.makeText(getApplicationContext(), "An error occurred to load the categories", Toast.LENGTH_SHORT).show();
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
        progress_dashboard.startAnimation(animation_in_progress);
        progress_dashboard.setVisibility(View.VISIBLE);
    }

    private void hide_progress() {
        Animation animation_out_progress = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeout_2);
        progress_dashboard.startAnimation(animation_out_progress);
        progress_dashboard.setVisibility(View.GONE);
    }
}
