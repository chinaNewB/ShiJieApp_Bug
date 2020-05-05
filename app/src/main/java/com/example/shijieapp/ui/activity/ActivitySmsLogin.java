package com.example.shijieapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shijieapp.R;
import com.example.shijieapp.base.BaseApplication;
import com.example.shijieapp.dto.user.User;
import com.example.shijieapp.dto.user.UserUtils;
import com.example.shijieapp.utils.InputUtil;
import com.example.shijieapp.utils.OnOneClickListener;
import com.example.shijieapp.utils.ToastUtil;
import com.example.shijieapp.utils.WindowStyleUtil;
import com.zhangym.customview.VerificationCodeView;

import java.util.List;
import java.util.Random;

public class ActivitySmsLogin extends AppCompatActivity {

    private Random mRandom = new Random();
    private EditText mEdtPhone;
    private EditText mEdtCode;
    private Button mBtnlogin;
    private VerificationCodeView mBtnCode;
    private String yzms,phones,codes;
    private UserUtils userUtils;
    private TextView closeDuanxin,yanzAppname;

    private SharedPreferences preferences = null;
    private SharedPreferences.Editor editor = null;
    private String mPwds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_login);

        initView();

        initSetting();

        initListener();

        mBtnlogin.setOnClickListener(new OnOneClickListener() {
            @Override
            public void oneClick(View v) {
                phones = mEdtPhone.getText().toString();
                yzms = mEdtCode.getText().toString();
                codes = mBtnCode.getVerificationText();

                if(phones.isEmpty()){
                    ToastUtil.showToast(ToastUtil.TYPE_WARN,"手机号不能为空");
                }else if(yzms.isEmpty()){
                    ToastUtil.showToast(ToastUtil.TYPE_WARN,"验证码不能为空");
                }else{
                    if(InputUtil.isChinaPhoneLegal(phones)){
                        if (userUtils.queryPhoneOnly(Long.parseLong(phones))) {
                            mPwds = userUtils.queryUserByPhone(Long.parseLong(phones)).getPwd();
                            if (yzms.equals(codes)) {
                                editor.putString("phone", phones);
                                editor.putString("pwd", mPwds);
                                editor.putBoolean("isLoginSelf", true);
                                editor.apply();
                                BaseApplication.setUserPhones(phones);

                                Intent intent = new Intent(ActivitySmsLogin.this, ActivityHomePage.class);
                                startActivity(intent);
                                finish();
                            } else {
                                ToastUtil.showToast(ToastUtil.TYPE_ERROR, "验证码错误");
                                changeCode();
                            }
                        } else {
                            ToastUtil.showToast(ToastUtil.TYPE_ERROR, "账号不存在");
                            changeCode();
                        }
                    }else{
                        ToastUtil.showToast(ToastUtil.TYPE_WARN,"请输入正确的手机号码");
                    }
                }
            }
        });


    }

    private void initListener() {
        mBtnCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCode();
                mEdtCode.getText().clear();
            }
        });

        closeDuanxin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initSetting() {
        WindowStyleUtil.getInstance().windowSettingColor(this,false);
        InputUtil.setFontFamily(yanzAppname);
        InputUtil.setHintTextSize(mEdtPhone,"请输入手机号",12);
        InputUtil.setHintTextSize(mEdtCode,"请输入验证码",12);
        changeCode();
    }

    private void initView() {
        preferences = getSharedPreferences("my preference", Context.MODE_PRIVATE);
        editor = preferences.edit();


        mEdtCode = findViewById(R.id.login_etv_code);
        mEdtPhone = findViewById(R.id.login_etv_phone);
        mBtnCode = findViewById(R.id.login_code_get);

        mBtnlogin = findViewById(R.id.login);
        yanzAppname = findViewById(R.id.yanz_appname);
        closeDuanxin = findViewById(R.id.close_duanxin);

        userUtils = new UserUtils(this);
    }

    public void changeCode(){
        String s = String.valueOf(mRandom.nextInt(10)) +
                String.valueOf(mRandom.nextInt(10)) +
                String.valueOf(mRandom.nextInt(10)) +
                String.valueOf(mRandom.nextInt(10));

        mBtnCode.setVerificationText(s);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        finish();
    }
}
