package com.ashrafazmi.smartcart;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ashraf on 11/8/2017.
 */

public class RegisterActivitty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etfullname = (EditText) findViewById(R.id.fullname);
        final EditText etusername = (EditText) findViewById(R.id.username);
        final EditText etemail = (EditText) findViewById(R.id.email);
        final EditText etcode = (EditText) findViewById(R.id.etcode);
        final Button bRegister = (Button) findViewById(R.id.nextbtn);


        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = etfullname.getText().toString().trim();
                String user = etusername.getText().toString().trim();
                final String email = etemail.getText().toString().trim();
                String code = etcode.getText().toString().trim();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Intent intent = new Intent(RegisterActivitty.this, SignUp.class);
                                intent.putExtra("email", email);
                                RegisterActivitty.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivitty.this);
                                builder.setMessage("Register Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                RegisterRequest RegisterRequest = new RegisterRequest(fullname, user, email, code, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivitty.this);
                queue.add(RegisterRequest);

            }

        });

    }
}
