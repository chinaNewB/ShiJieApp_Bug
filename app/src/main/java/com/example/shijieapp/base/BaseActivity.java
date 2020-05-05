package com.example.shijieapp.base;

import android.app.ActivityManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.example.shijieapp.utils.ToastUtil;

/**
 * Date: 2020/5/2 19:24
 * author:renzhipeng
 */

public class BaseActivity extends AppCompatActivity {
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 1500) {
                //弹出提示，可以有多种方式
                ToastUtil.showToast(ToastUtil.TYPE_INFO, "再按一次退出程序");
                exitTime = System.currentTimeMillis();

            } else {
                /*finish();
                System.exit(0);*/
                int currentVersion = android.os.Build.VERSION.SDK_INT;
                if (currentVersion > android.os.Build.VERSION_CODES.ECLAIR_MR1) {
                    Intent startMain = new Intent(Intent.ACTION_MAIN);
                    startMain.addCategory(Intent.CATEGORY_HOME);
                    startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(startMain);
                    System.exit(0);
                } else {// android2.1
                    ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                    am.restartPackage(getPackageName());
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
