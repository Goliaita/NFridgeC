package com.raffide.nfridgec.UI;


import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raffide.nfridgec.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ProductDefFrag extends Fragment {

    private String SavingJson;

    private static final String SAVE_STATE = "STATE";

    private List<String> productNameList = new ArrayList<>();
    private List<String> productManifacturerList = new ArrayList<>();
    private List<String> productExpiryDateList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);



        try {

            JSONObject obj;

            if (savedInstanceState != null) {

                obj = new JSONObject(savedInstanceState.getString(SAVE_STATE));

            }else {

                obj = new JSONObject(getArguments().getString("ProductDef"));

            }

            JSONArray jsonArrayName = obj.getJSONArray("product");

            SavingJson = obj.toString();

            for (int i = 0; i < jsonArrayName.length(); i++) {

                JSONObject jsonobject = new JSONObject(jsonArrayName.get(i).toString());

                productNameList.add(jsonobject.getString("ProductName"));

                productManifacturerList.add(jsonobject.getString("ProductManifacturer"));

                productExpiryDateList.add(jsonobject.getString("ExpryDate"));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frag_prod_def, null);

        RecyclerView mRecyclerView = (RecyclerView) v.findViewById(R.id.ProductDefCard);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(new MyArrayAdapter(getActivity(), productNameList,
                productManifacturerList, productExpiryDateList));

        return v;

    }


    @Override
    public void onSaveInstanceState(Bundle outState){

        super.onSaveInstanceState(outState);

        outState.putString(SAVE_STATE, SavingJson);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

}
