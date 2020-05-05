package com.example.shijieapp.ui.myview;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.shijieapp.R;


/**
 * @author 1278324384@qq.com
 */
public class DialogNoInput extends android.app.Dialog implements View.OnClickListener {

    private TextView mTitle, mMessage, mCancel, mConfirm;
    private String title, message, cancel, confirm;
    private IOnCancelListener iOnCancelListener;
    private IOnConfirmListener iOnConfirmListener;

    public String getTitle() {
        return title;
    }

    public DialogNoInput setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public DialogNoInput setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getCancel() {
        return cancel;
    }

    public DialogNoInput setCancel(String cancel, IOnCancelListener cancelListener) {
        this.cancel = cancel;
        this.iOnCancelListener = cancelListener;
        return this;
    }

    public String getConfirm() {
        return confirm;
    }

    public DialogNoInput setConfirm(String confirm, IOnConfirmListener confirmListener) {
        this.confirm = confirm;
        this.iOnConfirmListener = confirmListener;
        return this;
    }

    public DialogNoInput(@NonNull Context context) {
        super(context);
    }

    public DialogNoInput(@NonNull Context context, int themeId) {
        super(context, themeId);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.self_dialog);
        //设置宽度，这段百度都有
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        Point size = new Point();
        d.getSize(size);
        //设置dialog的宽度为当前屏幕的80%
        p.width = (int) (size.x * 0.8);
        getWindow().setAttributes(p);


        mTitle = findViewById(R.id.tv_title);
        mMessage = findViewById(R.id.tv_message);
        mCancel = findViewById(R.id.tv_cancle);
        mConfirm = findViewById(R.id.tv_confirm);

        if (!TextUtils.isEmpty(title)) {
            mTitle.setText(title);
        }

        if (!TextUtils.isEmpty(message)) {
            mMessage.setText(message);
        }
        if (!TextUtils.isEmpty(cancel)) {
            mCancel.setText(cancel);
        }
        if (!TextUtils.isEmpty(confirm)) {
            mConfirm.setText(confirm);
        }

        mCancel.setOnClickListener(this);
        mConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancle:
                if (iOnCancelListener != null) {
                    iOnCancelListener.onCancel(this);
                }
                dismiss();
                break;
            case R.id.tv_confirm:
                if (iOnConfirmListener != null) {
                    iOnConfirmListener.onConfirm(this);
                }
                dismiss();
                break;
            default:
                break;
        }
    }

    public interface IOnCancelListener {
        void onCancel(DialogNoInput dialog);
    }

    public interface IOnConfirmListener {
        void onConfirm(DialogNoInput dialog);
    }
}