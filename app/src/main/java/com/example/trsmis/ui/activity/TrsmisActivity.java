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
import android.os.Bundle;
import android.view.View;

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

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.trsmis.util.AppUtil.*;

public class TrsmisActivity extends BaseActivity {

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
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_trsmis);
        mBinding.setLifecycleOwner(this);


        mViewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        mViewModel = new ViewModelProvider(this, mViewModelFactory).get(TrsmisViewModel.class);
        mViewModel.init();
        mBinding.setViewModel(mViewModel);

        mAdapter = new TrsmisAdapter();
        mBinding.trsmisRecycler.setAdapter(mAdapter);

        // 날짜 초기화
        mBinding.setDate(getDayAndDate());
        // 지정자 초기화
        mViewModel.getApor().observe(this, apor -> {
            mBinding.setTeam(apor);
        });

        setObserver();
    }

    public void setObserver() {

    }


}
