package com.example.trsmis.model;

import lombok.Data;

@Data
public class Emp {

    /** ID */
    private Long id;

    /** 휴대전화번호 */
    private String cryalTelno;

    /** 사원비밀번호 */
    private String empPw;

    /** 사원 새비밀번호 */
    private String empPwNew;

    /** 사업부명 */
    private String bizDstnctNm;

    /** 사업부코드 */
    private String bizDstnctCd;

    /** 사이트 */
    private String custDstnctNm;

    /** 사이트 */
    private String custDstnctCd;

    /** 소속팀ID */
    private Long positTeamId;

    /** 소속팀 */
    private PositTeam positTeam;

    /** 소속팀명 */
    private String teamNm;

    /** 팀전화번호 */
    private String teamTelno;

    /** 팀배경색 */
    private String backgroundColor;

    /** 팀글자색 */
    private String fontColor;

    /** 최상위권한적용여부 */
    private String mhrAuthApYn;

    /** 사업부총괄권한여부 */
    private String bizSummAuthYn;

    /** 팀총괄권한여부 */
    private String teamSummAuthYn;

    /** 팀장권한여부 */
    private String tmheadAuthYn;

    /** 직급코드 */
    private String clsposCd;

    /** 직급코드명 */
    private String clsposCdNm;

    /** 직급코드명 */
    private int clsposSortOrdr;

    /** 업무코드 */
    private String jobCd;

    /** 업무코드명 */
    private String jobCdNm;

    /** 사원명 */
    private String empNm;

    /** 생년월일 */
    private String birdt;

    /** 남녀구분 */
    private String mnwmDstnct;

    /**
     * 사원주민번호(회계 및 재무 사용)
     */
    private String empSsn;

    /** 시작메뉴ID */
    private String strtMenuId;

    /** 시작메뉴ID */
    private String strtMenuLink;

    /** 사원주소 */
    private String empAddr;

    /** 사원이메일 */
    private String empEmail;

    /** 출입카드번호 */
    private String cmgCardNo;

    /** 하이웍스메신저아이디 */
    private String hiwkMsnrId;

    /** 네이버아이디 */
    private String naverId;

    /** 네이버카페가입여부 */
    private String naverCafeInsrdYn;

    /** 사원표시여부 */
    private String empIndictYn;

    /** 최초입사일자 */
    private String firstEntcomDe;

    /** 입사일자 */
    private String entcomDe;

    /** 퇴사일자 */
    private String retireDe;

    /** 퇴사여부 */
    private String retireYn;

    /** 퇴사사유 */
    private String retireRsn;

    /** 계좌번호 */
    private String acnutNo;

    /** 계좌사용은행명 */
    private String acnutUseBankNm;

    /** 비상연락전화번호 */
    private String emgncContTelno;

    /** 내선전화번호 */
    private String lxtnTelno;

    /** 연차 */
    private Long yrvacat;

    /** 기타휴무 */
    private Long etcOffday;

    /** 속보X */
    private Long qancXaxs;

    /** 속보Y */
    private Long qancYaxs;

    /** 최초입력일시 */
    private String firstInputDt;

    /** 최초입력자 */
    private String firstInptps;

    /** 최종수정일시 */
    private String lastUpdtDt;

    /** 최종수정자 */
    private String lastAmndr;

    /** 사용여부 */
    private String useYn;

    /** 사원ID */
    private Long empId;

    /** 사원IP */
    private String empIp;

    /** 사원MAC주소 */
    private String empMacadrs;

    /** 승인여부 */
    private String apvlYn;

    /** 출근체크여부 */
    private String attendYn;

    /** 검증 ID */
    private Long prfId;

    /** 시작페이지 */
    private String startPage;

    /** 시작페이지 url */
    private String menuLnk;


}
