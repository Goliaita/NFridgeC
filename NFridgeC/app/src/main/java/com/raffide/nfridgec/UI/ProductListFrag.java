package com.raffide.nfridgec.UI;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raffide.nfridgec.R;
import com.raffide.nfridgec.jerseyClient.GetTask;

import org.apache.commons.collections4.CollectionUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ProductListFrag extends Fragment {

    private String SavingJson;

    private static final String SAVE_STATE = "STATE";

    private List<String> productNameList;
    private List<String> productQuantityList;

    private int fridgeId;


    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        try {

			/*
			 * With JSON array we list all product into an array
			 */

            fridgeId = getArguments().getInt("FridgeId");

            JSONObject obj;

			if(savedInstanceState !=  null) {

                obj = new JSONObject(savedInstanceState.getString(SAVE_STATE));

            }else{

                obj = new JSONObject(getArguments().getString("listaProdotti"));

            }

            JSONArray jsonArrayName = obj.getJSONArray("product");

            SavingJson = obj.toString();

            productNameList = new ArrayList<>();
            productQuantityList = new ArrayList<>();

            for (int i = 0; i < jsonArrayName.length(); i++) {

                JSONObject jsonobject = new JSONObject(jsonArrayName.get(i).toString());

                productNameList.add(jsonobject.getString("Categoria"));

                productQuantityList.add(jsonobject.getString("ProductQuantity"));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, final Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_view_prod_list, null);

        final RecyclerView mRecyclerView = (RecyclerView) v.findViewById(R.id.ProductList);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);

        final MyArrayAdapter adapter = new MyArrayAdapter(getActivity(), productNameList,
                productQuantityList, null);

        mRecyclerView.setAdapter(adapter);


        final SwipeRefreshLayout refresh = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                refreshItem(getArguments(), refresh, adapter);

            }

        });

        final FragmentActivity act = getActivity();

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(act, mRecyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {

                        String[] list = new String[]{productNameList.get(position),
                                "" + fridgeId};


                        GetTask task = new GetTask("getProductDef",
                                getArguments().getString("SERVER_IP"), list) {

                            @Override
                            public void receiveData(Object object) {

                                if (!object.toString().equals("")) {

                                    ProductDefFrag frag = new ProductDefFrag();

                                    Bundle args = new Bundle();

                                    args.putString("ProductDef", object.toString());

                                    frag.setArguments(args);

                                    FragmentTransaction transaction = act.getSupportFragmentManager()
                                                .beginTransaction();
                                    transaction.addToBackStack(null);
                                    transaction.replace(R.id.ListFrag, frag).commit();

                                }

                            }

                        };

                        task.execute();

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        return v;

    }


    private void refreshItem(final Bundle bundle, final SwipeRefreshLayout refresh,
                                                        final MyArrayAdapter adapter){

        String[] list = new String[]{"" + bundle.getInt("FridgeId")};

        GetTask task = new GetTask("getFridgeContent", bundle.getString("SERVER_IP"), list){

            @Override
            public void receiveData(final Object object) {

                try {

                    if (!object.toString().equals("")) {

                        JSONArray arr = new JSONObject(object.toString()).getJSONArray("product");

                        JSONArray oldArr = new JSONObject(SavingJson).getJSONArray("product");

                        if (arr.length() != oldArr.length()) {

                            SavingJson = object.toString();

                            List<String> listCat = new ArrayList<>();

                            for (int i = 0; i < arr.length(); i++){
                                listCat.add(arr.getString(i));
                            }

                            List<String> listQuant = new ArrayList<>();

                            for (int i = 0; i < oldArr.length(); i++){
                                listQuant.add(oldArr.getString(i));
                            }

                            if(arr.length() > oldArr.length()) {

                                List<String> res = new ArrayList<>
                                        (CollectionUtils.disjunction(listCat, listQuant));

                                for (int i = 0; i < res.size(); i++) {

                                    productNameList.add(
                                            new JSONObject(res.get(i)).getString("Categoria"));


                                    productQuantityList.add(
                                            new JSONObject(res.get(i)).getString("ProductQuantity"));

                                    adapter.notifyItemInserted(productNameList.size());

                                }

                            }else{

                                List<String> res = new ArrayList<>
                                        (CollectionUtils.disjunction(listQuant, listCat));

                                for(int k = 0; k < res.size(); k++){

                                    for(int i = 0; i < productNameList.size(); i++) {

                                        System.out.println(productNameList.get(i) + " " + res.get(k));

                                        if (productNameList.get(i).equals(
                                                new JSONObject(res.get(k)).getString("Categoria"))){

                                            productNameList.remove(i);

                                            productQuantityList.remove(i);

                                            adapter.notifyItemRemoved(i);

                                        }

                                    }

                                }

                            }

                        }

                        refresh.setRefreshing(false);

                    }

                } catch(JSONException e){
                    e.printStackTrace();
                }

            }

        };

        task.execute();

    }


    @Override
    public void onSaveInstanceState(Bundle outState){

        super.onSaveInstanceState(outState);

        outState.putString(SAVE_STATE, SavingJson);
    }

}
