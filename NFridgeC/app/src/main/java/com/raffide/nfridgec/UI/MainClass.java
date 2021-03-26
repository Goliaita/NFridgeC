package com.raffide.nfridgec.UI;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.raffide.nfridgec.R;
import com.raffide.nfridgec.jerseyClient.GetTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class MainClass extends Activity{

    private EditText user;
    private EditText pass;
    public String Username;
    public String Password;


    private final String SERVER_IP= "http://192.168.1.5:8080/jersey/webapi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_class);

		/*
		 * Setup input fields
		 */

        user = (EditText)findViewById(R.id.idUsername);
        pass = (EditText)findViewById(R.id.password);
        Button button = (Button) findViewById(R.id.login);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Username = user.getText().toString();
                Password = pass.getText().toString();

				/*
				 * "message" is a string of type JSON Object, that is necessary to create the login query
				 * into server
				 */

                String[] list = new String[] {Username, Password};

				/*
				 * With the class Connection we call an async task method to process data and get information from
				 * the server
				 */
                GetTask task = new GetTask("Login", SERVER_IP, list) {

                    @Override
                    public void receiveData(Object object) {



                        try {


                            if (!object.toString().equals("")) {

                                final JSONObject obj = new JSONObject(object.toString());

                                System.out.println(obj.get("Result"));


                                if (Boolean.parseBoolean(obj.get("Result").toString())) {

                                    Intent intent = new Intent(MainClass.this, fridgeContent.class);

                                    intent.putExtra("UserId", obj.getString("UserId"));

                                    intent.putExtra("FridgeId", obj.getInt("FridgeId"));

                                    intent.putExtra("SERVER_IP", SERVER_IP);

                                    startActivity(intent);

                                }else{

                                    if (obj.getString("Error").equals("Connection")) {

                                        MainClass.this.runOnUiThread(new Runnable() {

                                            public void run() {

                                                Toast.makeText(MainClass.this, "Impossibile connettersi... riprovare ", Toast.LENGTH_LONG).show();

                                            }
                                        });

                                    } else {

                                        MainClass.this.runOnUiThread(new Runnable() {

                                            public void run() {

                                                Toast.makeText(MainClass.this, "Username e/o password errati ", Toast.LENGTH_LONG).show();

                                            }

                                        });

                                    }

                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                };

                task.execute();

                try {

                    task.get(10000, TimeUnit.MILLISECONDS);

                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();

                    MainClass.this.runOnUiThread(new Runnable() {

                        public void run() {

                            Toast.makeText(MainClass.this, "Impossibile connettersi... riprovare ", Toast.LENGTH_LONG).show();

                        }

                    });

                }

            }

        });

    }

}
