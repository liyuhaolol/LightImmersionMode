package spa.lyh.cn.lightimmersionmode.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import spa.lyh.cn.statusbarlightmode.ImmersionConfiguration;
import spa.lyh.cn.statusbarlightmode.ImmersionMode;
import spa.lyh.cn.statusbarlightmode.TemporaryConfig;

/**
 * Created by liyuhao on 2017/4/26.
 */

public class BaseActivity extends AppCompatActivity{
    public ImmersionMode immersionMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        immersionMode = ImmersionMode.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        immersionMode.execImmersionMode(this);
    }

    public void changeStatusBarColor(int ResId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            TemporaryConfig tConfig = new TemporaryConfig(this);
            tConfig.setTemporaryResIdColor(ResId);
            immersionMode.setTemporaryConfig(tConfig);
            immersionMode.execImmersionMode(this);
        }
    }
    public void changeStatusBarColor(String color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            TemporaryConfig tConfig = new TemporaryConfig(this);
            tConfig.setTemporaryStringColor(color);
            immersionMode.setTemporaryConfig(tConfig);
            immersionMode.execImmersionMode(this);
        }
    }

    /**
     * 这个方法必须在immersionMode.execImmersionMode(this);之前调用，也就是在onResume()之前调用，
     * 沉浸式一旦启动，就不可逆，请注意。
     */
    public void DisableImmersionMode(){
        TemporaryConfig config = new TemporaryConfig(this);
        config.setEnable(ImmersionConfiguration.DISABLE);
        immersionMode.setTemporaryConfig(config);
    }

    /**
     * 只工作在sdk19到21之间，这个方法实际开发中不一定会用到
     */
    public void setGONEtoStatusview(){
        immersionMode.setStarusViewGONE();
    }
    /**
     * 只工作在sdk19到21之间，这个方法实际开发中不一定会用到
     */
    public void setVISIBLEtoStatusview(){
        immersionMode.setStarusViewVISIBLE();
    }
}
