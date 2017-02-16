package com.xiaoxiao.salarycalculator.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by zxx on 2017/2/16.
 * email:t-xiaoxiao.zhang@pcitc.com
 */
public class MyApplication extends Application {
	private static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		context = this;
	}

	public static Context getContext() {
		return context;
	}
}
