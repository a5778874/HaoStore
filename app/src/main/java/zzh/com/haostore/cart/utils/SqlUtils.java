package zzh.com.haostore.cart.utils;


import java.util.List;

import zzh.com.haostore.app.MyApplication;
import zzh.com.haostore.cart.beans.CartBean;
import zzh.com.haostore.cart.database.CartBeanDao;


/**
 * Created by Administrator on 2017/12/31.
 */

public class SqlUtils {

    //得到数据库操作对象
    private static CartBeanDao getSqlInstant() {
        return MyApplication.getInstances().getDaoSession().getCartBeanDao();
    }

    //保存到本地
    public static void saveLocal(CartBean bean) {
        getSqlInstant().insert(bean);
    }

    //读取本地所有数据
    public static List<CartBean> readLocal() {
        return getSqlInstant().loadAll();
    }

    public static CartBean quaryID(String productID){
       return getSqlInstant().queryBuilder().where(CartBeanDao.Properties.Product_id.eq(productID)).unique();
    }


    //修改某条数据
    public static void alterItem(CartBean bean) {
        getSqlInstant().update(bean);
    }

    //根据产品ID删除某条
    public static void deleteItem(String productID) {
        getSqlInstant().queryBuilder().where(CartBeanDao.Properties.Product_id.eq(productID)).buildDelete().executeDeleteWithoutDetachingEntities();
    }


}
