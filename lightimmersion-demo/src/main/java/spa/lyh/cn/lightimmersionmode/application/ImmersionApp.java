package spa.lyh.cn.lightimmersionmode.application;

import android.app.Application;

import spa.lyh.cn.lightimmersionmode.R;
import spa.lyh.cn.statusbarlightmode.ImmersionConfiguration;
import spa.lyh.cn.statusbarlightmode.ImmersionMode;

/**
 * Created by liyuhao on 2017/4/26.
 */

public class ImmersionApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        ImmersionConfiguration configuration = new ImmersionConfiguration.Builder(this)
                .enableImmersionMode(ImmersionConfiguration.ENABLE)
                .defaultColor(R.color.bar_color)
                .build();
        ImmersionMode.getInstance().init(configuration);
    }
}
