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

public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.DialogHolder>{
    ArrayList<FoodItem> mList;
    Context context;
    public DialogAdapter(Context context, ArrayList<FoodItem> list){
        mList=list;
        this.context=context;

    }
    @Override
    public DialogHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.dialog_place_order_item,parent,false);
        return new DialogHolder(view);
    }

    @Override
    public void onBindViewHolder(DialogHolder holder, int position) {
        FoodItem item=mList.get(position);
        holder.mName.setText(item.getName());
        holder.mQty.setText(item.getQty());
        item.setCost(String.valueOf((item.getPrice())*Float.parseFloat(item.getQty().replace("Qty ",""))));
        holder.mPrice.setText(item.getCost());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class DialogHolder extends RecyclerView.ViewHolder {
        TextView mName;
        TextView mQty;
        TextView mPrice;
        public DialogHolder(View itemView) {
            super(itemView);
            mName=itemView.findViewById(R.id.dialog_place_order_item_name);
            mQty=itemView.findViewById(R.id.dialog_place_order_item_quantity);
            mPrice=itemView.findViewById(R.id.dialog_place_order_item_cost);
        }
    }
}
