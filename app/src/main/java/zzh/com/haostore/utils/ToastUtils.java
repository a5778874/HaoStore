package zzh.com.haostore.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by zzh on 2017/8/16.
 */

public class ToastUtils {
    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
