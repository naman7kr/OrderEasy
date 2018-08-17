package com.oeasy.ordereasy.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.oeasy.ordereasy.Activities.CartActivity;
import com.oeasy.ordereasy.Activities.MainActivity;
import com.oeasy.ordereasy.Activities.ScannerActivity;
import com.oeasy.ordereasy.Adapters.VerticalRecyclerViewAdapter;
import com.oeasy.ordereasy.Modals.FoodItem;

import com.oeasy.ordereasy.Others.Constants;
import com.oeasy.ordereasy.Others.DatabaseHelper;
import com.oeasy.ordereasy.Others.PlaceOrderDialogFragment;

import com.oeasy.ordereasy.Others.RequestHandler;
import com.oeasy.ordereasy.Others.SimpleItemTouchHelper;
import com.oeasy.ordereasy.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Stan on 4/14/2018.
 */

public class PreviewFragment extends Fragment implements PlaceOrderDialogFragment.Communicator, View.OnClickListener {
    private RecyclerView mHorizontalRecyclerView;
    private LinearLayoutManager horizontalLayoutManager;
    private RecyclerView mVerticalRecyclerView;
    private LinearLayoutManager verticalLayoutManager;
    private VerticalRecyclerViewAdapter verticalAdapter;
    private FloatingActionMenu fam;
    private FloatingActionButton fqr,fotp;
    private ArrayList<FoodItem> itemsList;
    private DatabaseHelper db;
    private Dialog mDialog;
    private Button poBtn,clrListBtn;
    private TextView otp;
    String tn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preview, container, false);


        initialize(view);
        checkScan();
        setCartLayout();
        onButtonClick();
        setFabs();

        return view;
    }

    private void setFabs() {
        SharedPreferences sp=getContext().getSharedPreferences("table",MODE_PRIVATE);
        if(sp.getString("table_no","").compareTo("")!=0){
            fam.setVisibility(View.GONE);
        }else{
            fam.setVisibility(View.VISIBLE);
        }
        fqr.setOnClickListener(this);
        fotp.setOnClickListener(this);
    }

    private void checkScan() {
        SharedPreferences sp=getContext().getSharedPreferences("table",MODE_PRIVATE);
        String tn=sp.getString("table_no","");
        if(tn.compareTo("")!=1){
            poBtn.setEnabled(false);
            poBtn.setText("Scan QR First");
        }else{
            poBtn.setText("Place Order");
            poBtn.setEnabled(true);
        }
    }

    private void initialize(View view) {
        mVerticalRecyclerView=view.findViewById(R.id.verticalRecyclerView);
        poBtn=view.findViewById(R.id.place_order_btn);
        itemsList=new ArrayList<>();
        clrListBtn=view.findViewById(R.id.clear_cart_btn);
        db = new DatabaseHelper(getContext());
        fam=view.findViewById(R.id.cart_fam);
        fqr=view.findViewById(R.id.cart_scan_qr);
        fotp=view.findViewById(R.id.cart_enter_otp);
    }
    private void onButtonClick() {
        poBtn.setOnClickListener(this);
        clrListBtn.setOnClickListener(this);

    }

    private void createPlaceOrderDialog() {
        PlaceOrderDialogFragment dialog=new PlaceOrderDialogFragment(getContext(),this,itemsList);
        dialog.show(getFragmentManager(),"MyFragment");


    }

    private void setCartLayout() {
        verticalAdapter = new VerticalRecyclerViewAdapter(getContext(),getCartItems());
        verticalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mVerticalRecyclerView.setLayoutManager(verticalLayoutManager);
        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelper(verticalAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mVerticalRecyclerView);
        mVerticalRecyclerView.setAdapter(verticalAdapter);
    }
    public ArrayList<FoodItem> getCartItems() {
        ArrayList<FoodItem> cList=db.getAllFoodItems();
        for(int i=0;i<cList.size();i++){
            cList.get(i).setTag(0);
            itemsList.add(cList.get(i));
            Log.e("preview cart",cList.get(i).getName());
        }
        ArrayList<FoodItem> bList=db.getBillItems();
        for(int i=0;i<bList.size();i++){
            bList.get(i).setTag(1);
            itemsList.add(bList.get(i));
            Log.e("preview bill",bList.get(i).getName());
        }
        return itemsList;
    }


    @Override
    public void communicatorMessage(String message) {
        if(message.compareTo("confirm")==0){
            itemsList.clear();
            verticalAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.cart_enter_otp){
            mDialog=new Dialog(getContext());
            mDialog.setContentView(R.layout.dialog_otp);
            otp=mDialog.findViewById(R.id.dialog_otp);
            Button cncl=mDialog.findViewById(R.id.dialog_otp_cancel);
            Button ok=mDialog.findViewById(R.id.dialog_otp_ok);
            mDialog.show();
            cncl.setOnClickListener(this);
            ok.setOnClickListener(this);
            fam.close(true);
        }else if(view.getId()==R.id.cart_scan_qr){
            startActivity(new Intent(getContext(),ScannerActivity.class));
            getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            fam.close(true);
        }else if(view.getId()==R.id.dialog_otp_cancel){
            mDialog.dismiss();
        }else if(view.getId()==R.id.dialog_otp_ok){
            verifyOTP(otp.getText().toString());
            mDialog.dismiss();
        }else if(view.getId()==R.id.place_order_btn){
            createPlaceOrderDialog();
        }else if(view.getId()==R.id.clear_cart_btn){
            itemsList.clear();
            db.deleteAllFoodItems();
            verticalAdapter.notifyDataSetChanged();
        }
    }

    private void verifyOTP(String otp) {
        final JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("email", GoogleSignIn.getLastSignedInAccount(getContext()).getEmail().toString());
            jsonObject.put("otp",otp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringRequest request=new StringRequest(Request.Method.POST, Constants.URL_PROCESS_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response!="0"){
                    Toast.makeText(getContext(),"You are connected to table no : "+response,Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                }else{
                    Toast.makeText(getContext(),"OTP Entered is wrong",Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<>();
                params.put("verify_otp", String.valueOf(jsonObject));
                return params;
            }
        };

        RequestHandler.getInstance(getContext(),this).addToRequestQueue(request);
    }
}

