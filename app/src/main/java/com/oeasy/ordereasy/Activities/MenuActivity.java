package com.oeasy.ordereasy.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oeasy.ordereasy.Adapters.MenuTabsAdapter;
import com.oeasy.ordereasy.Fragments.BreadFragment;
import com.oeasy.ordereasy.Fragments.DessertFragment;
import com.oeasy.ordereasy.Fragments.DrinksFragment;
import com.oeasy.ordereasy.Fragments.MainCourseFragment;
import com.oeasy.ordereasy.Fragments.RecommendedFragment;
import com.oeasy.ordereasy.Fragments.StartersFragment;
import com.oeasy.ordereasy.Interfaces.MenuBtmSearchInterface;
import com.oeasy.ordereasy.R;

/**
 * Created by Stan on 4/6/2018.
 */

public class MenuActivity extends BaseActivity {
    private ViewPager mPager;
    public TabLayout mTab;
    private MenuTabsAdapter adapter;
    public LinearLayout btmToolbar;
    private LinearLayout btmFilterBtn;
    private int flag=1;
    TextView filterCategory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initialize();
        setToolbar();
        setTabs();
        getSeeAllRequest();
        setBottomFilter();

    }

    private void setBottomFilter() {
        btmFilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu pum = new PopupMenu(MenuActivity.this,v);
                pum.inflate(R.menu.filter_options);
                pum.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.pop_up_veg:
                                filterItems(item.getTitle().toString());
                                filterCategory.setText(item.getTitle());
                                return true;
                            case R.id.pop_up_nonveg:
                                filterItems(item.getTitle().toString());
                                filterCategory.setText(item.getTitle());
                                return true;
                            case R.id.pop_up_all:
                                filterItems(item.getTitle().toString());
                                filterCategory.setText(item.getTitle());
                                return true;
                        }
                        return false;
                    }
                });
                pum.show();
            }
        });
    }

    private void filterItems(String title) {
        int current=mPager.getCurrentItem();
        MenuBtmSearchInterface i = (MenuBtmSearchInterface) adapter.getItem(current);
        i.filterItems(title);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void setToolbar() {
        toolbar=getToolbar();
        getSupportActionBar().setTitle("Menu");
        toolbar.setNavigationIcon(R.drawable.ic_action_home);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    private void initialize() {
        mTab=findViewById(R.id.menu_tabs);
        mPager=findViewById(R.id.menu_vp);
        btmToolbar=findViewById(R.id.btm_toolbar);
        btmFilterBtn=findViewById(R.id.btm_toolbar_filter_btn);
        filterCategory=findViewById(R.id.menu_filter_type);
    }
    private void setTabs() {
        adapter=new MenuTabsAdapter(this,getSupportFragmentManager());
        addTabs();
        mPager.setAdapter(adapter);
        mTab.setupWithViewPager(mPager);
    }

    private void addTabs() {
        adapter.addFragment(new RecommendedFragment(), "Recommended");
        adapter.addFragment(new StartersFragment(), "Starters");
        adapter.addFragment(new MainCourseFragment(), "Main Course");
        adapter.addFragment(new BreadFragment(),"Breads");
        adapter.addFragment(new DessertFragment(), "Dessert");
        adapter.addFragment(new DrinksFragment(), "Drinks");

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_cart:
                startActivity(new Intent(this,CartActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            case R.id.menu_item_search:
                flag=1;
                final int current = mPager.getCurrentItem();
                final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

                searchItem(item,current,searchView);

                final ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    }
                    @Override
                    public void onPageSelected(int position) {

                        if (current!= position) {
                            flag = 0;
                            Log.e("LPL", String.valueOf(position));
                            item.collapseActionView();
                        }
                        else{
                            flag=1;
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                };

                mPager.addOnPageChangeListener(listener);
                if(flag==0){
                    Log.e("LPL","LOL");
                    mPager.removeOnPageChangeListener(listener);
                }

             }
            return true;
        }
private void searchItem(MenuItem item, int current, SearchView searchView) {

        MenuBtmSearchInterface i = (MenuBtmSearchInterface) adapter.getItem(current);
        i.setToolbarIcon(item,searchView);
        }

    public void getSeeAllRequest() {
        int sTab = getIntent().getIntExtra("START_TAB", 0);
        mPager.setCurrentItem(sTab);
    }
}
