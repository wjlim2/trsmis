package com.example.trsmis.ui.viewmodel;

import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.example.trsmis.base.BaseViewModel;
import com.example.trsmis.model.Trsmis;
import com.example.trsmis.model.TrsmisReqModel;
import com.example.trsmis.model.TrsmisResModel;
import com.example.trsmis.network.RetrofitClient;
import com.example.trsmis.network.RetrofitService;
import static com.example.trsmis.util.AppUtil.*;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


public class TrsmisViewModel extends BaseViewModel {

    private MutableLiveData<String> mJobCode = new MutableLiveData<>();
    private MutableLiveData<Integer> mLoading = new MutableLiveData<>();
    private MutableLiveData<String> mAction = new MutableLiveData<>();
    private MutableLiveData<Integer> mTrsmisCnt = new MutableLiveData<>();
    private MutableLiveData<String> mError = new MutableLiveData<>();
    private MutableLiveData<String> mApor = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Trsmis>> mTrsmisList = new MutableLiveData<>();
    private RetrofitService mService;
    private String findDtDstnct, jobDstnctCd, pageSize, currentPage;
    private TrsmisReqModel mTrsmisReqModel;

    public void init() {
        findDtDstnct = "firstInputDt";
        jobDstnctCd = "";
        pageSize = "30";
        currentPage = "1";
        mTrsmisReqModel = new TrsmisReqModel(findDtDstnct, jobDstnctCd, pageSize, currentPage);
        mService = RetrofitClient.getClient().create(RetrofitService.class);

        mJobCode.setValue(testEmp().getJobCd()); //사용자의 직무코드
        mApor.setValue(apor(testEmp().getJobCd())); //지정자
        Logger.d(mJobCode.getValue());
        onListCall(mJobCode.getValue());
    }

    public void onListCall(String jobCd) {

        if (mTrsmisReqModel.getCurrentPage().equals("1")) {

            String dateToday = getDateToday(new Date()); //오늘 날짜
            String dateBefore = getDateMonthBefore(-2); //두 달 전 날짜

            mTrsmisReqModel.setFindStrtDt(dateBefore); //시작일 설정
            mTrsmisReqModel.setFindEndDt(dateToday); //종료일 설정
            mTrsmisReqModel.setJobDstnctCd(jobCd); //직무코드 설정


            mLoading.setValue(View.VISIBLE);
        } else {
            Logger.d("Current Page Is Not 1");
        }
        Logger.d(mTrsmisReqModel);
        mService.trsmisCall(mTrsmisReqModel).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<TrsmisResModel>>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<TrsmisResModel> trsmisResModelResponse) {
                        if (trsmisResModelResponse.headers().get("new_token") != null) {
                            Hawk.put("auth-token", trsmisResModelResponse.headers().get("new_token"));
                        }
                        if (trsmisResModelResponse.isSuccessful()) {
                            Logger.d("ResponseBody = " + trsmisResModelResponse.body());
                            if (trsmisResModelResponse.body() != null) {
                                ArrayList<Trsmis> trsmisList = trsmisResModelResponse.body().getTrsmisList();
                                int trsmisCnt = trsmisResModelResponse.body().getTrsmisCnt();
                                mTrsmisCnt.setValue(trsmisCnt);
                                Logger.d(mTrsmisReqModel.getCurrentPage());
                                if (mTrsmisReqModel.getCurrentPage().equals("1")){
                                    mLoading.setValue(View.GONE);
                                    mTrsmisList.setValue(trsmisList);
                                }

                            }
                        } else {
                            Logger.d(trsmisResModelResponse.errorBody());
                            try {
                                JSONObject jsonObject = null;
                                if (trsmisResModelResponse.errorBody() != null) {
                                    jsonObject = new JSONObject(trsmisResModelResponse.errorBody().string());
                                }
                                if (jsonObject != null && jsonObject.getString("message") != null) {
                                    if (jsonObject.getString("message").equals("Access Denied")) {
                                        mError.setValue("LOGIN");
                                        if (mTrsmisReqModel.getCurrentPage().equals("1")){
                                            mLoading.setValue(View.GONE);
                                        }

                                    }
                                }
                            } catch (JSONException | IOException e) {
                                mError.setValue("SERVER");
                                if (mTrsmisReqModel.getCurrentPage().equals("1")){
                                    mLoading.setValue(View.GONE);
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("RetrofitError: " + e);
                        mError.setValue("SERVER");
                        if (mTrsmisReqModel.getCurrentPage().equals("1")){
                            mLoading.setValue(View.GONE);
                        }
                    }
                });
    }

    private String apor(String jobCd) {

        switch (jobCd) {
            //경영지원팀
            case "4":
            case "6":
            case "12":
                return "경영지원팀";
            //공고팀
            case "7":
                return "공고팀";
            //개발팀
            default:
                return"개발팀";
        }
    }



    public TrsmisReqModel getTrsmisReqModel() {
        return mTrsmisReqModel;
    }

    public MutableLiveData<String> getError() {
        return mError;
    }

    public MutableLiveData<String> getJobCode() {
        return mJobCode;
    }

    public MutableLiveData<Integer> getTrsmisCnt() {
        return mTrsmisCnt;
    }

    public MutableLiveData<ArrayList<Trsmis>> getTrsmis() {
        return mTrsmisList;
    }

    public MutableLiveData<String> getAction() {
        return mAction;
    }

    public MutableLiveData<String> getApor() {
        return mApor;
    }

    public MutableLiveData<Integer> isLoading() {
        return mLoading;
    }

}
