package zzh.com.haostore.cart.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.base.bj.paysdk.domain.TrPayResult;
import com.base.bj.paysdk.listener.PayResultListener;
import com.base.bj.paysdk.utils.TrPay;

import java.util.List;
import java.util.UUID;

import zzh.com.haostore.R;
import zzh.com.haostore.cart.adapter.CartAdapter;
import zzh.com.haostore.cart.beans.CartBean;
import zzh.com.haostore.cart.utils.SqlUtils;
import zzh.com.haostore.utils.PriceFormatUtils;
import zzh.com.haostore.utils.ToastUtils;

/**
 * Created by ZZH on 2017/8/10.
 */

public class CartFragment extends Fragment implements View.OnClickListener {
    private View view;
    private LinearLayout ll_empty_cart, ll_edit, ll_checkAll;
    private RecyclerView rv_cart;
    private TextView bt_edit, bt_editComplete;
    private TextView tv_totalPrice;
    private CheckBox checkAll, editAll;
    private Button bt_delete, bt_pay;
    public static CartFragment fragment = null;
    private final String TAG = "tag";
    private List<CartBean> CartBeanList;
    private List<CartBean> selectedList;
    private CartAdapter cartAdapter;
    private final String appkey="appkey"; //测试用appkey
    private final String channel="应用宝";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cart_fragment, null);
        initView();
        return view;

    }


    private void initView() {
        tv_totalPrice = view.findViewById(R.id.tv_cart_total);
        checkAll = view.findViewById(R.id.checkbox_all);
        //全选框使用点击事件而不使用onItemChange事件为了避免单选全部时产生冲突
        checkAll.setOnClickListener(this);
        editAll = view.findViewById(R.id.cb_editall);
        editAll.setOnClickListener(this);
        ll_empty_cart = view.findViewById(R.id.ll_empty_shopcart);
        ll_edit = view.findViewById(R.id.ll_edit);
        ll_checkAll = view.findViewById(R.id.ll_check_all);
        bt_pay = view.findViewById(R.id.btn_pay);
        bt_pay.setOnClickListener(this);
        bt_edit = view.findViewById(R.id.tv_shopcart_edit);
        bt_editComplete = view.findViewById(R.id.tv_shopcart_editComplete);
        bt_delete = view.findViewById(R.id.btn_delete);
        bt_edit.setOnClickListener(this);
        bt_editComplete.setOnClickListener(this);
        bt_delete.setOnClickListener(this);
        rv_cart = view.findViewById(R.id.cart_recyclerview);
        LoadCart();
        //如果选择的条目为全部时，自动勾选全选
        if (cartAdapter != null) {
            setCheckAll(cartAdapter.isItemCheckAll());
        }
        showTotalPrice();
    }

    public static Fragment getInstance() {
        if (null == fragment) {
            fragment = new CartFragment();
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 初始化支付服务
         * appkey appkey-应用AppKey
         * channel 应用商店渠道名(如：360，小米、华为、应用宝)
         */
        TrPay.getInstance(getContext()).initPaySdk(appkey, channel);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ..........");
    }


    //fragment使用hide时，生命周期方法不掉用。onHiddenChanged会调用，显示为false，切换到其它fragment为true
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            LoadCart();
        }
    }


    /**
     * 加载购物车列表
     */
    private void LoadCart() {
        CartBeanList = SqlUtils.readLocal();
        Log.d(TAG, "initView: " + CartBeanList.size());
        //如果购物车没数据则显示空布局，有数据显示列表
        if (CartBeanList != null && CartBeanList.size() > 0) {
            ll_empty_cart.setVisibility(View.GONE);
            cartAdapter = new CartAdapter(CartFragment.this, CartBeanList);
            rv_cart.setAdapter(cartAdapter);
            rv_cart.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        } else {
            ll_empty_cart.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_shopcart_edit:
                bt_edit.setVisibility(View.GONE);//隐藏编辑按钮
                bt_editComplete.setVisibility(View.VISIBLE);//显示完成按钮
                ll_checkAll.setVisibility(View.GONE);//隐藏结算栏
                ll_edit.setVisibility(View.VISIBLE);//打开编辑栏
                break;
            case R.id.tv_shopcart_editComplete:
                bt_edit.setVisibility(View.VISIBLE);//显示编辑按钮
                bt_editComplete.setVisibility(View.GONE);//隐藏完成按钮
                ll_checkAll.setVisibility(View.VISIBLE);//显示结算栏
                ll_edit.setVisibility(View.GONE);//隐藏编辑栏
                break;
            case R.id.btn_delete:
                cartAdapter.deleteDatas();
                showTotalPrice();
                setCheckAll(false);//删除后取消全选
                break;
            case R.id.btn_pay:
               pay();
                break;
            case R.id.checkbox_all:
                CheckBox cb_all = (CheckBox) v;
                cartAdapter.setItemCheckAll(cb_all.isChecked());
                cartAdapter.notifyDataSetChanged();
                break;
            case R.id.cb_editall:
                CheckBox edit_all = (CheckBox) v;
                cartAdapter.setItemCheckAll(edit_all.isChecked());
                cartAdapter.notifyDataSetChanged();
                break;
        }

    }

    /**
     * 显示购物车所有勾选的总价格
     */
    public void showTotalPrice() {
        tv_totalPrice.setText(getSelectedPrice() + "");
    }

    public double getSelectedPrice() {
        selectedList = SqlUtils.quarySelected();
        double totalPrice = 0.00;
        for (CartBean bean : selectedList) {
            totalPrice += PriceFormatUtils.formatPrice(bean.getPrice()) * bean.getNum();
        }
        return totalPrice;
    }


    public void setCheckAll(Boolean isCheck) {
        checkAll.setChecked(isCheck);
        editAll.setChecked(isCheck);
    }

    private void pay() {
        double sum = getSelectedPrice();
        String userid = "trpay@52yszd.com";//商户系统用户ID(如：trpay@52yszd.com，商户系统内唯一)
        String outtradeno = UUID.randomUUID() + "";//商户系统订单号(为便于演示，此处利用UUID生成模拟订单号，商户系统内唯一)
        String tradename = selectedList.get(0).getName()+"等共"+selectedList.size()+"件商品";//商品名称
        String backparams = "name=zzh";//商户系统回调参数
        String notifyurl = "http://101.200.13.92/notify/alipayTestNotify";//商户系统回调地址
        long amount =new Double(sum*100).longValue();//商品价格（单位：分。如1.5元传150）

        TrPay.getInstance(getActivity()).callPay(tradename, outtradeno, amount, backparams, notifyurl, userid, new PayResultListener() {
                /**
                 * 支付完成回调
                 * @param context      上下文
                 * @param outtradeno   商户系统订单号
                 * @param resultCode   支付状态(RESULT_CODE_SUCC：支付成功、RESULT_CODE_FAIL：支付失败)
                 * @param resultString 支付结果
                 * @param payType      支付类型（1：支付宝 2：微信）
                 * @param amount       支付金额
                 * @param tradename    商品名称
                 */
                @Override
                public void onPayFinish(Context context, String outtradeno, int resultCode, String resultString, int payType, Long amount, String tradename) {
                    Toast.makeText(context, resultString, Toast.LENGTH_LONG).show();
                    if (resultCode == TrPayResult.RESULT_CODE_SUCC.getId()) {//1：支付成功回调
                        TrPay.getInstance(context).closePayView();//关闭快捷支付页面
                        Toast.makeText(getActivity(), resultString, Toast.LENGTH_LONG).show();
                        //支付成功逻辑处理
                    } else if (resultCode == TrPayResult.RESULT_CODE_FAIL.getId()) {//2：支付失败回调
                        Toast.makeText(getActivity(), resultString, Toast.LENGTH_LONG).show();
                        //支付失败逻辑处理
                    }
                }
            });

    }


}
