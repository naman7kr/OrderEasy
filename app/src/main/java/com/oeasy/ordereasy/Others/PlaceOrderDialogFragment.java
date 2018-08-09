package com.oeasy.ordereasy.Others;



import android.annotation.SuppressLint;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.oeasy.ordereasy.Adapters.DialogAdapter;
import com.oeasy.ordereasy.Fragments.PreviewFragment;
import com.oeasy.ordereasy.Modals.FoodItem;
import com.oeasy.ordereasy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class PlaceOrderDialogFragment extends DialogFragment implements View.OnClickListener {
    private DialogAdapter dadapter;
    private ArrayList<FoodItem> mList;
    private TextView grand_total;
    private Button confirm,cancel;
    RecyclerView rView;
    DatabaseHelper db;
    PreviewFragment pf;
    Context context;
    Communicator communicator;
    public PlaceOrderDialogFragment(){}
    @SuppressLint("ValidFragment")
    public PlaceOrderDialogFragment(Context context,PreviewFragment pf, ArrayList<FoodItem> list){
        mList=list;
        this.context=context;
        this.pf=pf;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            communicator= (Communicator) pf;
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling Fragment must implement OnAddFriendListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View mDialog= inflater.inflate(R.layout.dialog_place_order,container,false);

        initialize(mDialog);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            rView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        rView.setAdapter(dadapter);
        setTotal();
        setCancelable(false);
        return mDialog;
    }

    private void initialize(View mDialog) {
        rView=mDialog.findViewById(R.id.dialog_rview);
        dadapter=new DialogAdapter(getContext(),mList);
        grand_total=mDialog.findViewById(R.id.dialog_grand_total);
        confirm=mDialog.findViewById(R.id.dialog_place_order_confirm_btn);
        cancel=mDialog.findViewById(R.id.dialog_place_order_cancel_btn);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
        db = new DatabaseHelper(context);
    }
    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.dialog_place_order_cancel_btn){
            communicator.communicatorMessage("cancel");
            dismiss();
        }
        if(view.getId()==R.id.dialog_place_order_confirm_btn){
            sendDataToDatabase(mList);
            db.deleteAllFoodItems();
            mList.clear();
            communicator.communicatorMessage("confirm");
            dismiss();
        }
    }
    private void setTotal() {
        float cost=0;
        for(int i=0;i<mList.size();i++){
            String pQty=mList.get(i).getQty();
            float price=mList.get(i).getPrice();
            cost+=price*(Float.parseFloat(pQty));
        }
        grand_total.setText(String.valueOf(cost));
    }

    private void sendDataToDatabase(ArrayList<FoodItem> fList) {
        SharedPreferences sp=context.getSharedPreferences("table",MODE_PRIVATE);
        String tn=sp.getString("table_no","");
        final JSONArray jsonArray=new JSONArray();
        if(tn.compareTo("")!=0){
            String tableNo=tn;
            for(int i=0;i<fList.size();i++){
                JSONObject jObject=fList.get(i).getJSONObject();
                try {
                    jObject.put("qty",fList.get(i).getQty());
                    jObject.put("table_no",tableNo);
                    jsonArray.put(jObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Log.e("JSON",jsonArray.toString());
            Toast.makeText(getContext(),"Your Order has been placed",Toast.LENGTH_LONG).show();
            StringRequest request=new StringRequest(Request.Method.POST, Constants.URL_PROCESS_REQUEST, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params= new HashMap<>();
                    params.put("store_order",jsonArray.toString() );
                    return params;
                }
            };

            RequestHandler.getInstance(context).addToRequestQueue(request);
        }else{
            Toast.makeText(getContext(),"No waiter added First scan QR",Toast.LENGTH_SHORT).show();
        }
    }
    public interface  Communicator{
        void communicatorMessage(String message);
    }
}
