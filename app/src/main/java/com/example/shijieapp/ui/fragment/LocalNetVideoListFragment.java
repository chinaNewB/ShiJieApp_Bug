package com.example.shijieapp.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.shijieapp.R;
import com.example.shijieapp.base.BaseApplication;
import com.example.shijieapp.base.BaseFragment;
import com.example.shijieapp.ui.activity.ActivityHomePage;
import com.example.shijieapp.ui.activity.ActivityLogin;
import com.example.shijieapp.ui.myview.DialogWithInput;
import com.example.shijieapp.utils.NetUtils;
import com.example.shijieapp.utils.OnOneClickListener;
import com.example.shijieapp.utils.Rotate3dAnimation;
import com.example.shijieapp.utils.ToastUtil;
import com.scalified.fab.ActionButton;

/**
 * Date: 2020/5/2 22:49
 * author:renzhipeng
 *
 * @author 1278324384@qq.com
 */
@SuppressLint("StaticFieldLeak")
public class LocalNetVideoListFragment extends BaseFragment {

    private ActionButton inputNetUrlEtv;

    private FragmentManager mFm;

    private ActivityHomePage mActivityHomePage;
    private Button mBtnLocal;
    private Button mBtnNet;

    private static LocalVideoListFragment mLocalVideoListFragment;
    private static NetVideoListFragment mNetVideoListFragment;
    private View mContentView;

    private  Rotate3dAnimation mOpenFlipAnimation;
    private  Rotate3dAnimation mCloseFlipAnimation;
    private float centerX, centerY, depthZ;
    private static int duration = 400;

    @Override
    protected void initView(View rootView) {
        setUpState(State.SUCCESS);
        inputNetUrlEtv = rootView.findViewById(R.id.flow_net_video_url);
        mFm = getChildFragmentManager();
        mActivityHomePage = (ActivityHomePage) getActivity();
        mActivityHomePage.setNav(true, true, R.mipmap.search);

        mBtnLocal = rootView.findViewById(R.id.btn_local);
        mBtnNet = rootView.findViewById(R.id.btn_net);

        mContentView = rootView.findViewById(R.id.video_content);

        mLocalVideoListFragment = new LocalVideoListFragment();
        mNetVideoListFragment = new NetVideoListFragment();


        switchFragment(mLocalVideoListFragment);
    }


    private BaseFragment lastOneFragment;

    public void switchFragment(BaseFragment targetFragment) {

        //如果上一个fragment跟当前要切换的fragment是同一个，那么不需要切换
        if (lastOneFragment == targetFragment) {
            return;
        }
        //修改成add和hide的方式來控制Fragment的切换
        FragmentTransaction transaction = mFm.beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction.add(R.id.video_content, targetFragment);
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
    protected void initListener() {
        inputNetUrlEtv.setOnClickListener(new OnOneClickListener() {
            @Override
            public void oneClick(View v) {
                DialogWithInput dialog = new DialogWithInput(getActivity(), R.style.CustomDialog);
                dialog.setConfirm("确定", new DialogWithInput.IOnConfirmListener() {
                    @Override
                    public void onConfirm(DialogWithInput dialog, String netUrl) {
                        if (netUrl.isEmpty()) {
                            ToastUtil.showToast(ToastUtil.TYPE_WARN, "输入地址不能为空");
                        } else {
                            Intent intent = new Intent(getActivity(), ActivityLogin.class);
                            /*intent.putExtra("video", path);
                            intent.putExtra("net", 2);*/
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    }
                }).setCancel("取消", new DialogWithInput.IOnCancelListener() {
                    @Override
                    public void onCancel(DialogWithInput dialog) {
                        dialog.dismiss();
                    }
                }).show();

            }
        });

        mBtnLocal.setOnClickListener(new OnOneClickListener() {
            @Override
            public void oneClick(View v) {

                if (lastOneFragment != mLocalVideoListFragment) {
                    animates();
                    mContentView.startAnimation(mCloseFlipAnimation);

                    setBgc(mBtnLocal,
                            mBtnNet,
                            R.drawable.btn_local_purple,
                            R.drawable.btn_net_white,
                            R.color.colorWhite,
                            R.color.colorPurple);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            switchFragment(mLocalVideoListFragment);
                        }
                    }, 400);
                }
            }
        });

