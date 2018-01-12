package zzh.com.haostore.utils;

import android.util.Log;

/**
 * Created by ZZH on 2018/1/12.
 */

//价格格式为“￥ 18.00”，要想获取数字则要转换
public class PriceFormatUtils{
    public static double formatPrice(String price){
        double i=0.00;
        String param="¥ ";
        if (price.contains(param)){
            String[] split = price.split(param);
            i=Double.parseDouble(split[1]);
        }

        return i;

    }
}
