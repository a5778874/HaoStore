package zzh.com.haostore.app;

import android.app.Application;

import com.lzy.okgo.OkGo;

/**
 * Created by zzh on 2017/8/12.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化OkGo
        OkGo.getInstance().init(this);
    }
}
