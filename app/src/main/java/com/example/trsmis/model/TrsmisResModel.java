package com.example.trsmis.model;

import java.util.ArrayList;

import lombok.Data;

@Data
public class TrsmisResModel { //요청사항 서버요청결과 모델
    /** 상태 */
   private String status;
   /** 카운트 */
   private int trsmisCnt;
    /** 리스트 목록 */
   private ArrayList<Trsmis> trsmisList;

}
