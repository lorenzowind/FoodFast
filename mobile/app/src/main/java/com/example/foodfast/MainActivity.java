package com.example.foodfast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    RelativeLayout initial_screen_content;
    Button button_signin, button_signup;
    LinearLayout default_buttons, popup_signup, popup_signin;
    ImageView button_close_signup, button_close_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initial_screen_content = findViewById(R.id.initial_screen_content);

        Animation animation_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
        initial_screen_content.startAnimation(animation_in);

        button_signin = findViewById(R.id.button_signin);
        button_signup = findViewById(R.id.button_signup);

        default_buttons = findViewById(R.id.default_buttons);
        popup_signup = findViewById(R.id.popup_signup);
        popup_signin = findViewById(R.id.popup_signin);

        button_close_signup = findViewById(R.id.button_close_signup);
        button_close_signin = findViewById(R.id.button_close_signin);

        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bottomtotop);
                Animation animation_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeout);
                popup_signup.startAnimation(animation_in);
                default_buttons.startAnimation(animation_out);
                popup_signup.setVisibility(View.VISIBLE);
                default_buttons.setVisibility(View.GONE);
            }
        });

        button_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bottomtotop);
                Animation animation_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeout);
                popup_signin.startAnimation(animation_in);
                default_buttons.startAnimation(animation_out);
                popup_signin.setVisibility(View.VISIBLE);
                default_buttons.setVisibility(View.GONE);
            }
        });

        button_close_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.toptobottom);
                Animation animation_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
                popup_signup.startAnimation(animation_out);
                default_buttons.startAnimation(animation_in);
                popup_signup.setVisibility(View.GONE);
                default_buttons.setVisibility(View.VISIBLE);
            }
        });

        button_close_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.toptobottom);
                Animation animation_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
                popup_signin.startAnimation(animation_out);
                default_buttons.startAnimation(animation_in);
                popup_signin.setVisibility(View.GONE);
                default_buttons.setVisibility(View.VISIBLE);
            }
        });
    }
}