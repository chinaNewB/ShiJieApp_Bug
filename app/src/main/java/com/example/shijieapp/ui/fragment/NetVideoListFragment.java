package com.example.shijieapp.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shijieapp.R;
import com.example.shijieapp.base.BaseFragment;
import com.example.shijieapp.bean.NetVideoCategoryItem;
import com.example.shijieapp.ui.adapter.NetVideoListPagerAdapter;
import com.example.shijieapp.utils.Constans;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



/**
 * Date: 2020/5/3 17:50
 * author:renzhipeng
 *
 * @author 1278324384@qq.com
 */
public class NetVideoListFragment extends BaseFragment {

    private TabLayout mNetIndication;
    private ViewPager mNetPager;
    private  NetVideoListPagerAdapter mNetVideoListPagerAdapter;

    public static final int WHAT_LOADER_RESULT = 1;
    private static Handler mHandler;

    @Override
    protected void initView(View rootView) {
        setUpState(State.LOADING);
        mNetIndication = rootView.findViewById(R.id.net_indication);
        mNetPager = rootView.findViewById(R.id.net_pager);
        initData();

    }


    @SuppressLint("HandlerLeak")
    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadDatas();
            }
        }).start();


        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case WHAT_LOADER_RESULT:
                        NetVideoCategoryItem result = (NetVideoCategoryItem) msg.obj;
                        setUpState(State.SUCCESS);
                        initNetCategoryData(result);
                        break;
                }
            }
        };
    }

    private void initNetCategoryData(NetVideoCategoryItem result) {
        if (mNetVideoListPagerAdapter != null) {
            mNetVideoListPagerAdapter.setCategoryData(result);
            mNetPager.setAdapter(mNetVideoListPagerAdapter);
        }
    }

    private void loadDatas() {
        try {
            URL url = new URL(Constans.CONNECT_VIDEO_CATEGORY);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);
            int responseCode = httpURLConnection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                String line = bufferedReader.readLine();
                Log.d("NetVideoList","line -- > " + line);
                bufferedReader.close();
                Message message = mHandler.obtainMessage();
                message.what = WHAT_LOADER_RESULT;
                Gson gson = new Gson();
                message.obj = gson.fromJson(line, NetVideoCategoryItem.class);
                mHandler.sendMessage(message);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void initListener() {
        mNetIndication.setupWithViewPager(mNetPager);
        //给ViewPager设置适配器
        mNetVideoListPagerAdapter = new NetVideoListPagerAdapter(getChildFragmentManager());

    }

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_net_video_list;
    }
}
