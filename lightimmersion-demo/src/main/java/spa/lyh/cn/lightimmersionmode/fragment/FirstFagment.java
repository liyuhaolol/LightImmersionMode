package spa.lyh.cn.lightimmersionmode.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import spa.lyh.cn.lightimmersionmode.R;
import spa.lyh.cn.lightimmersionmode.fragment.base.BaseFragment;

/**
 * Created by liyuhao on 2017/4/26.
 */

public class FirstFagment extends BaseFragment{
    private View mContentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_first,container,false);
        return mContentView;
    }
}
