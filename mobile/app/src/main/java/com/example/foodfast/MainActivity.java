package com.example.foodfast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    RelativeLayout initial_screen_content;
    Button button_signin, button_signup, button_signup_popup, button_signin_popup;
    LinearLayout default_buttons, popup_signup, popup_signin;
    ImageView button_close_signup, button_close_signin;
    EditText name_signup, mail_signup, password_signup, mail_signin, password_signin;

    String base_url = "http://ec2-18-222-144-152.us-east-2.compute.amazonaws.com/";

    JSONObject auth_data_response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("@FoodFast",Context.MODE_PRIVATE);
        String retrived_token  = preferences.getString("TOKEN","");

        if (!retrived_token.equals("")) {
            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
            startActivity(intent);
        }

        initial_screen_content = findViewById(R.id.initial_screen_content);

        Animation animation_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
        initial_screen_content.startAnimation(animation_in);

        button_signin = findViewById(R.id.button_signin);
        button_signup = findViewById(R.id.button_signup);
        button_signup_popup = findViewById(R.id.button_signup_popup);
        button_signin_popup = findViewById(R.id.button_signin_popup);

        default_buttons = findViewById(R.id.default_buttons);
        popup_signup = findViewById(R.id.popup_signup);
        popup_signin = findViewById(R.id.popup_signin);

        button_close_signup = findViewById(R.id.button_close_signup);
        button_close_signin = findViewById(R.id.button_close_signin);

        name_signup = findViewById(R.id.name_signup);
        mail_signup = findViewById(R.id.mail_signup);
        password_signup = findViewById(R.id.password_signup);

        mail_signin = findViewById(R.id.mail_signin);
        password_signin = findViewById(R.id.password_signin);

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

        button_signup_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = name_signup.getText().toString();
                String email = mail_signup.getText().toString();
                String password = password_signup.getText().toString();

                if (name.equals("") || email.equals("") || password.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fill in all blanks", Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, String> params = new HashMap();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);

                JSONObject parameters = new JSONObject(params);

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, base_url + "users", parameters, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "User created successfully", Toast.LENGTH_SHORT).show();
                        button_close_signup.performClick();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "An error occurred to sign up", Toast.LENGTH_SHORT).show();
                        button_close_signup.performClick();
                    }
                });

                requestQueue.add(jsObjRequest);
            }
        });

        button_signin_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mail_signin.getText().toString();
                String password = password_signin.getText().toString();

                if (email.equals("") || password.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fill all blank spaces", Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, String> params = new HashMap();
                params.put("email", email);
                params.put("password", password);

                JSONObject parameters = new JSONObject(params);

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, base_url + "sessions", parameters, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            auth_data_response = new JSONObject(response.toString());

                            SharedPreferences preferences = getApplicationContext().getSharedPreferences("@FoodFast", Context.MODE_PRIVATE);
                            preferences.edit().putString("TOKEN", auth_data_response.getString("token")).apply();
                            preferences.edit().putString("USER", auth_data_response.getString("user")).apply();

                            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                        }
                        button_close_signin.performClick();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "An error occurred to sign in", Toast.LENGTH_SHORT).show();
                        button_close_signin.performClick();
                    }
                });
//                {
//                    @Override
//                    public Map<String, String> getHeaders() throws AuthFailureError {
//                        Map<String, String> headers = new HashMap();
//                        headers.put("Authorization", "Bearer " + accesstoken);
//                        return headers;
//                    }
//                };

                requestQueue.add(jsObjRequest);
            }
        });
    }
}