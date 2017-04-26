package spa.lyh.cn.lightimmersionmode.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
        System.out.println("==fragment1-onCreateView==");
        return mContentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        System.out.println("==fragment1-onAttach==");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("==fragment1-onCreate==");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("==fragment1-onActivityCreated==");
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("==fragment1-onStart==");
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("==fragment1-onResume==");
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("==fragment1-onPause==");
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("==fragment1-onStop==");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("==fragment1-onDestroyView==");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("==fragment1-onDestroy==");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        System.out.println("==fragment1-onDetach==");
    }
}
