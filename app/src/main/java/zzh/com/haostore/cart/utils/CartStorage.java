package zzh.com.haostore.cart.utils;

import android.content.Context;
import android.util.SparseArray;

import zzh.com.haostore.app.MyApplication;
import zzh.com.haostore.home.bean.GoodsInfoBean;

/**
 * Created by Administrator on 2017/12/30.
 */

public class CartStorage {

    private static CartStorage cartStorage;
    private Context context;
    private SparseArray<GoodsInfoBean> CartSparseArray;

    //初始化购物车存储类，使用单例能保证存储类是唯一的对象
    public static CartStorage getInstant(){

        if (cartStorage==null){
            cartStorage=new CartStorage(MyApplication.getContext());
        }
        return cartStorage;
    }


    private CartStorage(Context context){
        this.context=context;
        CartSparseArray=new SparseArray<>();
        //当这类加载时，读取本地存储的购物车列表信息，放在集合里
        localDataToList();
    }

    private void localDataToList() {

    }


}
