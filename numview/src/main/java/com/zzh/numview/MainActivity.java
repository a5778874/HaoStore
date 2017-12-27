package com.zzh.numview;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NumView nv=findViewById(R.id.nv);
        nv.setOnNumChangeListener(new NumView.OnNumChangeListener() {
            @Override
            public void OnNumChange(int num) {
                Toast.makeText(MainActivity.this,num+"",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
