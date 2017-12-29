package zzh.com.haostore.cart.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import zzh.com.haostore.R;

/**
 * Created by Administrator on 2017/8/10.
 */

public class CartFragment extends Fragment {
    private View view;
    private LinearLayout ll_empty_cart;
    private RecyclerView rv_cart;
    public static CartFragment fragment = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cart_fragment, null);
        initView();


        return view;

    }



    private void initView() {
        ll_empty_cart = view.findViewById(R.id.ll_empty_shopcart);
        rv_cart = view.findViewById(R.id.cart_recyclerview);

    }

    public static Fragment getInstance() {
        if (null == fragment) {
            fragment = new CartFragment();
        }
        return fragment;
    }
}
