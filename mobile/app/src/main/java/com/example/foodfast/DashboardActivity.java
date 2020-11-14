package com.example.foodfast;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DashboardActivity extends AppCompatActivity {
    public ImageView button_menu_dashboard, button_close_menu;
    public RelativeLayout layout_menu, layout_dashboard;
    public TextView text_logout_menu, text_username_dashboard, text_email_menu, text_username_menu;

    public HorizontalScrollView frame_non_empty_categories;
    private RecyclerView recycler_view_categories;

    String base_url = "https://foodfast.api-sact-test.com/";
    String retrieved_user, retrieved_token;

    ArrayList<CategoryModel> categories_data_response = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        button_menu_dashboard = findViewById(R.id.button_menu_dashboard);
        button_close_menu = findViewById(R.id.button_close_menu);

        text_logout_menu = findViewById(R.id.text_logout_menu);
        text_username_dashboard = findViewById(R.id.text_username_dashboard);
        text_email_menu = findViewById(R.id.text_email_menu);
        text_username_menu = findViewById(R.id.text_username_menu);

        layout_menu = findViewById(R.id.layout_menu);
        layout_dashboard = findViewById(R.id.layout_dashboard);

        frame_non_empty_categories = findViewById(R.id.frame_non_empty_categories);

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

        text_logout_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("@FoodFast", Context.MODE_PRIVATE);
                preferences.edit().remove("TOKEN").apply();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void load_categories() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest jsObjRequest = new JsonArrayRequest(Request.Method.GET, base_url + "categories/all", null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int index=0; index<response.length(); index++){
                        JSONObject category = (JSONObject) response.get(index);

                        String image_url = "";
                        String name = category.getString("name");

                        if (category.has("image_url")) {
                            image_url = category.getString("image_url");
                        }

                        categories_data_response.add(new CategoryModel(image_url, name));
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
}
