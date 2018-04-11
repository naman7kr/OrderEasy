package com.oeasy.ordereasy.Modals;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by utkarshh12 on 4/11/2018.
 */

public class WaiterModel  {

    private String name;

    public WaiterModel() {
    }

    public WaiterModel(String name) {

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
