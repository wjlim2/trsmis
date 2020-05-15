package com.example.trsmis.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.trsmis.R;
import com.example.trsmis.base.BaseActivity;
import com.example.trsmis.base.BaseApplication;
import com.example.trsmis.databinding.ActivityTrsmisBinding;
import com.example.trsmis.model.Trsmis;
import com.example.trsmis.ui.adapter.TrsmisAdapter;
import com.example.trsmis.ui.dialog.TeamSelectDialogFragment;
import com.example.trsmis.ui.listener.TrsmisItemClickListener;
import com.example.trsmis.ui.viewmodel.TrsmisViewModel;
import com.example.trsmis.util.AppUtil;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;

import static com.example.trsmis.util.AppUtil.*;

public class TrsmisActivity extends BaseActivity implements View.OnClickListener{

    private String mJobCdNm;
    private String mJobCd;
    private ViewModelProvider.AndroidViewModelFactory mViewModelFactory;
    private ViewModelStore mViewModelStore = new ViewModelStore();
    private TrsmisViewModel mViewModel;
    private ActivityTrsmisBinding mBinding;
    private TrsmisAdapter mAdapter;
    private DatePickerDialog mDatePickerDialog;
    private SimpleDateFormat mDateFormat;
    private String mNowDate;
    private TeamSelectDialogFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        //데이터 바인딩
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_trsmis);
        mBinding.setLifecycleOwner(this);

        //뷰모델 생성
        mViewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        mViewModel = new ViewModelProvider(this, mViewModelFactory).get(TrsmisViewModel.class);
        mViewModel.init();
        mBinding.setViewModel(mViewModel);

        //리사이클러뷰 어댑터 연결
        mAdapter = new TrsmisAdapter();
        mBinding.trsmisRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.trsmisRecycler.setAdapter(mAdapter);

        // 날짜 초기화
        mBinding.setDate(getDayAndDate());
        // 지정자 초기화
        mViewModel.getApor().observe(this, apor -> {
            mBinding.setTeam(apor);
        });
        Calendar calendar = new GregorianCalendar(Locale.KOREA);
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        mNowDate = mDateFormat.format(calendar.getTime());

        setObserver();
    }



    public void setObserver() {
        //데이터를 갱신하는 경우 기존 데이터리스트를 지우고 받아온 데이터리스트를 적용한다.
        mViewModel.getTrsmis().observe(this, trsmis -> {
            if (mViewModel.getTrsmisReqModel().getCurrentPage().equals("1")) {
                mAdapter.removeAllItem();
            }
            mAdapter.addItem(trsmis);
            if (mViewModel.getTrsmisReqModel().getCurrentPage().equals("1")) {
                if (mAdapter.getItemCount() == 0) {
                    mBinding.emptyText.setVisibility(View.VISIBLE);
                    mBinding.trsmisTagLayout.setVisibility(View.GONE);
                    mBinding.trsmisTagBottomLine.setVisibility(View.GONE);
                } else {
                    mBinding.emptyText.setVisibility(View.GONE);
                    mBinding.trsmisTagLayout.setVisibility(View.VISIBLE);
                    mBinding.trsmisTagBottomLine.setVisibility(View.VISIBLE);
                }
            }
        });
        mViewModel.getTrsmisCnt().observe(this, count -> {
            mAdapter.setCount(count);
        });
    }

    public void onDatePickClicked() {
        if (mDatePickerDialog != null && mDatePickerDialog.isShowing()) {
            mDatePickerDialog.dismiss();
        }
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(mDateFormat.parse(mNowDate));
            Logger.d(calendar);
            mDatePickerDialog = new DatePickerDialog(TrsmisActivity.this, (datePicker, i, i1, i2) -> {
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d", Locale.KOREA);
                    Logger.d(dateFormat);
                    Date date = dateFormat.parse(i + "-" + (i1 + 1) + "-" + i2);
                    Logger.d(date);
                    if (date != null) {
                        mNowDate = mDateFormat.format(date);
                    }
                    Logger.d(mNowDate);
                    String displayDate = mNowDate + getDateDay(mNowDate, "yyyy-MM-dd");
                    Logger.d(displayDate);
                    mBinding.trsmisDateText.setText(displayDate);
//                    onRecyclerViewScrollListener();
                    mViewModel.onListCall(null, date); // 리스트콜
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }, calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH)), calendar.get(Calendar.DAY_OF_MONTH));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mDatePickerDialog.setCanceledOnTouchOutside(false);
        mDatePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        mDatePickerDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.trsmis_back_image:
                onBackPressed();
                break;
            case R.id.trsmis_date_layout:
                onDatePickClicked();
                break;
//            case R.id.trsmis_left_image:
//                onDatePickClicked("left");
//                break;
//            case R.id.trsmis_right_image:
//                onDatePickClicked("right");
//                break;
//            case R.id.trsmis_teamSelect_text:
//                onTeamPickClicked();
//                break;
//            case R.id.trsmis_write_image:
//                Intent intent = new Intent(this, TrsmisWriteActivity.class);
//                this.mJobCd = mViewModel.onChangeJobCd(mJobCdNm);
//                intent.putExtra("jobCd", mJobCd);
//                startActivity(intent);
//                break;

        }
    }
}
