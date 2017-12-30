package com.example.greendaotest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anye.greendao.gen.cartBeanDao;

import java.util.List;

import bean.cartBean;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private EditText et;
    private cartBeanDao cbd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et=findViewById(R.id.et);
        tv=findViewById(R.id.tv);
      cbd = MyApplication.getInstances().getDaoSession().getCartBeanDao();
    }

    public void select(View view) {
        List<cartBean> users = cbd.loadAll();
        String userName = "";
        for (int i = 0; i < users.size(); i++) {
            userName +="id:"+ users.get(i).getId() + "---name:"+users.get(i).getName() + "---num:"+users.get(i).getNum()+"\n";
        }
       tv.setText(userName);

    }

    public void add(View view) {
        cartBean  cb=new cartBean(2L,"a","aa","aaa","aaaa",true,1);
        cbd.insert(cb);//添加一个
        Toast.makeText(this,"OK"+cbd,Toast.LENGTH_SHORT).show();
    }

    public void alter(View view) {
        cartBean updateData = new cartBean();
        updateData.setId(Long.parseLong(et.getText().toString()));
        updateData.setName("修改了name");
        cbd.update(updateData);
    }

    public void delete(View view) {
        cbd.deleteByKey(Long.parseLong(et.getText().toString()));
    }
}
