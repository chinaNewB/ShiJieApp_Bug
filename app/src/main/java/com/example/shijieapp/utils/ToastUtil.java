package com.example.shijieapp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shijieapp.R;
import com.example.shijieapp.base.BaseApplication;


public class ToastUtil {

    private static Toast mToast;
    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_ERROR = 1;
    public static final int TYPE_WARN = 2;
    public static final int TYPE_INFO = 3;
    private static Context context = BaseApplication.getAppContext();
    private static final int duration = 1000;
    private static LayoutInflater sInflater;
    private static TextView sTextView;
    private static View sView;
    private static LinearLayout sToastType;

    @SuppressLint("WrongConstant")
    public static void showToast(int type, String mes) {

        if (mToast == null) {
            mToast = new Toast(context);
            sInflater = LayoutInflater.from(context);
            sView = sInflater.inflate(R.layout.self_toast, null);
            sTextView = sView.findViewById(R.id.tv_toast);
            sToastType = sView.findViewById(R.id.toast_type);
        }

        mToast.setView(sView);
        if (!TextUtils.isEmpty(mes)) {
            sTextView.setText(mes);
        }

        if (type == TYPE_SUCCESS) {
            sToastType.setBackgroundResource(R.drawable.bg_toast_success);
        } else if (type == TYPE_ERROR) {
            sToastType.setBackgroundResource(R.drawable.bg_toast_error);
        } else if (type == TYPE_WARN) {
            sToastType.setBackgroundResource(R.drawable.bg_toast_warn);
        } else if (type == TYPE_INFO) {
            sToastType.setBackgroundResource(R.drawable.bg_toast_info);
        }

        mToast.setDuration(duration);
        mToast.show();
    }


}
