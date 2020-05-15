package com.example.trsmis.ui.adapter.viewholder;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trsmis.R;
import com.example.trsmis.databinding.ItemTrsmisBinding;
import com.example.trsmis.model.PositTeam;
import com.example.trsmis.model.Trsmis;
import com.example.trsmis.ui.listener.TrsmisItemClickListener;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

public class TrsmisViewHolder extends RecyclerView.ViewHolder {
    private ItemTrsmisBinding mBinding;

    public TrsmisViewHolder(@NonNull ItemTrsmisBinding binding) {
        super(binding.getRoot());
        this.mBinding = binding;
    }

    public void onBindView(Trsmis model, int position, TrsmisItemClickListener listener) {
        String trsmisPend;
        String trsmisPendTitle;
        if (model.getTrsmisLclasNm() == null) { //대분류
            model.setTrsmisLclasNm("미지정");
        }
        if (model.getTrsmisSclasNm() == null) { //소분류
            model.setTrsmisSclasNm("미지정");
        }
        if (model.getDlngResltText() == null) { //결과
            model.setDlngResltText("");
        }
        if (model.getPrblmDlngStatNm() == null || model.getPrblmDlngStatNm().equals("미확인")) { //진행 및 결과
            trsmisPendTitle = "";
        } else {
            trsmisPendTitle = "[" + model.getPrblmDlngStatNm() + "] ";
        }
        if (model.getDlngResltText().isEmpty()) {
            if (model.getPrblmPendText() != null) {
                trsmisPend = model.getPrblmPendText();
            } else {
                trsmisPend = "";
            }
        } else {
            trsmisPend = model.getDlngResltText();
        }
        String trsmisPrblmTitle = "[" + model.getTrsmisLclasNm() + ">" + model.getTrsmisSclasNm() + "]";
        String trsmisPrblm = model.getPrblmText();
        setTeamColor(model.getCustDstnctCd());
        mBinding.trsmisPrblmText.setText(trsmisPrblm);
        mBinding.trsmisPrblmTitleText.setText(trsmisPrblmTitle);
        mBinding.trsmisProblmPendTitleText.setText(trsmisPendTitle);
        mBinding.trsmisProblmPendText.setText(trsmisPend);
        setDefault();
        if (model.getPrblmDlngStatNm().equals("확인")) {
            mBinding.trsmisPrblmDlngStatNmText.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color87b868));
            mBinding.trsmisPrblmDlngStatNmText.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(itemView.getContext(), R.color.color87b868)));
        } else if (model.getPrblmDlngStatNm().equals("미확인")) {
            mBinding.trsmisPrblmDlngStatNmText.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colore23a3a));
            mBinding.trsmisPrblmDlngStatNmText.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(itemView.getContext(), R.color.colore23a3a)));
        }else if (model.getPrblmDlngStatNm().equals("완료")) {
            mBinding.trsmisPrblmDlngStatNmText.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color689ec1));
            mBinding.trsmisPrblmDlngStatNmText.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(itemView.getContext(), R.color.color689ec1)));
        }else if (model.getPrblmDlngStatNm().equals("취소")) {
            setCancel();
            mBinding.trsmisPrblmDlngStatNmText.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(itemView.getContext(), R.color.coloraaaaaa)));
            mBinding.trsmisPrblmDlngStatNmText.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.coloraaaaaa));
        }else if (model.getPrblmDlngStatNm().equals("미해결")) {
            mBinding.trsmisPrblmDlngStatNmText.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.coloraa58be));
            mBinding.trsmisPrblmDlngStatNmText.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(itemView.getContext(), R.color.coloraa58be)));
        }else {
            mBinding.trsmisPrblmDlngStatNmText.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorPrimary));
            mBinding.trsmisPrblmDlngStatNmText.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(itemView.getContext(), R.color.colorPrimary)));
        }

        mBinding.setModel(model);
        mBinding.setPosition(position);
        mBinding.setListener(listener);
    }

    private void setTeamColor(String custDstnctCd) {
        ArrayList<PositTeam> list = Hawk.get("TEAM_LIST", new ArrayList<>());
        int custDstnctcdColor = ContextCompat.getColor(itemView.getContext(), R.color.colorPrimary);
        if (list.size() == 0) {
            mBinding.trsmisCustdstnctCardView.setCardBackgroundColor(custDstnctcdColor);
            return;
        }
        for (PositTeam positTeam : list) {
            if (positTeam.getAppDeepColor() != null && positTeam.getCustDstnctCd().equals(custDstnctCd)) {
                custDstnctcdColor = Color.parseColor(positTeam.getAppDeepColor());
                break;
            }
        }
        mBinding.trsmisCustdstnctCardView.setCardBackgroundColor(custDstnctcdColor);
    }

    private void setCancel(){
        mBinding.trsmisCustdstnctCardView.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.coloraaaaaa));
        mBinding.trsmisPositTeamIdText.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.coloraaaaaa));
        mBinding.trsmisProblmPendTitleText.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.coloraaaaaa));
        mBinding.trsmisProblmPendText.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.coloraaaaaa));
        mBinding.trsmisPrblmTitleText.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.coloraaaaaa));
        mBinding.trsmisPrblmText.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.coloraaaaaa));

        mBinding.trsmisPrblmText.setPaintFlags(mBinding.trsmisPrblmText.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        mBinding.trsmisPrblmTitleText.setPaintFlags(mBinding.trsmisProblmPendText.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        mBinding.trsmisProblmPendTitleText.setPaintFlags(mBinding.trsmisProblmPendTitleText.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        mBinding.trsmisProblmPendText.setPaintFlags(mBinding.trsmisProblmPendText.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
    }

    private void setDefault(){
        mBinding.trsmisPositTeamIdText.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color333333));
        mBinding.trsmisProblmPendTitleText.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color333333));
        mBinding.trsmisPrblmTitleText.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color333333));
        mBinding.trsmisProblmPendText.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color888888));
        mBinding.trsmisPrblmText.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color888888));

        mBinding.trsmisPrblmText.setPaintFlags(mBinding.trsmisPrblmText.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        mBinding.trsmisPrblmTitleText.setPaintFlags(mBinding.trsmisPrblmTitleText.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        mBinding.trsmisProblmPendTitleText.setPaintFlags(mBinding.trsmisProblmPendTitleText.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        mBinding.trsmisProblmPendText.setPaintFlags(mBinding.trsmisProblmPendText.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
    }
}
