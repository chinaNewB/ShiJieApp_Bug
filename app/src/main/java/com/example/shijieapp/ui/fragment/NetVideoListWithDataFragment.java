package com.example.shijieapp.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.shijieapp.R;
import com.example.shijieapp.base.BaseFragment;
import com.example.shijieapp.bean.NetVideoCategoryItem;
import com.example.shijieapp.bean.NetVideoItem;
import com.example.shijieapp.utils.Constans;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Date: 2020/5/4 22:47
 * author:renzhipeng
 */
public class NetVideoListWithDataFragment extends BaseFragment {

    private RecyclerView mRlNetVideoView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView mTvTitleId;


    public static NetVideoListWithDataFragment newInstance(NetVideoCategoryItem.ResultBean netVideoItem) {
        NetVideoListWithDataFragment netVideoListWithDataFragment = new NetVideoListWithDataFragment();
        //
        Bundle bundle = new Bundle();
        bundle.putString(Constans.KEY_HOME_PAGER_TITLE, netVideoItem.getName());
        bundle.putInt(Constans.KEY_HOME_PAGER_MATETIAL_ID, netVideoItem.getId());
        netVideoListWithDataFragment.setArguments(bundle);

        return netVideoListWithDataFragment;
    }


    @Override
    protected void initView(View rootView) {
        setUpState(State.LOADING);
        mRlNetVideoView = rootView.findViewById(R.id.rl_net_video);
        mSwipeRefreshLayout = rootView.findViewById(R.id.pull_refresh);
        mTvTitleId = rootView.findViewById(R.id.tv_title_id);

        initData();
    }

    private void initData() {
        Bundle arguments = getArguments();
        String title = arguments.getString(Constans.KEY_HOME_PAGER_TITLE);
        int id = arguments.getInt(Constans.KEY_HOME_PAGER_MATETIAL_ID);
        mTvTitleId.setText(title + "=====" + id);
    }


    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_local_video_list_with_data;
    }
}
