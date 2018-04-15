package com.oeasy.ordereasy.Adapters;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oeasy.ordereasy.Modals.FoodItem;
import com.oeasy.ordereasy.R;

import java.util.ArrayList;

/**
 * Created by Stan on 4/15/2018.
 */

public class CartPlaceOrderAdapter extends RecyclerView.Adapter<CartPlaceOrderAdapter.MyViewHolder> {
    private ArrayList<FoodItem> items;
    private Context context;
    public CartPlaceOrderAdapter(Context context, ArrayList<FoodItem> items){
        this.items=items;
        this.context=context;
    }
    @Override
    public CartPlaceOrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.cart_place_order_item,parent,false);
        return new CartPlaceOrderAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartPlaceOrderAdapter.MyViewHolder holder, int position) {
        FoodItem current=items.get(position);
        holder.fName.setText(current.getName());
        holder.fQty.setText(current.getQty());
        holder.fCost.setText(String.valueOf(current.getPrice()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView fName;
        TextView fQty;
        TextView fCost;
        public MyViewHolder(View itemView) {
            super(itemView);
            fName=itemView.findViewById(R.id.place_order_item_name);
            fQty=itemView.findViewById(R.id.place_order_item_quantity);
            fCost=itemView.findViewById(R.id.place_order_item_cost);
        }
    }
}
