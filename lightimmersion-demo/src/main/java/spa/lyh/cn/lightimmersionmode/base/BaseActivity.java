package spa.lyh.cn.lightimmersionmode.base;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import spa.lyh.cn.statusbarlightmode.ImmersionConfiguration;
import spa.lyh.cn.statusbarlightmode.ImmersionMode;

/**
 * Created by liyuhao on 2017/4/26.
 */

public class BaseActivity extends AppCompatActivity{
    public ImmersionMode immersionMode;

    private boolean flag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        immersionMode = ImmersionMode.getInstance();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        flag = immersionMode.execImmersionMode(this);
    }

    public void changeStatusBarColor(int ResId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            ImmersionConfiguration tConfig = new ImmersionConfiguration.Builder(this)
                    .enableImmersionMode(ImmersionConfiguration.ENABLE,ImmersionConfiguration.ENABLE)
                    .setColor(ResId)
                    .setNavigationBarColor(ResId)
                    .build();
            immersionMode.setTemporaryConfig(tConfig);
            immersionMode.execImmersionMode(this);
        }
    }
    public void changeStatusBarColor(String color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            ImmersionConfiguration tConfig = new ImmersionConfiguration.Builder(this)
                    .enableImmersionMode(ImmersionConfiguration.ENABLE,ImmersionConfiguration.ENABLE)
                    .setColor(color)
                    .setNavigationBarColor(color)
                    .build();
            immersionMode.setTemporaryConfig(tConfig);
            immersionMode.execImmersionMode(this);
        }
    }

    /**
     * 这个方法必须在setContentView()之前调用，因为沉浸式是不可逆的过程
     * 沉浸式一旦启动，就不可逆，请注意!此方法主要为了兼容sdk 23以下，sdk 23以上为新api，与沉浸式无关
     */
    public void DisableImmersionMode(){
        if (!flag){
            ImmersionConfiguration tConfig = new ImmersionConfiguration.Builder(this)
                    .enableImmersionMode(ImmersionConfiguration.DISABLE,ImmersionConfiguration.DISABLE)
                    .build();
            immersionMode.setTemporaryConfig(tConfig);
        }else {
            immersionMode.throwDisableERROR(this);
        }
    }

    /**
     * 只工作在sdk19到23之间，这个方法实际开发中不一定会用到
     */
    public void setGONEtoStatusview(){
        immersionMode.setStarusViewGONE();
    }
    /**
     * 只工作在sdk19到23之间，这个方法实际开发中不一定会用到
     */
    public void setVISIBLEtoStatusview(){
        immersionMode.setStarusViewVISIBLE();
    }
}
