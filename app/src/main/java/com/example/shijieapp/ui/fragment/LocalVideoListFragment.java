package com.example.shijieapp.ui.fragment;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.shijieapp.R;
import com.example.shijieapp.base.BaseFragment;
import com.example.shijieapp.dto.localvideo.AbstructProvider;
import com.example.shijieapp.bean.LocalVideoItem;
import com.example.shijieapp.dto.localvideo.LocalVideoProvider;
import com.example.shijieapp.ui.adapter.LocalVideoAdapter;
import com.example.shijieapp.utils.ToastUtil;

import java.util.List;

/**
 * Date: 2020/5/3 17:13
 * author:renzhipeng
 *
 * @author 1278324384@qq.com
 */

public class LocalVideoListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "LocalVideoListFragment";
    private RecyclerView mRlLocalVideoList;
    List<LocalVideoItem> mLocalVideos;
    private AbstructProvider provider;
    private LocalVideoAdapter mLocalVideoAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean isRefreshing = false;


    @Override
    protected void initView(View rootView) {
        setUpState(State.LOADING);
        mRlLocalVideoList = rootView.findViewById(R.id.rl_local_video);
        mSwipeRefreshLayout = rootView.findViewById(R.id.pull_refresh);
        mLocalVideoAdapter = new LocalVideoAdapter();
        provider = new LocalVideoProvider();
        mLocalVideos = provider.getList();

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPurple, R.color.colorToastInfo, R.color.colorToastWarn, R.color.colorToastSuccess);
    }


    @Override
    protected void initListener() {
        if (mLocalVideos.size() <= 0) {
            setUpState(State.EMPTY);
        }
        setUpState(State.SUCCESS);
        mRlLocalVideoList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mLocalVideoAdapter.setData(mLocalVideos);
        mRlLocalVideoList.setNestedScrollingEnabled(false);
        mRlLocalVideoList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = 15;
                outRect.bottom = 15;
                outRect.left = 15;
                outRect.right = 15;
            }
        });
        mRlLocalVideoList.setAdapter(mLocalVideoAdapter);


        mLocalVideoAdapter.setOnLocalItemClickListener(new LocalVideoAdapter.OnLocalItemClickListener() {
            @Override
            public void onLocalItemClick(LocalVideoItem localVideos) {
                ToastUtil.showToast(ToastUtil.TYPE_INFO, "点击了" + localVideos.getTitle());
            }
        });

        mLocalVideoAdapter.setOnLocalItemLongClickListener(new LocalVideoAdapter.OnLocalItemLongClickListener() {
            @Override
            public void onLocalItemLongClick(LocalVideoItem localVideos) {
                Log.i(TAG, "onLocalItemLongClick: -----" + localVideos.toString());
            }
        });


        mSwipeRefreshLayout.setOnRefreshListener(this);

    }

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_local_video_list;
    }


    @Override
    public void onRefresh() {
        if (!isRefreshing) {
            isRefreshing = true;
            ToastUtil.showToast(ToastUtil.TYPE_INFO, "下拉了");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        isRefreshing = false;
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSwipeRefreshLayout.setRefreshing(false);
        isRefreshing = false;
    }
}
