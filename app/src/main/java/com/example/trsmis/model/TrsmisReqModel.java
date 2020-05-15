/*
 * 요청사항 요청모델
 */

package com.example.trsmis.model;

import lombok.Data;

@Data
public class TrsmisReqModel {

    /**
     * 날짜 구분. 완료일: cmpltDt, 입력일: firstInputDt
     * 요청사항 목록에 대해 완료일 기준으로 불러올 지, 입력일 기준으로 불러올 지 선택한다.
     * 완료일: 요청사항이 처리된 날짜
     * 입력일: 요청사항이 게시된 날짜
     */
    private String findDtDstnct;

    /**
     * 요청사항 목록 날짜 범위 시작일
     */
    private String findStrtDt;

    /**
     * 요청사항 목록 날짜 범위 종료일
     */
    private String findEndDt;

    /**
     * 어느 부서에 대한 요청사항인지 선택한다.
     * 개발 8 / 공고팀 7 / 경영지원 6
     */
    private String jobDstnctCd;

    /**
     * 현재 페이지
     */
    private String currentPage;

   /**
    * 페이지사이즈
    * 한 페이지에 불러올 아이템의 수
    */
    private String pageSize;

    public TrsmisReqModel(String findDtDstnct, String jobDstnctCd, String pageSize, String currentPage) {
        this.findDtDstnct = findDtDstnct;
        this.jobDstnctCd = jobDstnctCd;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }
}
