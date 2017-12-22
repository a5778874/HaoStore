package zzh.com.haostore.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import zzh.com.haostore.R;
import zzh.com.haostore.app.Constant;
import zzh.com.haostore.home.bean.HomeResultBean;
import zzh.com.haostore.view.GoodsInfoView.GoodsInfoActivity;

/**
 * Created by zzh on 2017/8/22.
 */

class recommendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<HomeResultBean.ResultBean.RecommendInfoBean> recommendInfoBeanList;
    private Context context;

    public recommendAdapter(List<HomeResultBean.ResultBean.RecommendInfoBean> recommendInfoBeanList, Context context) {
        this.context = context;
        this.recommendInfoBeanList = recommendInfoBeanList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecommendItemHolder(View.inflate(context, R.layout.recommend_item, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecommendItemHolder rHolder = (RecommendItemHolder) holder;
        final String recommendNameString = recommendInfoBeanList.get(position).getName();
        final String recommendPriceString = "¥ " + recommendInfoBeanList.get(position).getCover_price();
        rHolder.recommendName.setText(recommendNameString);
        rHolder.recommendPrice.setText(recommendPriceString);
        final String recommendImg = Constant.BASE_IMGURL + recommendInfoBeanList.get(position).getFigure();
        Glide.with(context).load(recommendImg).into(rHolder.recommendItemImg);
        rHolder.ll_recommendItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(context, GoodsInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("goodName", recommendNameString);
                bundle.putString("goodPrice", recommendPriceString);
                bundle.putString("goodImg", recommendImg);
                it.putExtra("goodsInfo", bundle);
                context.startActivity(it);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recommendInfoBeanList.size();
    }

    class RecommendItemHolder extends RecyclerView.ViewHolder {
        private LinearLayout ll_recommendItem;
        private ImageView recommendItemImg;
        private TextView recommendPrice, recommendName;

        public RecommendItemHolder(View itemView) {
            super(itemView);
            ll_recommendItem = itemView.findViewById(R.id.ll_recommendItem);
            recommendItemImg = itemView.findViewById(R.id.iv_recommendItemImg);
            recommendPrice = itemView.findViewById(R.id.tv_recommendPrice);
            recommendName = itemView.findViewById(R.id.tv_recommendName);
        }
    }
}
