package spa.lyh.cn.statusbarlightmode;

import android.app.Activity;
import android.os.Build;
import android.util.Log;

import spa.lyh.cn.statusbarlightmode.helpers.immersionmode.ImmersionHelper;

/**
 * Created by liyuhao on 2017/4/26.
 */

public class ImmersionMode {
    private static String TAG = "ImmersionMode";

    private static ImmersionMode instance;
    private ImmersionConfiguration configuration;
    private TemporaryConfig tConfig;


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

    public void setTemporaryConfig(TemporaryConfig config){
        this.tConfig = config;
    }

    public void destory(){
        if (this.configuration != null){
            Log.e(TAG,"Destroy configuration");
            this.configuration = null;
        }
    }

    public void execImmersionMode(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            ImmersionConfiguration config = backupConfig(configuration);
            if (tConfig != null){
                setTemporaryConfiguration(tConfig);
            }
            if (configuration.enable == ImmersionConfiguration.ENABLE){
                ImmersionHelper.statusBarFitToAPP(activity,configuration.defaultColor);
            }

            configuration = config;
            tConfig = null;
        }
    }

    private ImmersionConfiguration backupConfig(ImmersionConfiguration configuration){
        ImmersionConfiguration config = new ImmersionConfiguration.Builder(configuration.context)
                .enableImmersionMode(configuration.enable)
                .setIntColor(configuration.defaultColor)
                .build();
        return config;
    }
    private void setTemporaryConfiguration(TemporaryConfig tConfig){
        if (tConfig.getEnable() != 0){
            configuration.enable = tConfig.getEnable();
        }
        if (tConfig.getTemporaryColor() != 0){
            configuration.defaultColor = tConfig.getTemporaryColor();
        }
    }
}
