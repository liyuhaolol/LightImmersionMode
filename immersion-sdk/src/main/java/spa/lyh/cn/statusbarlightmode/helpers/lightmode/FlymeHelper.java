package spa.lyh.cn.statusbarlightmode.helpers.lightmode;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by liyuhao on 2017/3/17.
 */

public class FlymeHelper implements ILightModeHelper{
    private static Method mSetStatusBarDarkIcon;

    private static Field mStatusBarColorFiled;

    private static int SYSTEM_UI_FLAG_LIGHT_STATUS_BAR = 0;



    @Override
    public boolean setLightMode(Activity activity, boolean isLightMode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                mSetStatusBarDarkIcon = Activity.class.getMethod("setStatusBarDarkIcon", boolean.class);
            } catch (NoSuchMethodException e) {
                Log.w("LightModeException","Can not found Flyme Method");
                return false;
            }
            try {
                mStatusBarColorFiled = WindowManager.LayoutParams.class.getField("statusBarColor");
            } catch (NoSuchFieldException e) {
                Log.w("LightModeException","Can not found Flyme Method");
                return false;
            }
            if (mSetStatusBarDarkIcon != null) {
                try {
                    mSetStatusBarDarkIcon.invoke(activity, isLightMode);
                    return true;
                } catch (Exception e) {
                    Log.w("LightModeException","Failed to match Flyme");
                    return false;
                }
            } else {
                return setStatusBarDarkIcon(activity.getWindow(), isLightMode);
            }
        }
        return false;
    }

    private boolean setStatusBarDarkIcon(Window window, boolean dark) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return changeMeizuFlag(window.getAttributes(), "MEIZU_FLAG_DARK_STATUS_BAR_ICON", dark);
        } else {
            View decorView = window.getDecorView();
            if (decorView != null) {
                setStatusBarColor(window, 0);
                return setStatusBarDarkIcon(decorView, dark);
            }
            return false;
        }
    }

    private boolean setStatusBarDarkIcon(View view, boolean dark) {
        int oldVis = view.getSystemUiVisibility();
        int newVis = oldVis;
        if (dark) {
            newVis |= SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        } else {
            newVis &= ~SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
        if (newVis != oldVis) {
            view.setSystemUiVisibility(newVis);
        }
        return true;
    }

    private void setStatusBarColor(Window window, int color) {
        WindowManager.LayoutParams winParams = window.getAttributes();
        if (mStatusBarColorFiled != null) {
            try {
                int oldColor = mStatusBarColorFiled.getInt(winParams);
                if (oldColor != color) {
                    mStatusBarColorFiled.set(winParams, color);
                    window.setAttributes(winParams);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean changeMeizuFlag(WindowManager.LayoutParams winParams, String flagName, boolean on) {
        try {
            Field f = winParams.getClass().getDeclaredField(flagName);
            f.setAccessible(true);
            int bits = f.getInt(winParams);
            Field f2 = winParams.getClass().getDeclaredField("meizuFlags");
            f2.setAccessible(true);
            int meizuFlags = f2.getInt(winParams);
            int oldFlags = meizuFlags;
            if (on) {
                meizuFlags |= bits;
            } else {
                meizuFlags &= ~bits;
            }
            if (oldFlags != meizuFlags) {
                f2.setInt(winParams, meizuFlags);
                return true;
            }
        } catch (Exception e) {
            Log.w("LightModeException","Failed to match Flyme");
        }
        return false;
    }
}
