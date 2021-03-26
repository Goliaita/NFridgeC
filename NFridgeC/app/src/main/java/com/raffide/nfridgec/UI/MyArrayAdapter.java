package com.raffide.nfridgec.UI;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.raffide.nfridgec.R;

import java.util.List;


class MyArrayAdapter extends RecyclerView.Adapter<MyArrayAdapter.ViewHolder> {


    private FragmentActivity context;


    private List<String> ProductName;
    private List<String> ProductQuantity;
    private List<String> ProductExpiryDate;



    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView productName;
        private TextView productQuantity;
        private TextView productExpiryDate;



        ViewHolder(View v, Context context) {

            super(v);

            if (context.getResources().getResourceEntryName(v.getId()).equals("card_view")) {

                productName = (TextView) v.findViewById(R.id.ProductName);

                productQuantity = (TextView) v.findViewById(R.id.ProductQuantity);


            } else {

                productName = (TextView) v.findViewById(R.id.NameProd);

                productQuantity = (TextView) v.findViewById(R.id.ManiProd);

                productExpiryDate = (TextView) v.findViewById(R.id.DateProd);

            }

        }

    }

    MyArrayAdapter(FragmentActivity context, List<String> ProductName, List<String> ProductQuantity,
                   @Nullable List<String> ProductExpiryDate) {

        this.ProductName = ProductName;
        this.ProductQuantity = ProductQuantity;
        this.ProductExpiryDate = ProductExpiryDate;
        this.context = context;


    }


    @Override
    public MyArrayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;
        ViewHolder vh;


        if (ProductExpiryDate == null){

            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_list, parent, false);

            vh = new ViewHolder(v, context);


            return vh;

        }else{

            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_def, parent, false);

            vh = new ViewHolder(v, context);

            return vh;


        }

    }

    @Override
    public void onBindViewHolder(MyArrayAdapter.ViewHolder holder, int position) {

        System.out.println(ProductExpiryDate);

        if (ProductExpiryDate == null){

            holder.productName.setText(ProductName.get(position));

            holder.productQuantity.setText(ProductQuantity.get(position));

        }else{

            holder.productName.setText(ProductName.get(position));

            holder.productQuantity.setText(ProductQuantity.get(position));

            holder.productExpiryDate.setText(ProductExpiryDate.get(position));

        }

    }

    @Override
    public int getItemCount() {
        return ProductName.size();
    }



}
