package zzh.com.haostore.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import zzh.com.haostore.R;
import zzh.com.haostore.app.Constant;
import zzh.com.haostore.home.bean.GoodsInfoBean;
import zzh.com.haostore.home.bean.HomeResultBean;
import zzh.com.haostore.view.GoodsInfoView.GoodsInfoActivity;

/**
 * Created by zzh on 2017/8/22.
 */

class hotAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<HomeResultBean.ResultBean.HotInfoBean> hotInfoBeanList;


    public hotAdapter(List<HomeResultBean.ResultBean.HotInfoBean> hotInfoBeanList, Context context) {

        this.hotInfoBeanList = hotInfoBeanList;
        this.context = context;
        Log.d("hot", hotInfoBeanList.toString());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new mHolder(View.inflate(context, R.layout.hot_item, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final String hotNameString, hotPriceString, hotImgUrl, hotID;
        mHolder mholder = (mHolder) holder;
        hotID = hotInfoBeanList.get(position).getProduct_id();
        hotNameString = hotInfoBeanList.get(position).getName();
        mholder.hotName.setText(hotNameString);
        hotPriceString = "¥" + hotInfoBeanList.get(position).getCover_price();
        mholder.hotPrice.setText(hotPriceString);
        hotImgUrl = Constant.BASE_IMGURL + hotInfoBeanList.get(position).getFigure();
        Glide.with(context).load(hotImgUrl).into(mholder.hotImg);
        mholder.hotItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(context, GoodsInfoActivity.class);
                ;
                GoodsInfoBean goodsInfoBean = new GoodsInfoBean();
                goodsInfoBean.setCover_price(hotPriceString);
                goodsInfoBean.setFigure(hotImgUrl);
                goodsInfoBean.setName(hotNameString);
                goodsInfoBean.setProduct_id(hotID);
                it.putExtra(Constant.GOODS_INFO_BEAN, goodsInfoBean);
                context.startActivity(it);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hotInfoBeanList.size();
    }


    class mHolder extends RecyclerView.ViewHolder {
        private ImageView hotImg;
        private TextView hotName, hotPrice;
        private LinearLayout hotItem;

        public mHolder(View itemView) {
            super(itemView);
            hotItem = itemView.findViewById(R.id.ll_hotItem);
            hotImg = itemView.findViewById(R.id.iv_hotItemImg);
            hotName = itemView.findViewById(R.id.tv_hotName);
            hotPrice = itemView.findViewById(R.id.tv_hotPrice);

        }
    }
}
