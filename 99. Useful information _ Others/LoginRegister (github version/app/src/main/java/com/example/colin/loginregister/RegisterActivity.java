package com.example.colin.loginregister;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //This line of code connects this java file to the xml file of the same name
        setContentView(R.layout.activity_register);

        //these connect to the buttons within that xml file
        final EditText etAge = (EditText) findViewById(R.id.etAge);
        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etUserName = (EditText) findViewById(R.id.etUserName);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);

        final Button bRegister = (Button) findViewById(R.id.bRegister);

        //this connects the register button to send the data in the fields above to the RegisterRequest.java class.
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //this is getting the values that the user has placed into the text areas and sending them to the RegisterRequest.java class.
                final String name = etName.getText().toString();
                final String username = etUserName.getText().toString();
                final String password = etPassword.getText().toString();
                final int age = Integer.parseInt(etAge.getText().toString());

                Response.Listener<String> responseListener = new Response.Listener<String>() {


                    public void onResponse(String response){
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            //if the registration is successful (recieves "success" from the register.php file), It will send the user to the login page.
                            if (success) {
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);

                                //else, display an error message ("Register Failed") and allow the user to try again (.setNegativeButton("Retry", null).
                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Register Failed, Username may be taken.")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                };

                //this is the code that connects the information above and sends it to the RegisterRequest.java class.
                RegisterRequest registerRequest = new RegisterRequest(name, username, age, password,responseListener );

                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);

                }


            });

    }
}
