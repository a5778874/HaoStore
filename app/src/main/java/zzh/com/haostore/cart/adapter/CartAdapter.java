package zzh.com.haostore.cart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import zzh.com.haostore.R;
import zzh.com.haostore.cart.beans.CartBean;
import zzh.com.haostore.cart.view.NumView;

/**
 * Created by Administrator on 2018/1/10.
 */

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<CartBean> cartBeanList;

    public CartAdapter(Context context, List<CartBean> cartBeanList) {
        this.context = context;
        this.cartBeanList = cartBeanList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = View.inflate(context, R.layout.item_shop_cart, null);
        return new myHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        myHolder mHolder = (myHolder) holder;
        CartBean cartBean = cartBeanList.get(position);
        String itemName = cartBean.getName();
        int num = cartBean.getNum();                    //数量
        String singlePrice = cartBean.getPrice();       //单价
        float sumPrice = Float.parseFloat(singlePrice.substring(2)) * num;//合计,价格格式为“￥ 18.00”所以要截取字符串转为数字
        String imgURL = cartBean.getImgURL();
        boolean isCheck = cartBean.getIsCheck();

        mHolder.checkBox.setChecked(isCheck);
        mHolder.price_text.setText(sumPrice+"");
        mHolder.itemName_text.setText(itemName);
        mHolder.numView.setNum(num);
        Glide.with(context).load(imgURL).into(mHolder.item_pic);

    }

    @Override
    public int getItemCount() {
        return cartBeanList.size();
    }


    class myHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private TextView price_text;
        private TextView itemName_text;
        private ImageView item_pic;
        private NumView numView;


        public myHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.cb_item);
            price_text = itemView.findViewById(R.id.tv_price);
            itemName_text = itemView.findViewById(R.id.tv_itemName);
            item_pic = itemView.findViewById(R.id.iv_itemImg);
            numView = itemView.findViewById(R.id.numberview);
        }
    }
}
