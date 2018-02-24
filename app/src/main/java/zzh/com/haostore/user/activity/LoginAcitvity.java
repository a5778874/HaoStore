package zzh.com.haostore.user.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import zzh.com.haostore.R;

/**
 * Created by Administrator on 2018/1/18.
 */

public class LoginAcitvity extends Activity implements View.OnClickListener{
    private TextView tv_toRegist;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        tv_toRegist=findViewById(R.id.tv_register);
        tv_toRegist.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_register:
                startActivity(new Intent(LoginAcitvity.this,RegisterAcitvity.class));
                break;
        }
    }
}
