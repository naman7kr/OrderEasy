package com.oeasy.ordereasy.Interfaces;

import android.support.v7.widget.SearchView;
import android.view.MenuItem;

/**
 * Created by Stan on 4/11/2018.
 */

public interface MenuBtmSearchInterface {
    void setSearchToolbar();
    void setToolbarIcon(MenuItem item, SearchView searchView);
    void filterItems(String title);
}
