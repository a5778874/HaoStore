package zzh.com.haostore.discovery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import zzh.com.haostore.R;

/**
 * Created by Administrator on 2017/8/10.
 */

public class DiscoveryFragment extends Fragment {
    public static DiscoveryFragment fragment = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.discovery_fragment, null);
        return view;

    }

    public static Fragment getInstance() {
        if (null == fragment) {
            fragment = new DiscoveryFragment();
        }
        return fragment;
    }
}
