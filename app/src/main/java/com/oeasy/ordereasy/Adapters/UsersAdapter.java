package com.oeasy.ordereasy.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oeasy.ordereasy.Modals.UserInformation;
import com.oeasy.ordereasy.R;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserHolder> {
    Context context;
    ArrayList<UserInformation> list;
    public UsersAdapter(Context context, ArrayList<UserInformation> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View iView= LayoutInflater.from(context).inflate(R.layout.user,parent,false);
        return new UserHolder(iView);
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {
        holder.uEmail.setText(list.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class UserHolder extends RecyclerView.ViewHolder{
        TextView uEmail;
        public UserHolder(View itemView) {
            super(itemView);
            uEmail=itemView.findViewById(R.id.u_email);
        }
    }
}
