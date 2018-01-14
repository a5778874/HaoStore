package com.zzh.trpay_demo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.base.bj.paysdk.domain.TrPayResult;
import com.base.bj.paysdk.listener.PayResultListener;
import com.base.bj.paysdk.utils.TrPay;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
private EditText et_name,et_price;
private Button bt_pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TrPay.getInstance(getApplicationContext()).initPaySdk("ba42af842077416b9f5b687e00ce4d56","baidu");
        et_name=findViewById(R.id.et_name);
        et_price=findViewById(R.id.et_price);
        bt_pay=findViewById(R.id.bt_pay);

        bt_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userid = "trpay@52yszd.com";//商户系统用户ID(如：trpay@52yszd.com，商户系统内唯一)
                String outtradeno = UUID.randomUUID() + "";//商户系统订单号(为便于演示，此处利用UUID生成模拟订单号，商户系统内唯一)
                String tradename = et_name.getText().toString().trim();//商品名称
                String backparams = "name=2&age=22";//商户系统回调参数
                String notifyurl = "http://101.200.13.92/notify/alipayTestNotify";//商户系统回调地址
                if (TextUtils.isEmpty(tradename)) {
                    Toast.makeText(MainActivity.this, "请输入商品名称！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(et_price.getText().toString().trim())) {
                    Toast.makeText(MainActivity.this, "请输入商品价格！", Toast.LENGTH_SHORT).show();
                    return;
                }
                Long amount = Long.valueOf(et_price.getText().toString().trim());//商品价格（单位：分。如1.5元传150）
                if (amount < 1) {
                    Toast.makeText(MainActivity.this, "金额不能小于1分！", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    /**
                     * 发起支付调用
                     */
                    TrPay.getInstance(MainActivity.this).callPay(tradename, outtradeno, amount, backparams, notifyurl, userid, new PayResultListener() {
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
                                Toast.makeText(MainActivity.this, resultString, Toast.LENGTH_LONG).show();
                                //支付成功逻辑处理
                            } else if (resultCode == TrPayResult.RESULT_CODE_FAIL.getId()) {//2：支付失败回调
                                Toast.makeText(MainActivity.this, resultString, Toast.LENGTH_LONG).show();
                                //支付失败逻辑处理
                            }
                        }
                    });
                }
            }
        });
    }
}
