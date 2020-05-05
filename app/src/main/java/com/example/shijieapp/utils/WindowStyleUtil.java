package com.example.shijieapp.utils;

import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author 1278324384@qq.com
 */
public class WindowStyleUtil {

    private static WindowStyleUtil windowStyleUtil = null;
    public static WindowStyleUtil getInstance() {
        if (windowStyleUtil == null) {
            windowStyleUtil = new WindowStyleUtil();
        }
        return windowStyleUtil;
    }


    //状态栏颜色

    public void windowSettingColor(Activity activity,Boolean whiteOrBlack) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (whiteOrBlack) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        } else {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

}
