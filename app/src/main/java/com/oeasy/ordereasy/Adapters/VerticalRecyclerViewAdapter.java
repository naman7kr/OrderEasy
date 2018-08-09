package com.oeasy.ordereasy.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.oeasy.ordereasy.Interfaces.ItemTouchHelperAdapter;
import com.oeasy.ordereasy.Modals.FoodItem;
import com.oeasy.ordereasy.Others.Constants;
import com.oeasy.ordereasy.Others.DatabaseHelper;
import com.oeasy.ordereasy.Others.Utilities;
import com.oeasy.ordereasy.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by utkarshh12 on 4/11/2018.
 */

public class VerticalRecyclerViewAdapter extends RecyclerView.Adapter<VerticalRecyclerViewAdapter.MessageViewHolder> implements ItemTouchHelperAdapter{

    private ArrayList<FoodItem> foodItemArrayList;
    private Context context;
    private DatabaseHelper db;


    public VerticalRecyclerViewAdapter(Context context,ArrayList<FoodItem> foodItems) {
        this.foodItemArrayList=foodItems;
        this.context=context;
        db=new DatabaseHelper(context);
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
    public void onBindViewHolder(final MessageViewHolder holder, final int position) {
        final FoodItem fdItem=foodItemArrayList.get(position);
        final VerticalRecyclerViewAdapter.MessageViewHolder messageViewHolder =holder;
        messageViewHolder.fName.setText(fdItem.getName());
        messageViewHolder.fDescription.setText(fdItem.getDesc());
        setSpinner(holder.fSpinner,fdItem);
        if(foodItemArrayList.get(position).getTag()==0){
            messageViewHolder.fTag.setVisibility(View.VISIBLE);
            messageViewHolder.remBtn.setVisibility(View.VISIBLE);
            messageViewHolder.fSpinner.setVisibility(View.VISIBLE);
            messageViewHolder.fAlreadyPlaced.setVisibility(View.GONE);
        }else{
            messageViewHolder.fTag.setVisibility(View.GONE);
            messageViewHolder.remBtn.setVisibility(View.GONE);
            messageViewHolder.fSpinner.setVisibility(View.GONE);
            messageViewHolder.fAlreadyPlaced.setVisibility(View.VISIBLE);
        }
        messageViewHolder.remBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteFoodItem(fdItem.getFid());
                foodItemArrayList.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();
            }
        });
        if (fdItem.getImg() != null)
            Utilities.setPicassoImage(context, Constants.IMG_ROOT+fdItem.getImg(), holder.fImage, Constants.SQUA_PLACEHOLDER);
        messageViewHolder.fPrice.setText(String.valueOf(fdItem.getPrice()));

    }

    private void setSpinner(final Spinner sp, final FoodItem item) {

        ArrayAdapter<CharSequence> adapter=null;
        int arrayId;
        if(item.getQtyType()==0) {
            adapter = ArrayAdapter.createFromResource(context, R.array.Qty_for_desserts_and_drinks, android.R.layout.simple_spinner_item);
            arrayId=R.array.Qty_for_desserts_and_drinks;
        }
        else if(item.getQtyType()==1)
        {
            adapter=ArrayAdapter.createFromResource(context,R.array.Qty_for_maincourse_and_starters,android.R.layout.simple_spinner_item);
            arrayId=R.array.Qty_for_maincourse_and_starters;
        }
        else
        {
            adapter=ArrayAdapter.createFromResource(context,R.array.Qty_for_bread,android.R.layout.simple_spinner_item);
            arrayId=R.array.Qty_for_bread;
        }
        String [] quantity=context.getResources().getStringArray(arrayId);

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        for(int i=0;i<quantity.length;i++){
            String qty=quantity[i].replace("Qty ","");
            if(item.getQty().compareTo(qty)==0){
                sp.setSelection(i);
                break;
            }else{
                sp.setSelection(0);
            }
        }
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sQty= (String) sp.getSelectedItem();
                sQty=sQty.replace("Qty ","");
                db.updateFoodQty(item.getFid(),sQty);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return foodItemArrayList.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(foodItemArrayList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(foodItemArrayList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        db.deleteFoodItem(foodItemArrayList.get(position).getFid());
        foodItemArrayList.remove(position);
        notifyItemRemoved(position);

    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView fName;
        TextView fDescription;
        ImageView fImage;
        TextView fPrice;
        Spinner qtySp;
        TextView remBtn;
        ImageView fTag;
        Spinner fSpinner;
        TextView fAlreadyPlaced;
        public LinearLayout cLayout;
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
            remBtn=itemView.findViewById(R.id.cart_item_remove_btn);
            remBtn.setTextColor(Color.RED);
            fTag=itemView.findViewById(R.id.cart_item_tag);
            fSpinner=itemView.findViewById(R.id.cart_item_qty_spinner);
            fAlreadyPlaced=itemView.findViewById(R.id.cart_item_orderpresent);
            cLayout=itemView.findViewById(R.id.cart_item_layout);
        }
    }
}