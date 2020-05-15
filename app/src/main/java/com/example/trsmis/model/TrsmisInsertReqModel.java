package com.example.trsmis.model;

import lombok.Data;

@Data
public class TrsmisInsertReqModel {
    /** 지정자 아이디 Long */
    private Long aporId;
    /** 전달 내용 */
    private String prblmText;
    /** 직무 */
    private String jobDstnctCd;

    public TrsmisInsertReqModel(Long aporId, String prblmText, String jobDstnctCd) {
        this.aporId = aporId;
        this.prblmText = prblmText;
        this.jobDstnctCd = jobDstnctCd;
    }
}
