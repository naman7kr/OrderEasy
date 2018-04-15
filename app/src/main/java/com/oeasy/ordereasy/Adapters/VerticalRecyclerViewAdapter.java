package com.oeasy.ordereasy.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.oeasy.ordereasy.Modals.FoodItem;
import com.oeasy.ordereasy.Modals.WaiterModel;
import com.oeasy.ordereasy.Others.Constants;
import com.oeasy.ordereasy.Others.Utilities;
import com.oeasy.ordereasy.R;

import java.util.ArrayList;

/**
 * Created by utkarshh12 on 4/11/2018.
 */

public class VerticalRecyclerViewAdapter extends RecyclerView.Adapter<VerticalRecyclerViewAdapter.MessageViewHolder> {

    private ArrayList<FoodItem> foodItemArrayList;
    private Context context;

    public VerticalRecyclerViewAdapter(Context context,ArrayList<FoodItem> foodItems) {
        this.foodItemArrayList=foodItems;
        this.context=context;
    }

    @Override
    public int getItemViewType(int position) {
        return  super.getItemViewType(position);
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View ItemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false);
        return new VerticalRecyclerViewAdapter.MessageViewHolder(ItemView);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        final FoodItem foodItem=foodItemArrayList.get(position);
        VerticalRecyclerViewAdapter.MessageViewHolder messageViewHolder =holder;
        messageViewHolder.fName.setText(foodItem.getName());
        messageViewHolder.fDescription.setText(foodItem.getDesc());
        if (foodItem.getImg() != null)
            Utilities.setPicassoImage(context, Constants.IMG_ROOT+foodItem.getImg(), holder.fImage, Constants.SQUA_PLACEHOLDER);
        messageViewHolder.fPrice.setText(String.valueOf(foodItem.getPrice()));
    }

    @Override
    public int getItemCount() {
        return foodItemArrayList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView fName;
        TextView fDescription;
        ImageView fImage;
        TextView fPrice;
        Spinner qtySp;
        public MessageViewHolder(View itemView) {
            super(itemView);
            initialize(itemView);
        }

        private void initialize(View itemView) {
            fName=itemView.findViewById(R.id.cart_item_name);
            fDescription=itemView.findViewById(R.id.cart_item_description);
            fImage=itemView.findViewById(R.id.cart_item_img);
            fPrice=itemView.findViewById(R.id.cart_item_price);
            qtySp=itemView.findViewById(R.id.cart_item_qty_spinner);
        }
    }
}