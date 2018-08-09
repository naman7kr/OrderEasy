package com.oeasy.ordereasy.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.oeasy.ordereasy.Adapters.CartTabsAdapter;
import com.oeasy.ordereasy.Fragments.BillFragment;
import com.oeasy.ordereasy.Fragments.PreviewFragment;
import com.oeasy.ordereasy.R;

/**
 * Created by Stan on 4/6/2018.
 */

public class CartActivity extends BaseActivity {


    private CartTabsAdapter adapter;


    public ViewPager mPager;
    public TabLayout mTab;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initialize();
        setToolbar();
        setTabs();
    }
    private void initialize() {
        mTab=findViewById(R.id.cart_tabs);
        mPager=findViewById(R.id.cart_vp);
        adapter=new CartTabsAdapter(this,getSupportFragmentManager());
    }
    private void setTabs() {
        addTabs();
        mPager.setAdapter(adapter);
        mTab.setupWithViewPager(mPager);
    }

    private void addTabs() {
        adapter.addFragment(new PreviewFragment(), "Preview");
        adapter.addFragment(new BillFragment(), "Bill");
    }

    private void setToolbar() {
        toolbar=getToolbar();
        getSupportActionBar().setTitle("Cart");
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.cart_menu:
                startActivity(new Intent(this,MenuActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            case R.id.cart_table_info:
                startActivity(new Intent(this,TableInformationActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}