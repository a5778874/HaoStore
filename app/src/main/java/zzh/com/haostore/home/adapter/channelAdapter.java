package zzh.com.haostore.home.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import zzh.com.haostore.R;
import zzh.com.haostore.app.Constant;
import zzh.com.haostore.home.bean.HomeResultBean;
import zzh.com.haostore.utils.ToastUtils;

/**
 * Created by zzh on 2017/8/16.
 */

public class channelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<HomeResultBean.ResultBean.ChannelInfoBean> channel_info;

    public channelAdapter(List<HomeResultBean.ResultBean.ChannelInfoBean> channel_info, Context mContext) {
        this.channel_info = channel_info;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.channel_item, null);
        return new mHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final mHolder mh = (mHolder) holder;
        //得到屏幕宽度
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        //计算每个channel要设置的平均宽度
        int channelWidth = width / (channel_info.size() / 2);
        mh.channelLayout.setLayoutParams(new LinearLayoutCompat.LayoutParams(channelWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
        mh.channelText.setText(channel_info.get(position).getChannel_name());
        Glide.with(mContext).load(Constant.BASE_IMGURL + channel_info.get(position).getImage()).into(mh.channelImg);

        mh.channelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast(mContext, "channelID:"+channel_info.get(position).getValue().getChannel_id());
            }
        });


    }

    @Override
    public int getItemCount() {
        return channel_info.size();
    }

    class mHolder extends RecyclerView.ViewHolder {
        private LinearLayout channelLayout;
        private TextView channelText;
        private ImageView channelImg;

        public mHolder(View itemView) {
            super(itemView);
            channelLayout = (LinearLayout) itemView.findViewById(R.id.ll_channelItem);
            channelText = (TextView) itemView.findViewById(R.id.tv_channelText);
            channelImg = (ImageView) itemView.findViewById(R.id.iv_channelImg);
        }

    }
}
