package com.google.android.apps.uvg.views;


import com.google.android.maps.mytracks.R;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;

public class UVIViewActivity extends Activity {
  public static final String KEY_TAB_INDEX = "tab index";
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uvg_uvi_view );
        
        // set up action bar
        ActionBar bar = getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        // create fragments
        Fragment startFragment = new UVICurrentFragment();
        Fragment historyFragment = new UVIHourlyFragment();
        
        // create tabs
        ActionBar.Tab startTab = bar.newTab().setText(getString(R.string.uvi_current_tab));
        startTab.setTabListener(new UVITabListener(startFragment, getApplicationContext()));
        
        ActionBar.Tab historyTab = bar.newTab().setText(getString(R.string.uvi_hourly_tab));
        historyTab.setTabListener(new UVITabListener(historyFragment, getApplicationContext()));
        
        // add tabs
        bar.addTab(startTab);
        bar.addTab(historyTab);
        
        // resume state if applicable
        if (savedInstanceState != null){
            bar.setSelectedNavigationItem(savedInstanceState.getInt(KEY_TAB_INDEX));
        }
    }
}


class UVITabListener implements ActionBar.TabListener{
    public Fragment mFragment;
    public Context mContext;
    
    public UVITabListener(Fragment frgmt, Context cntxt){
        mFragment = frgmt; mContext = cntxt;
    }
    
    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft){
        ft.replace(R.id.fragment_container, mFragment);
//      Toast.makeText(mContext, "onTabSelected", Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft){
//      Toast.makeText(mContext, "onTabReSelected", Toast.LENGTH_SHORT).show();     
    }
    
    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft){
        ft.remove(mFragment);
//      Toast.makeText(mContext, "onTabUnSelected", Toast.LENGTH_SHORT).show();     
    }
}
