package zzh.com.haostore.cart.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import zzh.com.haostore.R;
import zzh.com.haostore.cart.beans.CartBean;
import zzh.com.haostore.cart.utils.SqlUtils;

/**
 * Created by Administrator on 2017/8/10.
 */

public class CartFragment extends Fragment {
    private View view;
    private LinearLayout ll_empty_cart;
    private RecyclerView rv_cart;
    public static CartFragment fragment = null;
    private final  String TAG="tag";
    private List<CartBean> CartBeanList;

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
        LoadCart();

    }

    public static Fragment getInstance() {
        if (null == fragment) {
            fragment = new CartFragment();
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ................");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ..........");
    }



    //fragment使用hide时，生命周期方法不掉用。onHiddenChanged会调用，显示为false，切换到其它fragment为true
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
           LoadCart();

        }
    }


    //如果本地没数据，加载空的布局。有数据则显示列表布局
    private void LoadCart(){
        CartBeanList = SqlUtils.readLocal();
        Log.d(TAG, "initView: "+CartBeanList.size());
        if (CartBeanList!=null&&CartBeanList.size()>0){
            ll_empty_cart.setVisibility(View.GONE);
        }else {
            ll_empty_cart.setVisibility(View.VISIBLE);
        }
    }

}
