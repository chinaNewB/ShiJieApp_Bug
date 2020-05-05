package com.example.shijieapp.utils;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shijieapp.base.BaseApplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Date: 2020/5/2 17:57
 * author:renzhipeng
 */
public class InputUtil {

    //清空输入框内容

    public static void clearClick(ImageButton imageButton, final EditText editText){
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.getText().clear();
            }
        });
    }

    //editText 的hint字体大小

    public static void setHintTextSize(EditText editText, String hintText, int textSize) {
        // 新建一个可以添加属性的文本对象
        SpannableString ss = new SpannableString(hintText);
        // 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(textSize, true);
        // 附加属性到文本
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 设置hint , 一定要进行转换,否则属性会消失
        editText.setHint(new SpannedString(ss));
    }

    //根据输入框字符长度，清除按钮显示问题

    public static void getInputString(String s,ImageButton imageButton){
        if(s.length()<=0){
            imageButton.setVisibility(View.GONE);
        }else{
            imageButton.setVisibility(View.VISIBLE);
        }
    }


    public static void setFontFamily(TextView appname){
        Typeface typeface = Typeface.createFromAsset(BaseApplication.getAppContext().getAssets(),"fonts/rzgf.ttf");
        appname.setTypeface(typeface);
    }


    public  static boolean isChinaPhoneLegal(String str)
            throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

}
