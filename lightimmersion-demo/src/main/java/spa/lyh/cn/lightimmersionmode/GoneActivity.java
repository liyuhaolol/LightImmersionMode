package spa.lyh.cn.lightimmersionmode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import spa.lyh.cn.lightimmersionmode.base.BaseActivity;

/**
 * Created by liyuhao on 2017/5/7.
 */

public class GoneActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gone);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.Bgone:
                setGONEtoStatusview();
                break;
            case R.id.Bvisible:
                setVISIBLEtoStatusview();
                break;
        }
    }
}
