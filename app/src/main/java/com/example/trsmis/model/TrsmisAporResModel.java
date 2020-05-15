package com.example.trsmis.model;

import java.util.ArrayList;

import lombok.Data;

@Data
public class TrsmisAporResModel {
    /**
     * 상태
     */
    private String status;
    /**
     * 사업부 통계 리스트
     */
    private ArrayList<TrsmisApor> trsmisAporList;

}
