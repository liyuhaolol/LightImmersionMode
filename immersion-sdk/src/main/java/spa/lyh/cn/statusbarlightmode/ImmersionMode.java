package spa.lyh.cn.statusbarlightmode;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import spa.lyh.cn.statusbarlightmode.helpers.immersionmode.ImmersionHelper;

/**
 * Created by liyuhao on 2017/4/26.
 */

public class ImmersionMode {
    private static String TAG = "ImmersionMode";

    private static ImmersionMode instance;
    private ImmersionConfiguration configuration;
    private ImmersionConfiguration temporaryConfig;

    private View starusView;

    //手机型号或者版本,0为未检测,1为MIUI,2为Flyme,3为Android6.0以上,4为OPPO的5.1版本以及其他Android版本
    private int PhoneType = 0;


    public static ImmersionMode getInstance(){
        if (instance == null){
            synchronized (ImmersionMode.class){
                if (instance == null){
                    instance = new ImmersionMode();
                }
            }
        }
        return instance;
    }


    public synchronized void init(ImmersionConfiguration configuration){
        if (configuration == null){
            throw new IllegalArgumentException("ImmersionMode configuration can not be initialized with null");
        } else {
            if (this.configuration == null){
                Log.e(TAG,"Initialize ImmersionMode with configuration");
                this.configuration = configuration;
            }else {
                Log.e(TAG,"Try to initialize ImmersionMode which had already been initialized before. To re-init ImmersionMode with new configuration call ImmersionMode.destroy() at first.");
            }
        }
    }

    public void setTemporaryConfig(ImmersionConfiguration config){
        this.temporaryConfig = config;
    }

    public void destory(){
        if (this.configuration != null){
            Log.e(TAG,"Destroy configuration");
            this.configuration = null;
        }
    }

    public boolean execImmersionMode(Activity activity){
        boolean mark = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            ImmersionConfiguration config = backupConfig(configuration);
            if (temporaryConfig != null){
                setTemporaryConfiguration(temporaryConfig);
            }
            if (configuration.enable == ImmersionConfiguration.ENABLE){

                starusView = ImmersionHelper.statusBarFitToAPP(activity,configuration.defaultColor);
                mark = true;
            }
            if (configuration.navigationBarEnable == ImmersionConfiguration.ENABLE){
                ImmersionHelper.NavigationBarFitToAPP(activity,configuration.navigationBarColor);
            }

            configuration = config;
            temporaryConfig = null;
        }
        return mark;
    }

    private ImmersionConfiguration backupConfig(ImmersionConfiguration configuration){
        ImmersionConfiguration config = new ImmersionConfiguration.Builder(configuration.context)
                .enableImmersionMode(configuration.enable,configuration.navigationBarEnable)
                .setIntColor(configuration.defaultColor)
                .setNavigationBarIntColor(configuration.navigationBarColor)
                .build();
        return config;
    }
    private void setTemporaryConfiguration(ImmersionConfiguration tConfig){
            configuration.enable = tConfig.enable;
            if (configuration.navigationBarEnable == ImmersionConfiguration.ENABLE){
                //默认配置为启动导航栏
                if (tConfig.navigationBarEnable == ImmersionConfiguration.DISABLE){
                    //修改配置为关闭导航栏
                    configuration.navigationBarEnable = ImmersionConfiguration.ENABLE;
                    configuration.navigationBarColor = Color.parseColor(ImmersionConfiguration.getBlackColor());
                }else {
                    configuration.navigationBarEnable = tConfig.navigationBarEnable;
                    configuration.navigationBarColor = tConfig.navigationBarColor;
                }
            }else {
                configuration.navigationBarEnable = tConfig.navigationBarEnable;
                configuration.navigationBarColor = tConfig.navigationBarColor;
            }
            configuration.defaultColor = tConfig.defaultColor;
    }

    /**
     * only work between sdk 19 and sdk 21
     */
    public void setStarusViewGONE(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            if (starusView != null){
                starusView.setVisibility(View.GONE);
            }
        }
    }
    /**
     * only work between sdk 19 and sdk 21
     */
    public void setStarusViewVISIBLE(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            if (starusView != null){
                starusView.setVisibility(View.VISIBLE);
            }
        }
    }

    public void throwDisableERROR(Activity activity){
        Log.e(TAG,"Disable must called before 'setContentView()',or it will not work properly.");
        Toast.makeText(activity,"Disable must called before 'setContentView()',or it will not work properly.",Toast.LENGTH_LONG).show();
    }

    /**
     * get phoneType
     * @return code
     */
    public int getPhoneType() {
        return PhoneType;
    }

    /**
     * set phoneType
     * @param phoneType code
     */
    public void setPhoneType(int phoneType) {
        PhoneType = phoneType;
    }
}
