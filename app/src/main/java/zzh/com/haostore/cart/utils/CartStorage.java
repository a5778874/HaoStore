package zzh.com.haostore.cart.utils;

import android.content.Context;

import java.util.List;

import zzh.com.haostore.app.MyApplication;
import zzh.com.haostore.cart.beans.CartBean;

/**
 * Created by Administrator on 2017/12/30.
 */

public class CartStorage {

    private static CartStorage cartStorage;
    private Context context;


    //初始化购物车存储类，使用单例能保证存储类是唯一的对象
    public static CartStorage getInstant(){

        if (cartStorage==null){
            cartStorage=new CartStorage(MyApplication.getContext());
        }
        return cartStorage;
    }


    private CartStorage(Context context){
        this.context=context;
        List<CartBean> cartBeans = showLocalList();

    }

    private List<CartBean> showLocalList() {
        return  SqlUtils.readLocal();
    }





}
