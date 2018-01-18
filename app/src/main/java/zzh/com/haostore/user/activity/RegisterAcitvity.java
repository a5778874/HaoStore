package zzh.com.haostore.user.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import zzh.com.haostore.R;

/**
 * Created by Administrator on 2018/1/18.
 */

public class RegisterAcitvity extends Activity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_register);
    }
}
