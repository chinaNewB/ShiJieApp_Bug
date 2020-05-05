package com.example.shijieapp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shijieapp.R;
import com.example.shijieapp.base.BaseApplication;
import com.example.shijieapp.bean.MenuItem;
import com.example.shijieapp.dto.user.User;
import com.example.shijieapp.dto.user.UserUtils;
import com.example.shijieapp.ui.activity.ActivityHomePage;
import com.example.shijieapp.ui.activity.ActivityLogin;
import com.example.shijieapp.ui.myview.DialogNoInput;


import java.util.ArrayList;
import java.util.List;


public class MenuFragment extends Fragment {

    private ListView mListView;
    private List<MenuItem> menuItemList = new ArrayList<>();
    public MenuItemAdapter mMenuItemAdapter;


    private TextView username;
    private ActivityHomePage activity;
    private UserUtils userUtils;
    private String userNames;
    private long userPhones;
    private User user;
    private View navView;

    private String[] mItemTitles;
    private MenuItem menuItem;
    private int[] mItemIcons;

    private DialogNoInput mDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        navView = inflater.inflate(R.layout.fragment_menu, container, false);


        initView();

        //        mListView.setDivider(null); // 去掉分割线

        initListView();
        clickEvents();
        return navView;
    }

    private void initView() {
        userUtils = new UserUtils(getActivity());
        activity = (ActivityHomePage) getActivity();

        mListView = navView.findViewById(R.id.menuList);
        username = navView.findViewById(R.id.user_name);
        userPhones = Long.parseLong(BaseApplication.getUserPhones());
        user = userUtils.queryUserByPhone(userPhones);
        userNames = user.getUsername();
        username.setText(userNames);


    }

    public void initListView() {

        mItemTitles = getResources().getStringArray(R.array.menu_item_title);
        mItemIcons = new int[]{R.mipmap.homepage, R.mipmap.history, R.mipmap.coin, R.mipmap.setting, R.mipmap.exit};

        for (int i = 0; i < mItemTitles.length; i++) {
            menuItem = new MenuItem(mItemTitles[i], mItemIcons[i]);
            menuItemList.add(menuItem);
        }
        mMenuItemAdapter = new MenuItemAdapter(getActivity(), R.layout.item_menu, menuItemList);
        mListView.setAdapter(mMenuItemAdapter);
    }

    public void clickEvents() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mMenuItemAdapter.changeSelected(position); // 先定义好样式如何改变
                activity = (ActivityHomePage) getActivity();
                DrawerLayout mDrawerLayout = activity.findViewById(R.id.drawerLayout);
                mDrawerLayout.closeDrawer(Gravity.START);
                switch (position) {
                    case 0:
                        activity.switchFragment(ActivityHomePage.mLocalNetVideoListFragment);
                        activity.setNav(true,true,R.mipmap.search);
                        break;
                    case 1:
                        activity.switchFragment(ActivityHomePage.mHistoryListFragment);
                        activity.setNav(true,false,R.mipmap.delete);

                        break;
                    case 2:
                        activity.switchFragment(ActivityHomePage.mCoinFragment);
                        activity.setNav(false,false,0);
                        break;
                    case 3:
                        activity.switchFragment(ActivityHomePage.mSettingFragment);
                        activity.setNav(false,false,0);
                        break;
                    case 4:
                        mDrawerLayout.openDrawer(Gravity.START);
                        if (mDialog == null) {
                            mDialog = new DialogNoInput(getContext(), R.style.CustomDialog);
                        }
                        mDialog.setTitle("退出登录")
                                .setMessage("是否退出登录")
                                .setConfirm("确定", new DialogNoInput.IOnConfirmListener() {
                                    @Override
                                    public void onConfirm(DialogNoInput dialog) {
                                        Intent intent = new Intent(getContext(), ActivityLogin.class);
                                        intent.putExtra("return2login", true);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                })
                                .setCancel("取消", new DialogNoInput.IOnCancelListener() {
                                    @Override
                                    public void onCancel(DialogNoInput dialog) {
                                        dialog.dismiss();
                                    }
                                }).show();
                        break;
                    default:
                        break;
                }
            }
        });

    }


}