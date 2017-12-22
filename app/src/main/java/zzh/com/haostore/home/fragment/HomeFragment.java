package zzh.com.haostore.home.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import zzh.com.haostore.R;
import zzh.com.haostore.app.Constant;
import zzh.com.haostore.home.adapter.HomeRecyclerViewAdapter;
import zzh.com.haostore.home.bean.HomeResultBean;
import zzh.com.haostore.utils.ToastUtils;


/**
 * Created by Administrator on 2017/8/10.
 */

public class HomeFragment extends Fragment {
    private RecyclerView rv_home;
    private RelativeLayout homeLayout;
    private MaterialRefreshLayout materialRefreshLayout;
    private HomeRecyclerViewAdapter homeRecyclerViewAdapter;
    private ImageView backTop;  //返回顶部按钮
    View view;
    public static HomeFragment fragment = null;

    /**
     * 普通未刷新数据的状态
     */
    private final int NORMAL = 0;

    /**
     * 刷新数据状态
     */
    private final int REFRESHING = 1;

    private int refreshStatus = NORMAL;

    public static Fragment getInstance() {
        if (fragment == null) {
            fragment = new HomeFragment();
        }
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, null);
        homeLayout = view.findViewById(R.id.homeFragmentLayout);
        backTop = view.findViewById(R.id.iv_backTop);
        rv_home = view.findViewById(R.id.rv_home);
        homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(getContext());
        initRereshView();
        initNetworkData();
        backTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rv_home.scrollToPosition(0);
            }
        });
        return view;

    }

    /**
     * 设置下拉刷新
     */
    private void initRereshView() {
        materialRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.home_refresh);
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                refreshStatus = REFRESHING;
                initNetworkData();
                //5秒钟还没刷新成功就提示加载失败
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        if (refreshStatus == REFRESHING) {
                            materialRefreshLayout.finishRefresh();
                            refreshStatus = NORMAL;
                            ToastUtils.showToast(getContext(), "网络数据加载失败，请检查网络");
                        }
                    }
                }, 5000);

            }

        });


    }

    private void initNetworkData() {
        String url = Constant.BASE_JSONURL + "/HOME_URL.json";
        OkGo.<String>get(url).tag(getActivity()).execute(new StringCallback() {
            //请求成功调用
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                Log.d("TAG", "onSuccess: ");
                String json = response.body();
                HomeResultBean.ResultBean resultBean = formatToBean(json);
                homeRecyclerViewAdapter.setResultBean(resultBean);
                rv_home.setAdapter(homeRecyclerViewAdapter);
                Log.d("TAG", "onSuccess: " + homeRecyclerViewAdapter.getResultBean());

            }


            //请求成功结束调用，请求失败不掉用
            @Override
            public void onFinish() {
                super.onFinish();
                Log.d("TAG", "onFinish: " + refreshStatus);
                if (refreshStatus == REFRESHING) {
                    materialRefreshLayout.finishRefresh();
                    refreshStatus = NORMAL;
                    ToastUtils.showToast(getContext(), "加载完成");
                    Log.d("TAG", "onFinish: " + refreshStatus);
                }
            }


            //准备请求开始调用
            @Override
            public void onStart(com.lzy.okgo.request.base.Request<String, ? extends com.lzy.okgo.request.base.Request> request) {
                super.onStart(request);
                Log.d("TAG", "onStart: ");
                //这里要初始化recyclerViewAdapter一次，若不初始化请求数据失败就不能使用recyclerView的下拉刷新
                rv_home.setAdapter(homeRecyclerViewAdapter);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);//使用GridLayoutManager而不使用LinearLayoutManager是因为方便监听滚动的位置
                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

                    @Override
                    public int getSpanSize(int position) {
                        //滚动位置小于3，就隐藏返回顶部按钮，否则就显示
                        if (position <= 3) {
                            backTop.setVisibility(View.GONE);

                        } else {
                            backTop.setVisibility(View.VISIBLE);
                        }
                        return 1;
                    }
                });
                rv_home.setLayoutManager(gridLayoutManager);
            }


        });

    }

    /**
     * 把json转为bean对象
     */
    private HomeResultBean.ResultBean formatToBean(String json) {
        Gson gson = new Gson();
        HomeResultBean bean = gson.fromJson(json, HomeResultBean.class);
        HomeResultBean.ResultBean resultbean = bean.getResult();
        return resultbean;
    }


}
