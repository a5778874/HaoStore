package zzh.com.haostore.app;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.heima.tabview.library.TabView;
import com.heima.tabview.library.TabViewChild;

import java.util.ArrayList;
import java.util.List;

import zzh.com.haostore.R;
import zzh.com.haostore.cart.fragment.CartFragment;
import zzh.com.haostore.discovery.fragment.DiscoveryFragment;
import zzh.com.haostore.home.fragment.HomeFragment;
import zzh.com.haostore.type.fragment.TypeFragment;
import zzh.com.haostore.user.fragment.UserFragment;

public class MainActivity extends FragmentActivity {
    List<TabViewChild> tabViewChildList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化tabView和fragment
        initFragmentTabhost();

    }


    /**
     * 使用开源库FragmentTabhostUtils来搭建框架
     */
    private void initFragmentTabhost() {
        tabViewChildList = new ArrayList<>();
        /**
         * TabViewChild
         * 第一个参数：导航栏上面的某一个tab被点击时候，相应的切换的图片
         * 第二个参数：导航栏上面的tab没有被被点击时候，相应的切换的图片
         * 第三个参数：导航栏上面的某一个tab的文字显示
         * 第四个参数：导航栏上面的某一个tab对应的Fragment对象，传进来就可以
         */
        TabViewChild tabViewChild01 = new TabViewChild(R.mipmap.main_home_press, R.mipmap.main_home, "首页", HomeFragment.getInstance());
        TabViewChild tabViewChild02 = new TabViewChild(R.mipmap.main_type_press, R.mipmap.main_type, "分类", TypeFragment.getInstance());
        TabViewChild tabViewChild03 = new TabViewChild(R.mipmap.main_discovery_press, R.mipmap.main_discovery, "发现", DiscoveryFragment.getInstance());
        TabViewChild tabViewChild04 = new TabViewChild(R.mipmap.main_cart_press, R.mipmap.main_cart, "购物车", CartFragment.getInstance());
        TabViewChild tabViewChild05 = new TabViewChild(R.mipmap.main_user_press, R.mipmap.main_user, "个人中心", UserFragment.getInstance());
        tabViewChildList.add(tabViewChild01);
        tabViewChildList.add(tabViewChild02);
        tabViewChildList.add(tabViewChild03);
        tabViewChildList.add(tabViewChild04);
        tabViewChildList.add(tabViewChild05);

        TabView tabView = (TabView) findViewById(R.id.tabView);
        tabView.setTabViewChild(tabViewChildList, getSupportFragmentManager());

    }
}
