package com.oeasy.ordereasy.Modals;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by utkarshh12 on 4/11/2018.
 */

public class WaiterModel  {

    private String name;
    private String contact_no;
    private String table_no;
    private int waiter_id;
    public WaiterModel() {
    }

    public WaiterModel(String name) {

        this.name = name;
    }

    public int getWaiter_id() {
        return waiter_id;
    }

    public void setWaiter_id(int waiter_id) {
        this.waiter_id = waiter_id;
    }

    public String getContact_no() {
        return contact_no;
    }



    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }



    public void setTable_no(String table_no) {
        this.table_no = table_no;
    }

    public String getTable_no() {
        return table_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}