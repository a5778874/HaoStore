package zzh.com.haostore.cart.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import zzh.com.haostore.R;

/**
 * Created by ZiHao on 2017/12/22.
 * 自定义控件：数量加减按钮
 */

public class NumView extends LinearLayout implements View.OnClickListener {
    private ImageView bt_add, bt_sub;
    private TextView tv_num;
    private Context mycontext;
    private int number = 1;  //当前数量
    private int max = 99;        //最大数量
    private int min = 1;        //最小数量


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }


    public NumView(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mycontext = context;
        //布局文件实例化，并加载到该类中
        View v = View.inflate(mycontext, R.layout.numview, this);
        //数量增加按钮
        bt_add = findViewById(R.id.bt_add);
        //数量减少按钮
        bt_sub = findViewById(R.id.bt_sub);
        tv_num = findViewById(R.id.tv_num);
        bt_add.setOnClickListener(this);
        bt_sub.setOnClickListener(this);
    }


    @Override

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add:
                AddNum();
                break;
            case R.id.bt_sub:
                SubNum();
                break;
        }
    }

    private void SubNum() {
        number = getTextNum();
        if (number > getMin()) {
            number--;
            setTextNum(number);
        }
        if (onNumChangeListener != null)
            onNumChangeListener.OnNumChange(number);
    }

    private void AddNum() {
        number = getTextNum();
        if (number < getMax()) {
            number++;
            setTextNum(number);
        }
        if (onNumChangeListener != null)
            onNumChangeListener.OnNumChange(number);
    }

    private int getTextNum() {
        return Integer.parseInt(tv_num.getText().toString());
    }

    public void setNum(int number) {
        setTextNum(number);
    }

    private void setTextNum(int num) {
        tv_num.setText("" + num);
    }

    public interface OnNumChangeListener {
        void OnNumChange(int num);
    }

    private OnNumChangeListener onNumChangeListener;

    public void setOnNumChangeListener(OnNumChangeListener onNumChangeListener) {
        this.onNumChangeListener = onNumChangeListener;
    }

}
