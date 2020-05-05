package com.example.shijieapp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Outline;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.shijieapp.R;
import com.example.shijieapp.base.BaseActivity;
import com.example.shijieapp.base.BaseFragment;
import com.example.shijieapp.ui.fragment.CoinFragment;
import com.example.shijieapp.ui.fragment.HistoryListFragment;
import com.example.shijieapp.ui.fragment.LocalNetVideoListFragment;
import com.example.shijieapp.ui.fragment.SettingFragment;
import com.example.shijieapp.ui.myview.DialogNoInput;
import com.example.shijieapp.utils.HomepageUtil;
import com.example.shijieapp.utils.WindowStyleUtil;

@SuppressLint("StaticFieldLeak")
public class ActivityHomePage extends BaseActivity {

    private DrawerLayout drawerlayout;
    private Toolbar toolbar;
    private ImageView mSearchOrDelete;
    private View mUserImg;
    private SharedPreferences preferences;
    private String userphone;
    private FragmentManager mFm;
    public static LocalNetVideoListFragment mLocalNetVideoListFragment;
    public static CoinFragment mCoinFragment;
    public static HistoryListFragment mHistoryListFragment;
    public static SettingFragment mSettingFragment;

    public String getUserPhone() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        userphone = preferences.getString("usernames", "");
        return userphone;
    }

    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //        getWindow().setStatusBarColor(0xff2299ff);

        initView();
        initSetting();
        initListener();
    }

    public void setNav(boolean isShow, boolean isHomePage, int searchOrdelete) {
        if (isShow) {
            mSearchOrDelete.setBackgroundResource(searchOrdelete);
        } else {
            mSearchOrDelete.setBackground(null);
        }
        if (isHomePage) {
            toolbar.setNavigationIcon(R.mipmap.toolbar);
        } else {
            toolbar.setNavigationIcon(R.mipmap.returns);
        }
    }

    private void initListener() {


        //        replaceFragment(new LocalNetVideoListFragment());
        //获取outline ============给头像设置阴影
        final ViewOutlineProvider vop = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                //修改outline
                outline.setOval(0, 0, view.getWidth(), view.getHeight());
            }
        };
        //重新设置
        mUserImg.setOutlineProvider(vop);


        //        drawerlayout.setScrimColor(Color.TRANSPARENT); // 菜单滑动时content不被阴影覆盖
        final ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this, drawerlayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        //同步drawerToggle按钮与侧滑菜单的打开（关闭）状态
        drawerToggle.syncState();
        HomepageUtil.drawlayoutOpClo(drawerlayout, this);
    }

    private void initSetting() {
        WindowStyleUtil.getInstance().windowSettingColor(this, false);
        switchFragment(mLocalNetVideoListFragment);

    }

    public void initView() {
        drawerlayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        mUserImg = findViewById(R.id.user_img);
        mFm = getSupportFragmentManager();
        mSearchOrDelete = findViewById(R.id.search);
        toolbar.setNavigationIcon(R.mipmap.toolbar);
        mLocalNetVideoListFragment = new LocalNetVideoListFragment();
        mCoinFragment = new CoinFragment();
        mHistoryListFragment = new HistoryListFragment();
        mSettingFragment = new SettingFragment();
    }



    /*public void replaceFragment(Fragment fragment) { // 动态加载fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_view, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }*/


    //上一次显示的fragment

    public static BaseFragment lastOneFragment = null;

    public void switchFragment(BaseFragment targetFragment) {

        //如果上一个fragment跟当前要切换的fragment是同一个，那么不需要切换
        if (lastOneFragment == targetFragment) {
            return;
        }
        //修改成add和hide的方式來控制Fragment的切换
        FragmentTransaction transaction = mFm.beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction.add(R.id.content_view, targetFragment);
        } else {
            transaction.show(targetFragment);
        }

        if (lastOneFragment != null) {
            transaction.hide(lastOneFragment);
        }

        lastOneFragment = targetFragment;

        //        transaction.replace(R.id.main_page_container, targetFragment);

        transaction.commitAllowingStateLoss();
    }


    @Override
    protected void onStop() {
        super.onStop();

    }
}
