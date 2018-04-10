package com.oeasy.ordereasy.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.oeasy.ordereasy.Modals.FoodItem;
import com.oeasy.ordereasy.Others.Constants;
import com.oeasy.ordereasy.Others.Utilities;
import com.oeasy.ordereasy.R;

import java.util.ArrayList;

/**
 * Created by Stan on 4/9/2018.
 */

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MyViewHolder> {
    private ArrayList<FoodItem> list;
    Context context;
    Dialog fDialog;
    public MenuItemAdapter(Context context, ArrayList<FoodItem> list){
        this.list=list;
        this.context=context;
    }
    @Override
    public MenuItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.menu_item_view,parent,false);
        return new MenuItemAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final FoodItem item=list.get(position);
        holder.fName.setText(item.getName());
        holder.fPrice.setText(String.valueOf(item.getPrice()));
        holder.fView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFoodDialog(context,item);

                Button addToCart=fDialog.findViewById(R.id.dialog_addbtn);
                addToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //store data in shared preference
                        fDialog.dismiss();
                    }
                });
            }
        });
    }
    public void showFoodDialog(Context context, FoodItem item){
        fDialog=new Dialog(context);
        WindowManager.LayoutParams lp=new WindowManager.LayoutParams();
        lp.copyFrom(fDialog.getWindow().getAttributes());
        lp.width=WindowManager.LayoutParams.MATCH_PARENT;
        lp.height=WindowManager.LayoutParams.MATCH_PARENT;
        fDialog.setContentView(R.layout.dialog_food);
        setDialogfContents(context,item,fDialog);
        fDialog.getWindow().getAttributes().windowAnimations=R.style.MyAnimation_Window;
        fDialog.show();
    }
    private void setDialogfContents(Context context, FoodItem item, Dialog fDialog) {
        TextView fName = fDialog.findViewById(R.id.dialog_name);
        ImageView fImg = fDialog.findViewById(R.id.dialog_fimage);
        TextView price=fDialog.findViewById(R.id.dialog_price);
        TextView desc=fDialog.findViewById(R.id.dialog_description);
        Spinner sp=fDialog.findViewById(R.id.dialog_sp_qty);
        setSpinner(sp);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        price.setText( String.valueOf(item.getPrice()));
        desc.setText(item.getDesc());
        setDialogImage(fImg);
        fName.setText(item.getName());
        Utilities.setPicassoImage(context, item.getImg(), fImg, Constants.SQUA_PLACEHOLDER);
    }
    private void setSpinner(Spinner sp) {
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(context,R.array.qty_pieces,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
    }
    public void setDialogImage(ImageView imgView) {

        WindowManager wm= (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        Log.e("WIndow", String.valueOf(wm.getDefaultDisplay().getHeight()));
        imgView.getLayoutParams().height=wm.getDefaultDisplay().getHeight()*(1)/3;
        imgView.getLayoutParams().width= WindowManager.LayoutParams.MATCH_PARENT;
        imgView.requestLayout();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView fName;
        TextView fPrice;
        ImageView fImg;
        LinearLayout fView;
        public MyViewHolder(View itemView) {
            super(itemView);
            fImg=itemView.findViewById(R.id.menu_item_img);
            fName=itemView.findViewById(R.id.menu_item_name);
            fPrice=itemView.findViewById(R.id.menu_item_price);
            fView=itemView.findViewById(R.id.menu_item_view);

        }
    }
}
