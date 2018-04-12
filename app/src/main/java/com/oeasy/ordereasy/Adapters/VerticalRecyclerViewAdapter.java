package com.oeasy.ordereasy.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oeasy.ordereasy.Modals.FoodItem;
import com.oeasy.ordereasy.Modals.WaiterModel;
import com.oeasy.ordereasy.R;

import java.util.ArrayList;

/**
 * Created by utkarshh12 on 4/11/2018.
 */

public class VerticalRecyclerViewAdapter extends RecyclerView.Adapter<VerticalRecyclerViewAdapter.MessageViewHolder> {

    private ArrayList<FoodItem> foodItemArrayList;


    public VerticalRecyclerViewAdapter(ArrayList<FoodItem> foodItems) {
        this.foodItemArrayList=foodItems;
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
        messageViewHolder.textView.setText(foodItem.getName());
    }

    @Override
    public int getItemCount() {
        return foodItemArrayList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public MessageViewHolder(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.item_name);
        }
    }
}