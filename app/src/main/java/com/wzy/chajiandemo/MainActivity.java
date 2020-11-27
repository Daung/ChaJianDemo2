package com.wzy.chajiandemo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.wzy.plguinservice.PluginManager;
import com.wzy.plguinservice.ProxyActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PluginManager.getInstance().init(this);

        findViewById(R.id.tv_load).setOnClickListener((view) -> {
            String apkPath = "yb-debug.apk";

            PluginManager.getInstance().copyAssets(apkPath);
        });

        findViewById(R.id.tv_go).setOnClickListener((view) -> {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, ProxyActivity.class);
            intent.putExtra("className", "com.wzy.yb.MainActivity");
            startActivity(intent);
        });

    }
}