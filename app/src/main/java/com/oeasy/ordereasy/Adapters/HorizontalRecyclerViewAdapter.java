package com.oeasy.ordereasy.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oeasy.ordereasy.Activities.CartActivity;
import com.oeasy.ordereasy.Modals.WaiterModel;
import com.oeasy.ordereasy.R;

import java.util.ArrayList;

/**
 * Created by utkarshh12 on 4/11/2018.
 */

public class HorizontalRecyclerViewAdapter extends RecyclerView.Adapter<HorizontalRecyclerViewAdapter.MessageViewHolder> {

    private ArrayList<WaiterModel> mArrayList;
    private Context context;
    public HorizontalRecyclerViewAdapter(Context context,ArrayList<WaiterModel> waiterModels) {
        this.mArrayList=waiterModels;
        this.context=context;
    }
    @Override
    public int getItemViewType(int position) {
        return  super.getItemViewType(position);
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View ItemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_waiter,parent,false);
        return new MessageViewHolder(ItemView);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        final WaiterModel waiterModel=mArrayList.get(position);
        MessageViewHolder messageViewHolder =holder;
        messageViewHolder.wName.setText(waiterModel.getName());

    }



    @Override
    public int getItemCount() {
        return mArrayList.size();
    }


    public class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView wName;
        private MessageViewHolder(View view) {
            super(view);
            wName=view.findViewById(R.id.cart_waiter_name);
        }
    }
}