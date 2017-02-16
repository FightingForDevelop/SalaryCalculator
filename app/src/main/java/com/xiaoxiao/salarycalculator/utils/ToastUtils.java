package com.xiaoxiao.salarycalculator.utils;

import android.widget.Toast;

/**
 * Created by zxx on 2017/02/15.
 */
public class ToastUtils {

    private static Toast toast;


    public static void show(String content) {
        if (toast == null) {
            toast = Toast.makeText(MyApplication.getContext(), content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

}
