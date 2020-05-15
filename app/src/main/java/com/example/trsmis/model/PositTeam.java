package com.example.trsmis.model;

import lombok.Data;

@Data
public class PositTeam {

    private Long id;

    private String custDstnctCd;

    private String bizDstnctCdNm;

    private String bizDstnctCd;

    private String teamNm;

    private String teamSoNm;

    private String custDstnctCdSoNm;
    /** 앱 배경색*/
    private String appBackgroundColor;

    /** 앱 사업부 색*/
    private String appDeepColor;

    private boolean isSelected;

    private String jobCd;


}
