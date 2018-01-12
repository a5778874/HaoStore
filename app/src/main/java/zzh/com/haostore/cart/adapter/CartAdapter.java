package zzh.com.haostore.cart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import zzh.com.haostore.R;
import zzh.com.haostore.cart.beans.CartBean;
import zzh.com.haostore.cart.utils.CartStorage;
import zzh.com.haostore.cart.utils.SqlUtils;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final myHolder mHolder = (myHolder) holder;
        final CartBean cartBean = cartBeanList.get(position);
        String itemName = cartBean.getName();
        int num = cartBean.getNum();                    //数量
        final float singlePrice = Float.parseFloat(cartBean.getPrice().substring(2));       //单价,价格格式为“￥ 18.00”所以要截取字符串转为数字
        float sumPrice = singlePrice * num;//合计
        String imgURL = cartBean.getImgURL();
        final boolean isCheck = cartBean.getIsCheck();
        mHolder.checkBox.setChecked(isCheck);

        mHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CartBean cartBean1 = cartBean;
                cartBean1.setIsCheck(isChecked);
                SqlUtils.alterItem(cartBean1);
            }
        });
        mHolder.price_text.setText("" + sumPrice);
        mHolder.itemName_text.setText(itemName);
        mHolder.numView.setNum(num);
        Glide.with(context).load(imgURL).into(mHolder.item_pic);

        //修改数量的同时，保存到本地
        mHolder.numView.setOnNumChangeListener(new NumView.OnNumChangeListener() {
            @Override
            public void OnNumChange(int num) {
                mHolder.price_text.setText(singlePrice * num + "");
                cartBean.setNum(num);
                SqlUtils.alterItem(cartBean);
                Log.d("TAG", "OnNumChange: " + cartBean.toString());
            }
        });

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


    //删除选中数据
    public void deleteDatas() {

        if (cartBeanList.size() > 0 && cartBeanList != null) {

            for (int i=0;i<cartBeanList.size();i++){
                CartBean cartBean = cartBeanList.get(i);
                if (cartBean.getIsCheck()){
                    cartBeanList.remove(cartBean);    //从集合中删除
                    SqlUtils.deleteItem(cartBean.getProduct_id());//删除本地数据库这条数据
                    notifyItemRemoved(i);  //通知更新列表
                    i--;                   //移除后循环列表数量减1
                }

            }

        }


}
}
