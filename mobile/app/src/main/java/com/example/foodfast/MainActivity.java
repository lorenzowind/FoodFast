package com.example.foodfast;

import androidx.appcompat.app.AppCompatActivity;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RelativeLayout initial_screen_content;
    Button button_signin, button_signup, button_signup_popup, button_signin_popup;
    LinearLayout default_buttons, popup_signup, popup_signin;
    ImageView button_close_signup, button_close_signin;
    EditText name_signup, mail_signup, password_signup, password_signin;

    String base_url = "http://10.0.2.2:3333/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                    Toast.makeText(getApplicationContext(), "Preencha todos os dados", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getApplicationContext(), "Usuário criado com sucesso", Toast.LENGTH_SHORT).show();
                        button_close_signup.performClick();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Erro na criação de usuário", Toast.LENGTH_SHORT).show();
                        button_close_signup.performClick();
                    }
                });

                requestQueue.add(jsObjRequest);
            }
        });
    }
}