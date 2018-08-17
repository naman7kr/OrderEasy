package com.oeasy.ordereasy.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.NumberPicker;
import android.widget.TextView;

import com.oeasy.ordereasy.Interfaces.ItemTouchHelperAdapter;
import com.oeasy.ordereasy.Modals.FoodItem;
import com.oeasy.ordereasy.Others.Constants;
import com.oeasy.ordereasy.Others.DatabaseHelper;
import com.oeasy.ordereasy.Others.Utilities;
import com.oeasy.ordereasy.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by utkarshh12 on 4/11/2018.
 */

public class VerticalRecyclerViewAdapter extends RecyclerView.Adapter<VerticalRecyclerViewAdapter.MessageViewHolder> implements ItemTouchHelperAdapter {

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
        messageViewHolder.qty.setText(fdItem.getQty());
        final String[] ar_dess_drinks=context.getResources().getStringArray( R.array.Qty_for_desserts_and_drinks);
        final String[] ar_main_start=context.getResources().getStringArray(R.array.Qty_for_maincourse_and_starters);
        final String[] ar_bread=context.getResources().getStringArray(R.array.Qty_for_bread);
        messageViewHolder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index;
                if(fdItem.getQtyType()==0){

                    index=Arrays.asList(ar_dess_drinks).indexOf(holder.qty.getText().toString());
                            if(index!=0){
                                holder.qty.setText(ar_dess_drinks[index-1]);
                            }
                        }else if(fdItem.getQtyType()==1){
                            index=Arrays.asList(ar_main_start).indexOf(holder.qty.getText().toString());
                    Log.e("in", String.valueOf(holder.qty.getText().toString()));
                            if(index!=0){
                                holder.qty.setText(ar_main_start[index-1]);
                            }
                        }else{
                            index=Arrays.asList(ar_bread).indexOf(holder.qty.getText().toString());
                            if(index!=0){
                                holder.qty.setText(ar_bread[index-1]);
                    }
                }
                fdItem.setQty(holder.qty.getText().toString());
                db.updateFoodQty(fdItem.getFid(),fdItem.getQty());
            }
        });
        messageViewHolder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index;
                if(fdItem.getQtyType()==0){
                    index=Arrays.asList(ar_dess_drinks).indexOf(holder.qty.getText().toString());
                    if(index!=ar_dess_drinks.length-1){
                        holder.qty.setText(ar_dess_drinks[index+1]);
                    }
                }else if(fdItem.getQtyType()==1){
                    index=Arrays.asList(ar_main_start).indexOf(holder.qty.getText().toString());
                    if(index!=ar_main_start.length-1){
                        holder.qty.setText(ar_main_start[index+1]);
                    }
                }else{
                    index=Arrays.asList(ar_bread).indexOf(holder.qty.getText().toString());
                    if(index!=ar_bread.length-1){
                        holder.qty.setText(ar_bread[index+1]);
                    }
                }
                fdItem.setQty(holder.qty.getText().toString());
                db.updateFoodQty(fdItem.getFid(),fdItem.getQty());
            }
        });
        messageViewHolder.qty.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                final AlertDialog mDialog = new AlertDialog.Builder(context)
                        .setTitle("Set Quantity")
                        .setView(R.layout.dialog_set_qty)
                        .create();
                mDialog.show();
                int width = (int)(context.getResources().getDisplayMetrics().widthPixels*0.80);
                int height = (int)(context.getResources().getDisplayMetrics().heightPixels*0.60);
                mDialog.getWindow().setLayout(width,height);


                final NumberPicker picker=mDialog.findViewById(R.id.qty_picker);
                Button set=mDialog.findViewById(R.id.dialog_setqty_set);
                Button cancel=mDialog.findViewById(R.id.dialog_setqty_cancel);

                set.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(fdItem.getQtyType()==0){
                            messageViewHolder.qty.setText(ar_dess_drinks[picker.getValue()]);
                        }else if(fdItem.getQtyType()==1){
                            messageViewHolder.qty.setText(ar_main_start[picker.getValue()]);
                        }else{
                            messageViewHolder.qty.setText(ar_bread[picker.getValue()]);
                        }
                        fdItem.setQty(messageViewHolder.qty.getText().toString());
                        db.updateFoodQty(fdItem.getFid(),fdItem.getQty());
                        mDialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialog.dismiss();
                    }
                });

                if(fdItem.getQtyType()==0){
                    picker.setMaxValue(ar_dess_drinks.length-1);
                    picker.setMinValue(0);
                    picker.setWrapSelectorWheel(false);
                    picker.setDisplayedValues(ar_dess_drinks);
                    picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                }else if(fdItem.getQtyType()==1){
                    picker.setMaxValue(ar_main_start.length-1);
                    picker.setMinValue(0);
                    picker.setWrapSelectorWheel(false);
                    picker.setDisplayedValues(ar_main_start);
                    picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                }else{
                    picker.setMaxValue(ar_bread.length-1);
                    picker.setMinValue(0);
                    picker.setWrapSelectorWheel(false);
                    picker.setDisplayedValues(ar_bread);
                    picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                }

            }
        });

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
        Button remBtn;
        Button plus,minus,qty;
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
            remBtn=itemView.findViewById(R.id.cart_item_remove_btn);
            plus=itemView.findViewById(R.id.cart_qty_plus);
            minus=itemView.findViewById(R.id.cart_qty_minus);
            qty=itemView.findViewById(R.id.cart_qty);

            cLayout=itemView.findViewById(R.id.cart_item_layout);
        }
    }
}