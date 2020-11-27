package com.wzy.plguinservice;

import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;

import dalvik.system.DexClassLoader;

public class PluginApk {
    //加载Apk实体对象
    private DexClassLoader mClassLoader;
    private Resources mResource;
    private PackageInfo mPackageInfo;
    private AssetManager mAssetManager;

    public PluginApk(DexClassLoader mClassLoader, Resources mResource, PackageInfo mPackageInfo, AssetManager mAssetManager) {
        this.mClassLoader = mClassLoader;
        this.mResource = mResource;
        this.mPackageInfo = mPackageInfo;
        this.mAssetManager = mAssetManager;
    }

    public DexClassLoader getClassLoader() {
        return mClassLoader;
    }

    public Resources getResource() {
        return mResource;
    }

    public PackageInfo getPackageInfo() {
        return mPackageInfo;
    }

    public AssetManager getAssetManager() {
        return mAssetManager;
    }
}
