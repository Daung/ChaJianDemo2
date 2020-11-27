package com.wzy.plguinservice;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class PluginManager {
    private static final PluginManager manager = new PluginManager();
    private Context mContext;

    private PluginManager() {

    }

    public static PluginManager getInstance() {
        return manager;
    }

    public void init(Context context) {
        this.mContext = context;
    }

    private PluginApk mPluginApk;

    public PluginApk getPluginApk() {
        return mPluginApk;
    }

    public  void copyAssets(String oldPath) {

        String newPath = mContext.getFilesDir().getAbsolutePath() + "/yiban-debug.apk";
        try {
//            File file = new File(newPath);
//            if (file.exists()) {
//                boolean delete = file.delete();
//                if (delete) {
//                    System.out.println("delete success");
//                } else {
//                    System.out.println("delete failed");
//                }
//            }

//            boolean mkdirs = file.mkdirs();// 如果文件夹不存在，则递归
//            if (mkdirs) {
//                System.out.println("创建成功");
//            } else {
//                System.out.println("创建失败");
//            }

            InputStream is = mContext.getAssets().open(oldPath);
            FileOutputStream fos = new FileOutputStream(new File(newPath));
            byte[] buffer = new byte[1024];
            int byteCount;
            while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
                // buffer字节
                fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
            }
            fos.flush();// 刷新缓冲区
            is.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadApk(newPath);
    }


    public void loadApk(String apkPath) {
        PackageInfo packageInfo = mContext.getPackageManager().getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES);
        if (packageInfo == null) {
            return;
        }

        DexClassLoader classLoader = createDexClassLoader(apkPath);

        AssetManager am = createAssetManager(apkPath);

        Resources resources = createResource(am);

        mPluginApk = new PluginApk(classLoader, resources, packageInfo, am);
    }

    private Resources createResource(AssetManager am) {
        Resources res = mContext.getResources();
        return new Resources(am, res.getDisplayMetrics(), res.getConfiguration());
    }

    private AssetManager createAssetManager(String apkPath) {
        try {
            AssetManager am = AssetManager.class.newInstance();
            Method method = AssetManager.class.getMethod("addAssetPath", String.class);
            method.invoke(am, apkPath);
            return am;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private DexClassLoader createDexClassLoader(String apkPath) {
        File file = mContext.getDir("dex", Context.MODE_PRIVATE);

        return new DexClassLoader(apkPath, file.getAbsolutePath(), null, mContext.getClassLoader());
    }


}
