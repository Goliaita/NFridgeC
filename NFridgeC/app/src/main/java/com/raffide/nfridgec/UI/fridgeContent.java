package com.raffide.nfridgec.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.raffide.nfridgec.R;
import com.raffide.nfridgec.jerseyClient.GetTask;


public class fridgeContent extends Activity {

    Bundle bundle;
    Button button;

    private ProgressBar mProgress;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.fridge_content);

        button = (Button) findViewById(R.id.button1);

        bundle = getIntent().getExtras();

        String[] list = new String[] {"" + bundle.getInt("FridgeId")};

        GetTask task = new GetTask("getFridgeContent", bundle.getString("SERVER_IP"), list) {

            @Override
            public void receiveData(final Object object) {

                mProgress = (ProgressBar) findViewById(R.id.progressbar);

                if(!object.toString().equals("")) {

                    mProgress.setVisibility(View.INVISIBLE);

                }

                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        if(!object.equals("")) {

                            Intent intent = new Intent(fridgeContent.this, fridgeContent2.class);

                            intent.putExtra("listaProdotti", object.toString());

                            intent.putExtra("FridgeId", bundle.getInt("FridgeId"));

                            intent.putExtra("SERVER_IP", bundle.getString("SERVER_IP"));

                            startActivity(intent);

                        }

                    }
                });

            }

        };

        task.execute();

    }

}


