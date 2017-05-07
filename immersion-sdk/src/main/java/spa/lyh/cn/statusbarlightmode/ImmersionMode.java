package spa.lyh.cn.statusbarlightmode;

import android.app.Activity;
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

            configuration = config;
            temporaryConfig = null;
        }
        return mark;
    }

    private ImmersionConfiguration backupConfig(ImmersionConfiguration configuration){
        ImmersionConfiguration config = new ImmersionConfiguration.Builder(configuration.context)
                .enableImmersionMode(configuration.enable)
                .setIntColor(configuration.defaultColor)
                .build();
        return config;
    }
    private void setTemporaryConfiguration(ImmersionConfiguration tConfig){
            configuration.enable = tConfig.enable;
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
}
