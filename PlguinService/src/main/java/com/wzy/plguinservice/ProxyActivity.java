package com.wzy.plguinservice;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class ProxyActivity extends AppCompatActivity {

    private static final String TAG = "ProxyActivity";

    private String mClassName;
    private PluginApk mPluginApk;


    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        mClassName = getIntent().getStringExtra("className");
        mPluginApk = PluginManager.getInstance().getPluginApk();
        launchPluginActivity();
    }

    private void launchPluginActivity() {
        if (mPluginApk == null) {
            Log.e(TAG, "please loading your apk first ");

        }
        try {
            Class<?> clazz = mPluginApk.getClassLoader().loadClass(mClassName);
            Object object = clazz.newInstance();
            if (object instanceof IPlugin) {
                IPlugin iPlugin = (IPlugin) object;
                iPlugin.attach(this);
                Bundle bundle = new Bundle();
                bundle.putInt("FROM", IPlugin.FROM_EXTERNAL);
                iPlugin.onCreate(bundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Resources getResources() {
        return mPluginApk != null ? mPluginApk.getResource() : super.getResources();
    }

    @Override
    public AssetManager getAssets() {
        return mPluginApk != null ? mPluginApk.getAssetManager() : super.getAssets();
    }

    @Override
    public ClassLoader getClassLoader() {
        return mPluginApk != null ? mPluginApk.getClassLoader() : super.getClassLoader();
    }
}
