package com.androidwind.androidquick.demo.features.architecture.mvvm.activity;

import android.arch.lifecycle.MutableLiveData;

import com.androidwind.androidquick.demo.bean.NameBean;
import com.androidwind.androidquick.demo.features.module.network.retrofit.TestApis;
import com.androidwind.androidquick.module.retrofit.RetrofitManager;
import com.androidwind.androidquick.module.retrofit.exeception.ApiException;
import com.androidwind.androidquick.module.rxjava.BaseObserver;
import com.androidwind.androidquick.util.RxUtil;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class MVVMRepository2 {

    private LifecycleProvider<ActivityEvent> lifecycleProvider;

    private MutableLiveData<List<NameBean>> testLiveData = new MutableLiveData<>();

    public MVVMRepository2(LifecycleProvider<ActivityEvent> activityEventLifecycleProvider) {
        lifecycleProvider = activityEventLifecycleProvider;
    }

    public MutableLiveData<List<NameBean>> getTestData() {

        RetrofitManager.getInstance().createApi(TestApis.class)
                .getTestData()
                .compose(RxUtil.applySchedulers())
                .compose(lifecycleProvider.bindToLifecycle())
                .subscribe(new BaseObserver<List<NameBean>>() {

                               @Override
                               public void onError(ApiException exception) {

                               }

                               @Override
                               public void onSuccess(List<NameBean> testBeans) {
                                   testLiveData.setValue(testBeans);
                               }
                           }
                );
        return testLiveData;
    }
}