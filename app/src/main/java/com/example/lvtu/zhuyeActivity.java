package com.example.lvtu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import util.SpfUtil;

public class zhuyeActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int MODE_LINEAR = 0;
    public static final int MODE_GRID = 1;

    public static final String KEY_LAYOUT_MODE = "key_layout_mode";
    private int currentListLayoutMode = MODE_LINEAR;
      Toolbar toolbar;
    ViewPager2 viewPager;

    private LinearLayout llshouye, llshequ, llchuxing, llwode;
    private ImageView ivShouye, ivshequ, ivchuxing, ivwode,ivCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuye);
        LayoutInflater If = getLayoutInflater().from(this);
        initPager();
        initTabView();
        toolbar = findViewById(R.id.tb);
        setSupportActionBar(toolbar);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu,menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.menu_search:
//
//                return true;
//            case R.id.menu_linear:
////                setToLinearList();
////                currentListLayoutMode = MODE_LINEAR;
////                SpfUtil.saveInt(getActivity().getApplicationContext(), KEY_LAYOUT_MODE, MODE_LINEAR);
//                return true;
//            case R.id.menu_grid:
////                setToGridList();
////                currentListLayoutMode = MODE_GRID;
////                SpfUtil.saveInt(getActivity().getApplicationContext(), KEY_LAYOUT_MODE, MODE_GRID);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//
//    }

    private void initTabView() {
        llshouye = findViewById(R.id.tab_shouye);
        llshouye.setOnClickListener(this);
        llshequ = findViewById(R.id.tab_shequ);
        llshequ.setOnClickListener(this);

        llchuxing = findViewById(R.id.tab_chuxing);
        llchuxing.setOnClickListener(this);
        llwode = findViewById(R.id.tab_wode);
        llwode.setOnClickListener(this);

        ivShouye = findViewById(R.id.tab_Iv_shouye);
        ivshequ = findViewById(R.id.tab_Iv_shequ);
        ivchuxing = findViewById(R.id.tab_Iv_chuxing);
        ivwode = findViewById(R.id.tab_Iv_wode);

        ivShouye.setSelected(true);
        ivCurrent = ivShouye;

    }

    private void initPager() {
        viewPager = findViewById(R.id.view_pager);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new shouyeFragment());

        fragments.add(new shequFragment());
        fragments.add(new chuxingFragment());
        fragments.add(new wodeFragment());

        MyfragmentAdapter pagerAdapter = new MyfragmentAdapter(getSupportFragmentManager(),getLifecycle(),fragments);
        viewPager.setAdapter( pagerAdapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                changeTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }


    private void changeTab(int position) {
        ivCurrent.setSelected(false);
        switch (position){
            case R.id.tab_shouye:
                viewPager.setCurrentItem(0);
            case 0:
                ivShouye.setSelected(true);
                ivCurrent = ivShouye;
                break;
            case R.id.tab_shequ:
                viewPager.setCurrentItem(1);
            case 1:ivshequ.setSelected(true);
                ivCurrent = ivshequ;
                break;
            case R.id.tab_chuxing:
                viewPager.setCurrentItem(2);
            case 2:ivchuxing.setSelected(true);
                ivCurrent = ivchuxing;
                break;
            case R.id.tab_wode:
                viewPager.setCurrentItem(3);
            case 3:ivwode.setSelected(true);
                ivCurrent = ivwode;
                break;
        }
    }

    @Override
    public void onClick(View v) {
        changeTab(v.getId());

    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

}