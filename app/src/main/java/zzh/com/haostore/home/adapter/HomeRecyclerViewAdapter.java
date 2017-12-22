package zzh.com.haostore.home.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.zhy.magicviewpager.transformer.RotateYTransformer;

import java.util.List;

import cn.iwgang.countdownview.CountdownView;
import zzh.com.haostore.R;
import zzh.com.haostore.app.Constant;
import zzh.com.haostore.home.bean.HomeResultBean;
import zzh.com.haostore.utils.ToastUtils;

/**
 * Created by Administrator on 2017/8/10.
 */

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    public HomeResultBean.ResultBean getResultBean() {
        return resultBean;
    }


    private HomeResultBean.ResultBean resultBean;

    public void setResultBean(HomeResultBean.ResultBean resultBean) {
        this.resultBean = resultBean;
    }


    private final int BANNER = 0;//滚动广告
    private final int CHANNEL = 1;//分类频道
    private final int ACT = 2; //ViewPager显示活动信息
    private final int SECKILL = 3;//秒杀
    private final int RECOMMEND = 4;//推荐
    private final int HOT = 5;//热卖
    private int currentType = BANNER;
    private final String TAG = "TAG";
    boolean isSetBanner;  //是否已经设置过banner

    public HomeRecyclerViewAdapter(Context context) {
        Log.d(TAG, "HomeRecyclerViewAdapter: ");
        this.context = context;
        Log.d("TAG", "HomeRecyclerViewAdapter: " + resultBean);
    }


    //返回当前位置信息
    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case ACT:
                currentType = ACT;
                break;
            case SECKILL:
                currentType = SECKILL;
                break;
            case RECOMMEND:
                currentType = RECOMMEND;
                break;
            case HOT:
                currentType = HOT;
                break;
        }
        return currentType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        switch (viewType) {
            case BANNER:
                isSetBanner = false;
                return new BannerHolder(View.inflate(context, R.layout.banner, null));

            case CHANNEL:
                return new ChannelHolder(View.inflate(context, R.layout.channel, null));
            case ACT:
                return new ActHolder(View.inflate(context, R.layout.act, null));
            case SECKILL:
                return new SeckillHolder(View.inflate(context, R.layout.seckill, null));
            case RECOMMEND:
                return new RecommendHolder(View.inflate(context, R.layout.recommend, null));
            case HOT:
                return new HotHolder(View.inflate(context, R.layout.hot, null));
            default:

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d("TAG", "onBindViewHolder: " + position);
        if (resultBean != null) {
            //如果没有设置过banner才设置，不加判断可能引起复用太多图片导致错误
            if (getItemViewType(position) == BANNER && isSetBanner == false) {
                BannerHolder bHolder = (BannerHolder) holder;
                bHolder.setData(resultBean.getBanner_info(), context);
            } else if (getItemViewType(position) == CHANNEL) {
                ChannelHolder cHolder = (ChannelHolder) holder;
                cHolder.setData(resultBean.getChannel_info(), context);
            } else if (getItemViewType(position) == ACT) {
                ActHolder aHolder = (ActHolder) holder;
                aHolder.setData(resultBean.getAct_info(), context);
            } else if (getItemViewType(position) == SECKILL) {
                SeckillHolder sHolder = (SeckillHolder) holder;
                sHolder.setData(resultBean.getSeckill_info());
            } else if (getItemViewType(position) == RECOMMEND) {
                RecommendHolder rHolder = (RecommendHolder) holder;
                rHolder.setData(resultBean.getRecommend_info());
            } else if (getItemViewType(position) == HOT) {
                HotHolder hHolder = (HotHolder) holder;
                hHolder.setData(resultBean.getHot_info());
            }
        }

    }

    @Override
    public int getItemCount() {
        return 6;
    }


    class BannerHolder extends RecyclerView.ViewHolder {
        private SliderLayout slidelayout;

        public BannerHolder(View itemView) {
            super(itemView);
            slidelayout = itemView.findViewById(R.id.slider);
        }

        //使用AndroidImageSlider做banner
        private void setData(List<HomeResultBean.ResultBean.BannerInfoBean> bannerInfoBean, Context mContext) {
            int banner_num = bannerInfoBean.size();
            TextSliderView slider;

            //设置图片和文字描述
            for (int i = 0; i < banner_num; i++) {
                Log.d("TAG", "slider " + i);
                slider = new TextSliderView(mContext);
                slider.description("热点 " + (i + 1)).image(Constant.BASE_URL + "img/" + resultBean.getBanner_info().get(i).getImage());
                slider.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView slider) {
                        ToastUtils.showToast(context, slider.getUrl());
                    }
                });
                slidelayout.addSlider(slider);

            }
            isSetBanner = true;
            slidelayout.setDuration(3000);
            slidelayout.setCustomIndicator((PagerIndicator) itemView.findViewById(R.id.custom_indicator));//设置指示器的样式

        }


    }

    class ChannelHolder extends RecyclerView.ViewHolder {

        private RecyclerView rv_channel;

        public ChannelHolder(View itemView) {
            super(itemView);
            rv_channel = itemView.findViewById(R.id.rv_channel);
        }

        private void setData(List<HomeResultBean.ResultBean.ChannelInfoBean> channeInfoBean, Context mContext) {
            rv_channel.setAdapter(new channelAdapter(channeInfoBean, mContext));
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2, LinearLayoutManager.HORIZONTAL, false);
            rv_channel.setLayoutManager(gridLayoutManager);
        }

    }

    class ActHolder extends RecyclerView.ViewHolder {
        private ViewPager vp;

        public ActHolder(View itemView) {
            super(itemView);
            vp = (ViewPager) itemView.findViewById(R.id.actPager);
            vp.setPageMargin(20);
            vp.setOffscreenPageLimit(3);
            vp.setPageTransformer(true, new RotateYTransformer());
        }

        private void setData(final List<HomeResultBean.ResultBean.ActInfoBean> actInfoBean, final Context mContext) {
            Log.d("TAG", "setData:......");
            vp.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    return actInfoBean.size();
                }

                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return view == object;
                }


                @Override
                public Object instantiateItem(ViewGroup container, int position) {
                    ImageView imageView = new ImageView(mContext);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    Glide.with(mContext).load(Constant.BASE_URL + "img" + actInfoBean.get(position).getIcon_url()).into(imageView);
                    container.addView(imageView);
                    return imageView;
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    super.destroyItem(container, position, object);
                    container.removeView((View) object);
                }
            });
        }
    }

    class SeckillHolder extends RecyclerView.ViewHolder {
        private CountdownView cv;
        private TextView more;
        private LinearLayout main_seckill;
        private RecyclerView rv_item;

        public SeckillHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv_countdown);
            more = itemView.findViewById(R.id.seckillMore);
            main_seckill =  itemView.findViewById(R.id.main_seckill);
            rv_item =  itemView.findViewById(R.id.rv_seckillItem);

        }

        private void setData(final HomeResultBean.ResultBean.SeckillInfoBean seckillBean) {
            main_seckill.setVisibility(View.VISIBLE);
            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtils.showToast(context, "seckill");
                }
            });
            long seckillSecond = Long.parseLong(seckillBean.getEnd_time()) - Long.parseLong(seckillBean.getStart_time());
            cv.start(seckillSecond * 1000);
            rv_item.setAdapter(new seckillItemAdapter(seckillBean, context));
            rv_item.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));



        }
    }

    class RecommendHolder extends RecyclerView.ViewHolder
    {
        private TextView tv_recommendMore;
        private RecyclerView rv_recommend;

        public RecommendHolder(View itemView) {
            super(itemView);
            tv_recommendMore = itemView.findViewById(R.id.recommendMore);
            rv_recommend = itemView.findViewById(R.id.rv_recommend);
        }

        private void setData(List<HomeResultBean.ResultBean.RecommendInfoBean> recommendInfoBeanList) {
            rv_recommend.setAdapter(new recommendAdapter(recommendInfoBeanList, context));
            rv_recommend.setLayoutManager(new GridLayoutManager(context, 2, LinearLayoutManager.HORIZONTAL, false));
            tv_recommendMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtils.showToast(context, "more");
                }
            });
        }
    }

    class HotHolder extends RecyclerView.ViewHolder {
        private TextView tv_hotMore;
        private RecyclerView rv_hot;


        public HotHolder(View itemView) {
            super(itemView);
            tv_hotMore = itemView.findViewById(R.id.hotMore);
            rv_hot = itemView.findViewById(R.id.rv_hot);
        }

        private void setData(List<HomeResultBean.ResultBean.HotInfoBean> hotInfoBeanList) {
            rv_hot.setAdapter(new hotAdapter(hotInfoBeanList, context));
            rv_hot.setLayoutManager(new GridLayoutManager(context, 2));
            //设置水平垂直分割线
            rv_hot.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL));
            rv_hot.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
            tv_hotMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtils.showToast(context, "more");
                }
            });
        }
    }


}
