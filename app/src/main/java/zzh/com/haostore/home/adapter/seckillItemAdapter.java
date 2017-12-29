package zzh.com.haostore.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import zzh.com.haostore.R;
import zzh.com.haostore.app.Constant;
import zzh.com.haostore.home.bean.GoodsInfoBean;
import zzh.com.haostore.home.bean.HomeResultBean;
import zzh.com.haostore.view.GoodsInfoView.GoodsInfoActivity;

/**
 * Created by zzh on 2017/8/16.
 */

public class seckillItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private HomeResultBean.ResultBean.SeckillInfoBean seckill_info;


    public seckillItemAdapter(HomeResultBean.ResultBean.SeckillInfoBean seckill_info, Context context) {
        this.seckill_info = seckill_info;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.seckill_item, null);
        return new SeckillItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        SeckillItemHolder seckillItemHolder = (SeckillItemHolder) holder;
        final String nowPriceString = "¥ " + seckill_info.getList().get(position).getCover_price();
        final String oldPriceString = "¥ " + seckill_info.getList().get(position).getOrigin_price();
        final String seckillNameString = seckill_info.getList().get(position).getName();
        final String seckillID = seckill_info.getList().get(position).getProduct_id();
        seckillItemHolder.nowPrice.setText(nowPriceString); //现价
        seckillItemHolder.oldPrice.setText(oldPriceString); //原价
        final String imgURL = Constant.BASE_IMGURL + seckill_info.getList().get(position).getFigure();
        Glide.with(context).load(imgURL).into(seckillItemHolder.seckillTicketImg);
        //设置条目点击事件跳转到商品详情界面
        seckillItemHolder.seckillItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(context, GoodsInfoActivity.class);
                GoodsInfoBean goodsInfoBean = new GoodsInfoBean();
                goodsInfoBean.setCover_price(nowPriceString);
                goodsInfoBean.setFigure(imgURL);
                goodsInfoBean.setName(seckillNameString);
                goodsInfoBean.setProduct_id(seckillID);
                it.putExtra(Constant.GOODS_INFO_BEAN, goodsInfoBean);
                context.startActivity(it);
            }
        });
    }

    @Override
    public int getItemCount() {
        return seckill_info.getList().size();
    }

    class SeckillItemHolder extends RecyclerView.ViewHolder {
        private LinearLayout seckillItemLayout;
        private TextView nowPrice, oldPrice;
        private ImageView seckillTicketImg;

        public SeckillItemHolder(View itemView) {
            super(itemView);
            seckillItemLayout = (LinearLayout) itemView.findViewById(R.id.ll_seckillItem);
            nowPrice = (TextView) itemView.findViewById(R.id.tv_seckillNowPrice);
            oldPrice = (TextView) itemView.findViewById(R.id.tv_seckillOldPrice);
            seckillTicketImg = (ImageView) itemView.findViewById(R.id.iv_seckillTicket);
        }

    }

}