        mBtnNet.setOnClickListener(new OnOneClickListener() {
            @Override
            public void oneClick(View v) {

                if (lastOneFragment != mNetVideoListFragment) {
                    animates();
                    mContentView.startAnimation(mCloseFlipAnimation);
                    setBgc(mBtnLocal,
                            mBtnNet,
                            R.drawable.btn_local_white,
                            R.drawable.btn_net_purple,
                            R.color.colorPurple,
                            R.color.colorWhite);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            switchFragment(mNetVideoListFragment);
                        }
                    }, 400);

                }

            }
        });
    }


    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_local_net_video_list;
    }


    //按钮点击变化样式
    public void setBgc(Button btnLocal, Button btnNet, int localBg, int netBg, int localColor, int netColor) {
        btnLocal.setBackgroundResource(localBg);
        btnLocal.setTextColor(getResources().getColor(localColor));
        btnNet.setBackgroundResource(netBg);
        btnNet.setTextColor(getResources().getColor(netColor));
    }

    //卡片翻转部分
    private void initOpenAnim() {
        if (mOpenFlipAnimation == null) {
            mOpenFlipAnimation = new Rotate3dAnimation(0, 90, centerX, centerY, depthZ, true);
        }
        mOpenFlipAnimation.setDuration(duration);
        mOpenFlipAnimation.setFillAfter(true);
        mOpenFlipAnimation.setInterpolator(new AccelerateInterpolator());
        mOpenFlipAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                //从270到360度，顺时针旋转视图，此时reverse参数为false，达到360度动画结束时视图变得可见
                Rotate3dAnimation mRotateAnimation1 = new Rotate3dAnimation(270, 360, centerX, centerY, depthZ, false);
                mRotateAnimation1.setDuration(duration);
                mRotateAnimation1.setFillAfter(true);
                mRotateAnimation1.setInterpolator(new DecelerateInterpolator());
                mContentView.startAnimation(mRotateAnimation1);
            }
        });
    }

    /**
     * 卡牌文本介绍关闭效果：旋转角度与打开时逆行即可
     */
    private void initCloseAnim() {
        if (mCloseFlipAnimation == null) {
            mCloseFlipAnimation = new Rotate3dAnimation(360, 270, centerX, centerY, depthZ, true);
        }
        mCloseFlipAnimation.setDuration(duration);
        mCloseFlipAnimation.setFillAfter(true);
        mCloseFlipAnimation.setInterpolator(new AccelerateInterpolator());
        mCloseFlipAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                Rotate3dAnimation mRotateAnimation = new Rotate3dAnimation(90, 0, centerX, centerY, depthZ, false);
                mRotateAnimation.setDuration(duration);
                mRotateAnimation.setFillAfter(true);
                mRotateAnimation.setInterpolator(new DecelerateInterpolator());
                mContentView.startAnimation(mRotateAnimation);

            }
        });
    }

    private void animates() {
        //计算中心点
        centerX = mContentView.getWidth() / 2.0f;
        centerY = mContentView.getHeight() / 2.0f;
        depthZ = 400.0f;

        if (mOpenFlipAnimation == null) {
            initOpenAnim();
        }

        if (mCloseFlipAnimation == null) {
            initCloseAnim();
        }

        //用作判断当前点击事件发生时动画是否正在执行
        if (mOpenFlipAnimation.hasStarted() && !mOpenFlipAnimation.hasEnded()) {
            return;
        }
        if (mCloseFlipAnimation.hasStarted() && !mCloseFlipAnimation.hasEnded()) {
            return;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCloseFlipAnimation.cancel();
        mOpenFlipAnimation.cancel();
    }
}
