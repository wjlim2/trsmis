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
import java.text.ParseException;
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
        onListCall(mJobCode.getValue(), null);
    }

    public void onListCall(String jobCd, Date date) {

        if (mTrsmisReqModel.getCurrentPage().equals("1") && date == null) {

            String dateToday = getDateToString(new Date()); //오늘 날짜
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            String dateBefore = getDateMonthBefore(cal, -2); //두 달 전 날짜

            mTrsmisReqModel.setFindStrtDt(dateBefore); //시작일 설정
            mTrsmisReqModel.setFindEndDt(dateToday); //종료일 설정
            mTrsmisReqModel.setJobDstnctCd(jobCd); //직무코드 설정

            mLoading.setValue(View.VISIBLE);
        } else if(date != null){
            //두 달 전 날짜

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            mTrsmisReqModel.setFindStrtDt(getDateMonthBefore(cal, -2));
            mTrsmisReqModel.setFindEndDt(getDateToString(date));
            mTrsmisReqModel.setJobDstnctCd(jobDstnctCd);
        }
        Logger.d(mTrsmisReqModel);
        mService.trsmisCall(mTrsmisReqModel).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<TrsmisResModel>>() {

                    @Override
                    public void onSubscribe(Disposable d) {}

                    @Override
                    public void onSuccess(Response<TrsmisResModel> trsmisResModelResponse) {
                        //통신을 하는 시점에는 반드시 토큰을 전달한다.
                        //만료된 토큰일 때는 서버에서 토큰을 새로 발급한다.
                        //토큰을 새로 발급받았다면 받은 토큰을 로컬에 저장한다.
                        if (trsmisResModelResponse.headers().get("new_token") != null) {
                            Hawk.put("auth-token", trsmisResModelResponse.headers().get("new_token"));
                        }
                        //통신 성공
                        if (trsmisResModelResponse.isSuccessful()) {
                            if (trsmisResModelResponse.body() != null) {
                                ArrayList<Trsmis> trsmisList = trsmisResModelResponse.body().getTrsmisList();//전달받은 데이터를 trsmisList에 저장한다.
                                int trsmisCnt = trsmisResModelResponse.body().getTrsmisCnt();//아이템의 갯수를 저장한다.
                                mTrsmisCnt.setValue(trsmisCnt);//아이템의 갯수는 최종적으로 어댑터에 전달된다.
                                Logger.d(mTrsmisReqModel.getCurrentPage());
                                //currentPage는 액티비티가 시작될때, 날짜가 변경될 때, 팀이 변경될 때 데이터리스트가 갱신되기때문에 1페이지부터 시작한다.
                                //스크롤을 내려 데이터를 불러오는 경우에는 2페이지 이상이다.
                                if (mTrsmisReqModel.getCurrentPage().equals("1")){
                                    mLoading.setValue(View.GONE);
                                    mTrsmisList.setValue(trsmisList);
                                }
                            }
                        //통신 실패
                        } else {
                            try {
                                JSONObject jsonObject = null;
                                if (trsmisResModelResponse.errorBody() != null) {
                                    jsonObject = new JSONObject(trsmisResModelResponse.errorBody().string());
                                }
                                //Access Denied 메시지를 받은 경우
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
