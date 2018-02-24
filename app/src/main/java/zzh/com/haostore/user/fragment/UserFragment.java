package zzh.com.haostore.user.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import zzh.com.haostore.R;
import zzh.com.haostore.user.activity.LoginAcitvity;
import zzh.com.haostore.user.activity.RegisterAcitvity;

/**
 * Created by Administrator on 2017/8/10.
 */

public class UserFragment extends Fragment implements View.OnClickListener{
    private LinearLayout ll_head;

    public static UserFragment fragment = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment, null);
        initView(view);
        return view;

    }

    private void initView(View view) {
        ll_head=view.findViewById(R.id.ll_head);
        ll_head.setOnClickListener(this);
    }

    public static Fragment getInstance() {
        if (null == fragment) {
            fragment = new UserFragment();
        }
        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_head:
                startActivity(new Intent(getActivity(),LoginAcitvity.class));
                break;
        }
    }
}
