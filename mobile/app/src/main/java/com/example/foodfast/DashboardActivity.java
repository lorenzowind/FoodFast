package com.example.foodfast;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {
    ImageView button_menu_dashboard, button_close_menu;
    RelativeLayout layout_menu, layout_dashboard;
    TextView text_logout_menu, text_username_dashboard, text_email_menu, text_username_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);;
        setContentView(R.layout.activity_dashboard);

        button_menu_dashboard = findViewById(R.id.button_menu_dashboard);
        button_close_menu = findViewById(R.id.button_close_menu);

        text_logout_menu = findViewById(R.id.text_logout_menu);
        text_username_dashboard = findViewById(R.id.text_username_dashboard);
        text_email_menu = findViewById(R.id.text_email_menu);
        text_username_menu = findViewById(R.id.text_username_menu);

        layout_menu = findViewById(R.id.layout_menu);
        layout_dashboard = findViewById(R.id.layout_dashboard);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("@FoodFast",Context.MODE_PRIVATE);
        String retrived_user  = preferences.getString("USER","");

        try {
            JSONObject user = new JSONObject(retrived_user);

            text_username_dashboard.setText("Hi " + user.getString("name"));
            text_email_menu.setText(user.getString("email"));
            text_username_menu.setText(user.getString("name"));
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();
        }

        button_menu_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.lefttoright);
                Animation animation_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeout);
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
}
