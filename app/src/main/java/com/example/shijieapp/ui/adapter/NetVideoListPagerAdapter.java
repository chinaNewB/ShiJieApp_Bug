package com.example.shijieapp.ui.adapter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.shijieapp.bean.NetVideoCategoryItem;
import com.example.shijieapp.ui.fragment.NetVideoListWithDataFragment;

import java.util.ArrayList;
import java.util.List;



/**
 * Date: 2020/5/4 22:33
 * author:renzhipeng
 */
public class NetVideoListPagerAdapter extends FragmentPagerAdapter {

    List<NetVideoCategoryItem.ResultBean> categoryList = new ArrayList<>();
    public NetVideoListPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return categoryList.get(position).getName();
    }

    @Override
    public Fragment getItem(int i) {
        NetVideoCategoryItem.ResultBean resultBean = categoryList.get(i);
        NetVideoListWithDataFragment netVideoListWithDataFragment = NetVideoListWithDataFragment.newInstance(resultBean);
        return netVideoListWithDataFragment;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    public void setCategoryData(NetVideoCategoryItem result) {
        categoryList.clear();
        List<NetVideoCategoryItem.ResultBean> data = result.getResult();
        this.categoryList.addAll(data);
        Log.d("NetVideoCategory","size -------- "+ this.categoryList.size());
        notifyDataSetChanged();
    }
}
