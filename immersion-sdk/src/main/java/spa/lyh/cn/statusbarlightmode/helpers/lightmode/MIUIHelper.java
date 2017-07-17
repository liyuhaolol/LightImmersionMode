package spa.lyh.cn.statusbarlightmode.helpers.lightmode;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by liyuhao on 2017/3/17.
 */

public class MIUIHelper implements ILightModeHelper{
    @Override
    public boolean setLightMode(Activity activity, boolean isLightMode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            Window window = activity.getWindow();
            if (window != null){
                Class clazz = window.getClass();
                try{
                    Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                    Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                    Method extraFlagField = clazz.getMethod("setExtraFlags",int.class,int.class);
                    if (isLightMode){
                        //切换到浅色模式
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            //当miui的版本大于201(7.7.13)年发布以后所有最新的MIUI版本，稳定版版本号未知，但是发布日期需要晚于2017年7月13日
                            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                        }
                        //调用MIUI自己的方法，设置状态栏，只支持老版本MIUI，新版MIUI调用这句话会被自动忽略，不会造成稳定性影响
                        extraFlagField.invoke(window,field.getInt(layoutParams),field.getInt(layoutParams));
                    }else {
                        //还原为深色模式
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            //当miui的版本大于201(7.7.13)年发布以后所有最新的MIUI版本，稳定版版本号未知，但是发布日期需要晚于2017年7月13日
                            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                        }
                        //调用MIUI自己的方法，设置状态栏，只支持老版本MIUI，新版MIUI调用这句话会被自动忽略，不会造成稳定性影响
                        extraFlagField.invoke(window,0,field.getInt(layoutParams));
                    }
                    return true;
                }catch (Exception e){
                    Log.e("LightModeException","Failed to match MIUI");
                }
            }
        }
        return false;
    }
}
