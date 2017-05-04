package spa.lyh.cn.lightimmersionmode;

import android.os.Bundle;
import android.support.annotation.Nullable;

import spa.lyh.cn.lightimmersionmode.base.BaseActivity;
import spa.lyh.cn.statusbarlightmode.ImmersionConfiguration;
import spa.lyh.cn.statusbarlightmode.TemporaryConfig;

/**
 * Created by liyuhao on 2017/5/4.
 */

public class DisableActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disablemode);
        /**
         * config must be set before function:'immersionMode.execImmersionMode(this)',
         * therefore, config should init in onCreate(), function exec in onResume()
         */
        TemporaryConfig config = new TemporaryConfig(this);
        config.setEnable(ImmersionConfiguration.DISABLE);
        immersionMode.setTemporaryConfig(config);
    }
}