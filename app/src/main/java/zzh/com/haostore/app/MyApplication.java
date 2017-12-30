package zzh.com.haostore.app;

import android.app.Application;
import android.content.Context;

import com.lzy.okgo.OkGo;

/**
 * Created by zzh on 2017/8/12.
 */

public class MyApplication extends Application {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext = this;
        //初始化OkGo
        OkGo.getInstance().init(this);
    }

    // 获取全局上下文
    public static Context getContext() {
        return mContext;
    }
}
