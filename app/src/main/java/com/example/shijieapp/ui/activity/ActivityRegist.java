package com.example.shijieapp.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shijieapp.R;
import com.example.shijieapp.dto.user.UserUtils;
import com.example.shijieapp.utils.InputUtil;
import com.example.shijieapp.utils.OnOneClickListener;
import com.example.shijieapp.utils.ToastUtil;
import com.example.shijieapp.utils.WindowStyleUtil;


public class ActivityRegist extends AppCompatActivity {
    private EditText mEdtPhone;
    private EditText mEdtPwd;
    private EditText mEdtPwdAgain;
    private Button regist;
    private String phones, pwds, pwdsagain;
    private UserUtils userUtil;

    private TextView closeRegist, registAppname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        initView();


        WindowStyleUtil.getInstance().windowSettingColor(this, false);
        InputUtil.setFontFamily(registAppname);
        InputUtil.setHintTextSize(mEdtPhone, "请输入手机号", 12);
        InputUtil.setHintTextSize(mEdtPwd, "请输入密码   -   支持16位数字和英文字母", 12);
        InputUtil.setHintTextSize(mEdtPwdAgain, "请确认密码", 12);

        closeRegist.setOnClickListener(new OnOneClickListener() {
            @Override
            public void oneClick(View v) {
                finish();
            }
        });

        regist.setOnClickListener(new OnOneClickListener() {
            @Override
            public void oneClick(View v) {
                phones = mEdtPhone.getText().toString();
                pwds = mEdtPwd.getText().toString();
                pwdsagain = mEdtPwdAgain.getText().toString();

                if (phones.isEmpty()) {
                    //                    loginUtil.showToast("手机号不能为空");
                    ToastUtil.showToast(ToastUtil.TYPE_WARN, "手机号不能为空");
                } else if (pwds.isEmpty()) {
                    //                    loginUtil.showToast("密码不能为空");
                    ToastUtil.showToast(ToastUtil.TYPE_WARN, "密码不能为空");
                } else if (pwdsagain.isEmpty()) {
                    //                    loginUtil.showToast("请确认密码");
                    ToastUtil.showToast(ToastUtil.TYPE_WARN, "请确认密码");
                } else {
                    if (InputUtil.isChinaPhoneLegal(phones)) {
                        if (userUtil.queryPhoneOnly(Long.parseLong(phones))) {
                            //                            loginUtil.showToast("账号已存在");
                            ToastUtil.showToast(ToastUtil.TYPE_ERROR, "账号已存在");
                        } else {
                            if (pwds.length() < 8) {
                                //                                loginUtil.showToast("密码长度小于8位");
                                ToastUtil.showToast(ToastUtil.TYPE_WARN, "密码长度小于8位");
                            } else if (pwds.length() > 16) {
                                //                                loginUtil.showToast("密码长度大于16位");
                                ToastUtil.showToast(ToastUtil.TYPE_ERROR, "密码长度大于16位");
                            } else {
                                if (!pwdsagain.equals(pwds)) {
                                    //                                    loginUtil.showToast("确认密码错误");
                                    ToastUtil.showToast(ToastUtil.TYPE_ERROR, "确认密码错误");
                                } else {
                                    userUtil.insert(Long.parseLong(phones), pwds);
                                    Intent registSuccess = new Intent(ActivityRegist.this, ActivityLogin.class);
                                    startActivity(registSuccess);
                                    //                                    loginUtil.showToast("注册成功");
                                    ToastUtil.showToast(ToastUtil.TYPE_SUCCESS, "注册成功");
                                    finish();
                                }
                            }
                        }
                    } else {
                        //                        loginUtil.showToast("请输入正确的手机号");
                        ToastUtil.showToast(ToastUtil.TYPE_WARN, "请输入正确的手机号");
                    }
                }

            }
        });


    }

    private void initView() {
        mEdtPhone = findViewById(R.id.regist_etv_phone);
        mEdtPwd = findViewById(R.id.regist_etv_pwd);
        mEdtPwdAgain = findViewById(R.id.regist_etv_pwd_again);

        regist = findViewById(R.id.regist);
        registAppname = findViewById(R.id.regist_app_name);
        closeRegist = findViewById(R.id.close_regist);
        userUtil = new UserUtils(this);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        finish();
    }
}

