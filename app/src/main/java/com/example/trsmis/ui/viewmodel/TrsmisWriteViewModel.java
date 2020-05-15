package com.example.trsmis.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.example.trsmis.model.TrsmisApor;
import com.example.trsmis.network.RetrofitService;

import java.util.ArrayList;

public class TrsmisWriteViewModel {

    private MutableLiveData<String> mToastMessage;
    private MutableLiveData<String> mAction;
    private RetrofitService mService;
    private MutableLiveData<ArrayList<TrsmisApor>> mTrsBizStatusList;
    private String jobCd_Selected;
    private MutableLiveData<String> mError;
    private MutableLiveData<Integer> mLoading;

    public MutableLiveData<String> getToastMessage() {
        return mToastMessage;
    }

    public MutableLiveData<String> getAction() {
        return mAction;
    }

    public RetrofitService getService() {
        return mService;
    }

    public MutableLiveData<Integer> getLoading() {
        return mLoading;
    }

    public MutableLiveData<String> getError() {
        return mError;
    }

    public MutableLiveData<ArrayList<TrsmisApor>> getTrsBizStatusList() {
        return mTrsBizStatusList;
    }
}
