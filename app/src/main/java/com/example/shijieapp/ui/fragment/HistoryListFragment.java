package com.example.shijieapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shijieapp.R;
import com.example.shijieapp.base.BaseFragment;

/**
 * Date: 2020/5/2 22:49
 * author:renzhipeng
 */
public class HistoryListFragment extends BaseFragment {


    @Override
    protected int getRootViewResId() {
        return R.layout.self_dialog;
    }

    @Override
    protected void initView(View rootView) {
        setUpState(State.SUCCESS);
    }
}
