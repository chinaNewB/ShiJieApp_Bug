package com.example.shijieapp.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.shijieapp.R;
import com.example.shijieapp.utils.OnOneClickListener;
import com.example.shijieapp.utils.WindowStyleUtil;

import java.util.ArrayList;

import me.wangyuwei.particleview.ParticleView;


/**
 * @author 1278324384@qq.com
 */
public class ActivitySplash extends AppCompatActivity {

    private final int SDK_PERMISSION_REQUEST = 127;
    private ParticleView mParticleView;
    private TextView time;
    private CountDownTimer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPersimmions();

        initView();
        initListener();
    }

    private void initListener() {
        mParticleView.startAnim();
        time.setOnClickListener(new OnOneClickListener() {
            @Override
            public void oneClick(View v) {
                Intent intent=   new Intent(ActivitySplash.this, ActivityLogin.class);
                startActivity(intent);
                finish();
            }
        });

        timer = new CountDownTimer(6000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                //倒计时显示文本秒数 +1实现非零归一操作
                time.setText(millisUntilFinished /1000 + "丨跳过");
            }
            @Override
            public void onFinish() {
                time.callOnClick();
            }
        }.start();

    }

    private void initView() {
        WindowStyleUtil.getInstance().windowSettingColor(this, true);
        mParticleView = findViewById(R.id.mParticleView);
        time = findViewById(R.id.time);
    }


    @Override
    protected void onStart(){
        super.onStart();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timer != null){
            timer.cancel();
        }
        mParticleView = null;
    }


    //========获取手机一些权限================
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }
                break;
            default:
                break;
            // add other cases for more permissions
        }
    }


    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();

            // 读写权限，不同意下次打开继续请求
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            // 读取电话状态权限
            if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
            }

            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }

        } else {
            return true;
        }
    }
}
