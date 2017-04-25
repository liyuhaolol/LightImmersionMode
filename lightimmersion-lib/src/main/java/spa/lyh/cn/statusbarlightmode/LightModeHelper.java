package spa.lyh.cn.statusbarlightmode;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;


import spa.lyh.cn.statusbarlightmode.helpers.AndroidMHelper;
import spa.lyh.cn.statusbarlightmode.helpers.FlymeHelper;
import spa.lyh.cn.statusbarlightmode.helpers.MIUIHelper;

import static spa.lyh.cn.statusbarlightmode.barutils.barUtils.getStatusBarHeight;
import static spa.lyh.cn.statusbarlightmode.barutils.barUtils.transparencyBar;
import static spa.lyh.cn.statusbarlightmode.barutils.barUtils.isLightRGB;

/**
 * Created by liyuhao on 2017/3/17.
 * 设置深浅状态栏颜色以及修改状态栏字体颜色,本工具主要针对Android4.4(api >= 19)的手机系统
 * targetSdkVersion < (19)Android4.4，那不用使用本工具，无法生效
 * 本工具有3个主要方法:statusBarFit2APP(),onlyChangeStatusColor(),onlySetStatusBarMode()
 * statusBarFit2APP():本方式实现的主要方法,一般只使用这个方法,通过api版本生效不同的效果
 * onlyChangeStatusColor():只修改状态栏颜色,备用给特别需求的人
 * onlySetStatusBarMode():只修改状态栏字体深浅,备用给特别需求的人
 * ##########################################################################
 * 具体实现思路,api == 19时,使用沉浸式状态栏来实现修改状态栏位置颜色
 * (不存在release版的api 20)api >= 21,使用新封装的api直接修改状态栏颜色,非沉浸式
 * MIUI,Flyme从api 19支持修改状态栏字体,原生系统和其他是从api 23开始支持修改状态栏字体
 */

public class LightModeHelper {
    private static String TAG = "LightModeHelper";
    private static LightModeHelper helper;

    public static LightModeHelper getInstance(){
        if (helper == null){
            synchronized (LightModeHelper.class){
                if (helper == null){
                    helper = new LightModeHelper();
                }
            }
        }
        return helper;
    }

    /**
     * 同时适配状态栏颜色和深浅状态栏字体颜色<p>MIUI,Flyme需要api版本 >= 19,生效颜色和字体
     * <p>原生Andoird,或其他系统需要api版本 >= 23,生效颜色和字体,< 23将不会生效字体
     * @param activity activity实例
     * @param color string型的Color值可带透明度，如"#FFFED952",#ARGB格式
     */
    public void statusBarFit2APP(Activity activity,String color){
        int sColor;
        try{
            sColor = Color.parseColor(color);
        }catch (Exception e){
            Log.e(TAG,"Unknown color");
            return;
        }
        statusBarFit2APP(activity,sColor);
    }
    /**
     * 同时适配状态栏颜色和深浅状态栏字体颜色<p>MIUI,Flyme需要api版本 >= 19,生效颜色和字体
     * <p>原生Andoird,或其他系统需要api版本 >= 23,生效颜色和字体,< 23将不会生效字体
     * @param activity activity实例
     * @param color int型的Color值,可以从color.xml获取
     */
    public void statusBarFit2APP(Activity activity,int color){
        statusBarFitToAPP(activity,color);
    }

    /**
     * 只修改状态栏颜色<p>需要api版本 >= 19
     * @param activity activity实例
     * @param color string型的Color值可带透明度，如"#FFFED952",#ARGB格式
     */
    public void onlyChangeStatusColor(Activity activity,String color){
        int sColor;
        try{
            sColor = Color.parseColor(color);
        }catch (Exception e){
            Log.e(TAG,"Unknown color");
            return;
        }
        onlyChangeStatusColor(activity,sColor);
    }
    /**
     * 只修改状态栏颜色<p>需要api版本 >= 19
     * @param activity activity实例
     * @param color int型的Color值,可以从color.xml获取
     */
    public void onlyChangeStatusColor(Activity activity,int color){
        changeStatusBarColor(activity,color);
    }

    /**
     * 只修改状态栏深浅状态<p>MIUI,Flyme需要api版本 >= 19
     * <p>原生Andoird,或其他系统需要api版本 >= 23
     * @param activity activity实例
     * @param isLightMode 是否为浅色状态栏模式<p>true:浅色状态栏，深色字<p>false:深色状态栏，浅色字
     */
    public void onlySetStatusBarMode(Activity activity,boolean isLightMode){
        setStatusBarMode(activity,isLightMode);
    }



    private void statusBarFitToAPP(Activity activity,int color){
        //获取颜色
        int redValue = Color.red(color);
        int greenValue = Color.green(color);
        int blueValue = Color.blue(color);
        int[] colorArry = new int[]{redValue,greenValue,blueValue};
        changeStatusBarColor(activity,color);
        setStatusBarMode(activity,isLightRGB(colorArry));
    }

    private void changeStatusBarColor(Activity activity,int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.setStatusBarColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            transparencyBar(activity);
            ViewGroup vg = (ViewGroup) activity.getWindow().getDecorView();
            View v = new View(activity);
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,getStatusBarHeight(activity));
            v.setBackgroundColor(color);
            v.setLayoutParams(params);
            vg.addView(v);
        }

    }

    private boolean setStatusBarMode(Activity activity,boolean isLightMode){
        boolean flag = false;
        if (new MIUIHelper().setLightMode(activity,isLightMode)){
            flag = true;
        }else if (new FlymeHelper().setLightMode(activity,isLightMode)){
            flag = true;
        }else if (new AndroidMHelper().setLightMode(activity,isLightMode)){
            flag = true;
        }
        return flag;
    }
}
