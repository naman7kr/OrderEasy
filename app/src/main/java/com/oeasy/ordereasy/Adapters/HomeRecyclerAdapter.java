package com.oeasy.ordereasy.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.oeasy.ordereasy.Modals.FoodItem;
import com.oeasy.ordereasy.Others.Constants;
import com.oeasy.ordereasy.Others.DatabaseHelper;
import com.oeasy.ordereasy.Others.Utilities;
import com.oeasy.ordereasy.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Stan on 4/5/2018.
 */
public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<FoodItem> items;
    private int type;
    Dialog fDialog;
    private ImageView dialogImage;
    DatabaseHelper db;
    int alreadyPresent;
    Spinner sp;
    private TextView aldyPresent;
    public HomeRecyclerAdapter(Context context, ArrayList<FoodItem> items) {
        this.items=items;
        this.context=context;
        this.type=type;
        db = new DatabaseHelper(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.home_item_view,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final FoodItem current = items.get(position);
        if (current.getImg() != null)
            Utilities.setPicassoImage(context, Constants.IMG_ROOT+current.getImg(), holder.fImg, Constants.SQUA_PLACEHOLDER);
        holder.fName.setText(current.getName());

        holder.fView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showFoodDialog(context,current);
                alreadyPresent=db.countFoodItem(current);
                if(alreadyPresent==0){
                    aldyPresent.setVisibility(View.GONE);
                }else{
                    aldyPresent.setVisibility(View.VISIBLE);
                }
                Log.e("FCOUNT", String.valueOf(alreadyPresent));
                Button addToCart=fDialog.findViewById(R.id.dialog_addbtn);
                addToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String spItem= (String) sp.getSelectedItem();
                        if(alreadyPresent==0){
                        current.setQty(spItem);
                        db.createFoodItems(current);
                        }
                        else{
                            db.updateFoodQty(current.getFid(),spItem);
                        }
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
        aldyPresent=fDialog.findViewById(R.id.dialog_alpresent);
        TextView price=fDialog.findViewById(R.id.dialog_price);
        TextView desc=fDialog.findViewById(R.id.dialog_description);
        sp=fDialog.findViewById(R.id.dialog_sp_qty);
        if (item.getImg() != null) {
            Picasso.with(context).load(Constants.IMG_ROOT+item.getImg()).into(fImg);
                  Log.e("LOL",Constants.IMG_ROOT+item.getImg());
        }
        setSpinner(sp,item);

        price.setText( String.valueOf(item.getPrice()));
        desc.setText(item.getDesc());
        setDialogImage(fImg);
        fName.setText(item.getName());
    }

    private void setSpinner(Spinner sp, FoodItem item) {
        ArrayAdapter<CharSequence> adapter=null;
        if(item.getQtyType()==0)
        adapter=ArrayAdapter.createFromResource(context,R.array.Qty_for_desserts_and_drinks,android.R.layout.simple_spinner_item);

        else if(item.getQtyType()==1)
        {
            adapter=ArrayAdapter.createFromResource(context,R.array.Qty_for_maincourse_and_starters,android.R.layout.simple_spinner_item);
        }

        else
        {
            adapter=ArrayAdapter.createFromResource(context,R.array.Qty_for_bread,android.R.layout.simple_spinner_item);
        }

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
       return items.size();
    }

    public void setDialogImage(ImageView imgView) {

        WindowManager wm= (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        Log.e("WIndow", String.valueOf(wm.getDefaultDisplay().getHeight()));
        imgView.getLayoutParams().height=wm.getDefaultDisplay().getHeight()*(1)/3;
        imgView.getLayoutParams().width= WindowManager.LayoutParams.MATCH_PARENT;
        imgView.requestLayout();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView fImg;
        TextView fName;
        CardView fView;

        public ViewHolder(View itemView) {
            super(itemView);
            fImg=itemView.findViewById(R.id.home_food_img);
            fName=itemView.findViewById(R.id.home_food_name);
            fView=itemView.findViewById(R.id.home_food_view);

        }
    }
}