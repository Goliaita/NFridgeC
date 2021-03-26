package com.raffide.nfridgec.UI;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.raffide.nfridgec.R;

public class fridgeContent2 extends FragmentActivity {


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fridge_content2);


        if(findViewById(R.id.ListFrag) != null) {

            if (savedInstanceState != null) {
                return;
            }

            ProductListFrag firstFragment = new ProductListFrag();

            firstFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.ListFrag, firstFragment).commit();

        }

    }


}

