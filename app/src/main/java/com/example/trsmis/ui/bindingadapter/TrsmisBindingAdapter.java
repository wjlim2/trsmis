package com.example.trsmis.ui.bindingadapter;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class TrsmisBindingAdapter {

    @BindingAdapter("trsmisLclasNm")
    public static void setTrsmisLclasNm(TextView textView, String date){
        textView.setText(date);
    }
}
