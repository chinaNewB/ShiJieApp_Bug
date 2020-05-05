package com.example.shijieapp.ui.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shijieapp.R;
import com.example.shijieapp.base.BaseActivity;
import com.example.shijieapp.base.BaseApplication;
import com.example.shijieapp.dto.user.User;
import com.example.shijieapp.dto.user.UserUtils;
import com.example.shijieapp.utils.InputUtil;
import com.example.shijieapp.utils.OnOneClickListener;
import com.example.shijieapp.utils.ToastUtil;
import com.example.shijieapp.utils.WindowStyleUtil;

import java.util.List;


/**
 * @author 1278324384@qq.com
 */
public class ActivityLogin extends BaseActivity {

    private LinearLayout linearLayout;
    private ImageButton login;
    private EditText userPhone, userPwd;
    private TextView regist, yanzlogin, loginAppname;
    private ImageButton clearUserName, clearUserPwd;
    private String userPhones, userPwds;
    private Intent intent;
    private UserUtils userUtils;


    private SharedPreferences mSharedPreferences = null;
    private SharedPreferences.Editor mEditor = null;
    private boolean isChangePwdReturn = false;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_login);

        initView();
        setting();
        initListener();

    }

    private void initListener() {
        login.setOnClickListener(new OnOneClickListener() {
            @Override
            public void oneClick(View v) {
                userPhones = userPhone.getText().toString().trim();
                userPwds = userPwd.getText().toString().trim();

                if (userPhones.isEmpty()) {
                    ToastUtil.showToast(ToastUtil.TYPE_WARN, getString(R.string.text_phone_not_null));
                } else if (userPwds.isEmpty()) {
                    ToastUtil.showToast(ToastUtil.TYPE_WARN, getString(R.string.text_password_not_null));
                } else {
                    if(InputUtil.isChinaPhoneLegal(userPhones)){
                        if(userUtils.queryPhoneOnly(Long.parseLong(userPhones))){
                            if(userUtils.query(Long.parseLong(userPhones),userPwds)){
                                mEditor.putString("phone", userPhones);
                                mEditor.putString("pwd", userPwds);
                                mEditor.putBoolean("isLoginSelf",true);
                                BaseApplication.setUserPhones(userPhones);
                                mEditor.apply();
                                Intent intent = new Intent(ActivityLogin.this, ActivityHomePage.class);
                                startActivity(intent);
                                finish();
                            }else{
                                //                                loginUtil.showToast("密码错误");
                                ToastUtil.showToast(ToastUtil.TYPE_ERROR,"密码错误");
                            }
                        }else{
                            //                            loginUtil.showToast("账号不存在");
                            ToastUtil.showToast(ToastUtil.TYPE_ERROR,"账号不存在");

                        }
                    }else{
                        ToastUtil.showToast(ToastUtil.TYPE_WARN,"请输入正确的手机号");

                    }
                }
            }
        });

        regist.setOnClickListener(new OnOneClickListener() {
            @Override
            public void oneClick(View v) {
                intent = new Intent(ActivityLogin.this, ActivityRegist.class);
                startActivity(intent);
            }
        });

        yanzlogin.setOnClickListener(new OnOneClickListener() {
            @Override
            public void oneClick(View v) {
                intent = new Intent(ActivityLogin.this, ActivitySmsLogin.class);
                startActivity(intent);
            }
        });

    }


    @SuppressLint("CommitPrefEdits")
    private void initView() {
        isChangePwdReturn = getIntent().getBooleanExtra("return2login", false);
        linearLayout = findViewById(R.id.login_layout);
        loginAppname = findViewById(R.id.login_app_name);
        login = findViewById(R.id.btn_login);
        userPhone = findViewById(R.id.etv_username);
        userPwd = findViewById(R.id.etv_user_pwd);
        regist = findViewById(R.id.tv_regist);
        yanzlogin = findViewById(R.id.tv_sms_login);
        clearUserName = findViewById(R.id.clear_etv_username);
        clearUserPwd = findViewById(R.id.clear_etv_userpwd);
        userUtils = new UserUtils(this);

        mSharedPreferences = getSharedPreferences("my preference", Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();


    }

    private void setting() {
        WindowStyleUtil.getInstance().windowSettingColor(this,false);
        InputUtil.setFontFamily(loginAppname);
        InputUtil.setHintTextSize(userPhone, getString(R.string.text_login_hint_phone), 15);
        InputUtil.setHintTextSize(userPwd, getString(R.string.text_login_hint_pwd), 15);
        getInputChangeLisnner(userPhone, clearUserName);
        getInputChangeLisnner(userPwd, clearUserPwd);
        InputUtil.clearClick(clearUserName, userPhone);
        InputUtil.clearClick(clearUserPwd, userPwd);

        String phoneShare = mSharedPreferences.getString("phone", "");
        String pwdShare = mSharedPreferences.getString("pwd","");

        if (isChangePwdReturn) {
            userPhone.setText(phoneShare);
            userPwd.setText(pwdShare);
            mEditor.remove("pwd");
            mEditor.putBoolean("isLoginSelf",false);
            mEditor.apply();

        }else {
            userPhone.setText(phoneShare);
            userPwd.setText(pwdShare);


            if (mSharedPreferences.getBoolean("isLoginSelf", false)) {
                BaseApplication.setUserPhones(phoneShare);
                startActivity(new Intent(ActivityLogin.this, ActivityHomePage.class));
                finish();
                //                mLoginPresenter.checkUserInfo(phoneShare, pwdShare);
            }
        }
    }



    //输入框监听事件封装方法

    private void getInputChangeLisnner(final EditText editText, final ImageButton imageButton) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                buttonColor(userPhone, userPwd);
                InputUtil.getInputString(editText.getText().toString(), imageButton);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                InputUtil.getInputString(editText.getText().toString(), imageButton);
                buttonColor(userPhone, userPwd);
            }

            @Override
            public void afterTextChanged(Editable s) {
                InputUtil.getInputString(editText.getText().toString(), imageButton);
                buttonColor(userPhone, userPwd);

            }
        });


    }

    //判断输入框字符长度，改变登录按钮样式

    @SuppressLint("NewApi")
    private void buttonColor(EditText editText1, EditText editText2) {
        userPhones = editText1.getText().toString();
        userPwds = editText2.getText().toString();
        if (userPhones.length() > 0 && userPwds.length() > 0) {
            login.setBackground(getDrawable(R.drawable.shape_login_has_input_all_style));
        } else {
            login.setBackground(getDrawable(R.drawable.shape_login_registe_etv));
        }
    }



    /*@TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void exitApp() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.AppTask> appTaskList = activityManager.getAppTasks();
        for (ActivityManager.AppTask appTask : appTaskList) {
            appTask.finishAndRemoveTask();
        }
    }


    *//** 上次点击返回键的时间 *//*
    private long lastBackPressed;
    *//** 两次点击的间隔时间 *//*
    private static final int QUIT_INTERVAL = 2000;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0) {
            long backPressed = System.currentTimeMillis();
            if (backPressed - lastBackPressed > QUIT_INTERVAL) {
                lastBackPressed = backPressed;
                ToastUtil.showToast(ToastUtil.TYPE_INFO, "再按一次退出");
            } else {
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
                *//*finish();
                System.exit(0);*//*
                //                exitAPP();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/
}
