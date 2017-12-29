package zzh.com.haostore.view.GoodsInfoView.fragment;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.ysnows.page.Page;


import java.util.ArrayList;

import zzh.com.haostore.R;
import zzh.com.haostore.app.Constant;
import zzh.com.haostore.home.bean.GoodsInfoBean;


/**
 * A simple {@link Fragment} subclass.
 */
public class GoodsDetailFragment extends Fragment {


    private WebView webview;
    private ArrayList<String> list = new ArrayList<>();
    private Page page1;
    private CoordinatorLayout container;
    private Page.OnScrollListener onScrollListener;
    private TextView goodsInfoStyle;                 //请选择款式条目
    private LinearLayout goodsInfoStyleItemLayout; //请选择款式
    private TextView goodInfoName, goodInfoPrice, goodInfoDescription;
    private PhotoView goodPhoto;
    String goodName, goodPrice, goodImg;

    public GoodsDetailFragment() {

    }


    private static GoodsDetailFragment fragment = null;


    public static GoodsDetailFragment newInstance() {
        if (fragment == null) {
            fragment = new GoodsDetailFragment();
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_goods_detail_with_webview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Intent it = getActivity().getIntent();
        GoodsInfoBean gb = (GoodsInfoBean) it.getSerializableExtra(Constant.GOODS_INFO_BEAN);
        if (gb != null) {
            goodName = gb.getName();
            goodPrice = gb.getCover_price();
            goodImg = gb.getFigure();
        }

        initViews(view);
        initListener();
        initData();
    }

    private void initData() {
        goodInfoName.setText(goodName);
        goodInfoPrice.setText(goodPrice);
        Glide.with(getContext()).load(goodImg).into(goodPhoto);
    }

    private void initViews(View view) {
        goodsInfoStyleItemLayout = view.findViewById(R.id.style_item_layout);
        goodsInfoStyle = view.findViewById(R.id.tv_good_info_style);
        page1 = view.findViewById(R.id.pageOne);
        container = view.findViewById(R.id.container);
        goodInfoName = view.findViewById(R.id.tv_goodInfoName);
        goodInfoPrice = view.findViewById(R.id.tv_goodInfoPrice);
        goodInfoDescription = view.findViewById(R.id.tv_goodInfoDescription);
        goodPhoto = view.findViewById(R.id.pv_goodInfoImage);

        webview = view.findViewById(R.id.webview);
        initWebView();

    }

    private void initWebView() {
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("file:///android_asset/index.html");
    }


    private void initListener() {
        //设置“请选择样式”的显示
        goodsInfoStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (goodsInfoStyleItemLayout.getVisibility() == View.GONE) {
                    //设置drawableRight的图片
                    Drawable drawable = getResources().getDrawable(R.mipmap.home_arrow_down);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    goodsInfoStyle.setCompoundDrawables(null, null, drawable, null);
                    goodsInfoStyleItemLayout.setVisibility(View.VISIBLE);
                } else {
                    Drawable drawable = getResources().getDrawable(R.mipmap.home_arrow_right);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    goodsInfoStyle.setCompoundDrawables(null, null, drawable, null);
                    goodsInfoStyleItemLayout.setVisibility(View.GONE);
                }
            }
        });
    }


}
