package zzh.com.haostore.user.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import zzh.com.haostore.R;

/**
 * Created by Administrator on 2017/8/10.
 */

public class UserFragment extends Fragment {
    private EditText et_ip;
    private Button bt_saveIP;
    public static UserFragment fragment = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment, null);
        et_ip= (EditText) view.findViewById(R.id.et_ip);
        bt_saveIP= (Button) view.findViewById(R.id.bt_saveIP);
        return view;

    }

    public static Fragment getInstance() {
        if (null == fragment) {
            fragment = new UserFragment();
        }
        return fragment;
    }
}
