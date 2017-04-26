package spa.lyh.cn.lightimmersionmode.base;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;

import spa.lyh.cn.statusbarlightmode.ImmersionMode;
import spa.lyh.cn.statusbarlightmode.TemporaryConfig;

/**
 * Created by liyuhao on 2017/4/26.
 */

public class BaseActivity extends AppCompatActivity{
    public ImmersionMode immersionMode;

    @Override
    protected void onResume() {
        super.onResume();
        immersionMode = ImmersionMode.getInstance();
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
}
