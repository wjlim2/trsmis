package com.example.trsmis.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import lombok.Data;

@Data
public class Trsmis implements Parcelable { //요청사항 모델
    /**
     * ntbdId
     */
    private String trsmisId;
    /**
     * 요청사항
     */
    private String prblmText;
    /**
     * 처리진행
     */
    private String prblmPendText;
    /**
     * 처리완료
     */
    private String dlngResltText;
    /**
     * 요청자아이디. writer ID
     */
    private String writrId;
    /**
     * 요청자명
     */
    private String writrNm;
    /**
     * 팀아이디
     */
    private String positTeamId;
    /**
     * 팀명
     */
    private String positTeamNm;
    /**
     * 고객짧은명
     */
    private String custDstnctSoNm;
    /**
     * 고객구분코드
     */
    private String custDstnctCd;
    /**
     * 처리자아이디
     */
    private String dlrId;
    /**
     * 처리자명
     */
    private String dlrNm;
    /**
     * 완료일시
     */
    private String cmpltDt;
    /**
     * 구분코드
     */
    private String prblmDstnctCd;
    /**
     * 구분명
     */
    private String prblmDstnctNm;
    /**
     * 상태코드
     */
    private String prblmDlngStatCd;
    /**
     * 상태명
     */
    private String prblmDlngStatNm;
    /**
     * 입력시간
     */
    private String firstInputDt;

    /**
     * 첨부파일 리스트 // 리스트인데 임시로
     */
    private ArrayList<TrsmisAtchmnfl> fileList;

    /**
     * 대분류코드
     */
    private String trsmisLclasCd;
    /**
     * 대분류명
     */
    private String trsmisLclasNm;
    /**
     * 소분류 아이디
     */
    private String trsmisSclasId;
    /**
     * 소분류 명
     */
    private String trsmisSclasNm;
    /**
     * 전달여뷰
     */
    private String trsmisYn;
    /**
     * 완료 알람 여부
     */
    private String cmpltAlamYn;
    /**
     * 직무 명
     */
    private String jobNm;
    /**
     * 신청일자
     */
    private String applyDe;
    /**
     * 지정자명
     */
    private String aporNm;

    private Trsmis(Parcel in) {
        trsmisId = in.readString();
        prblmText = in.readString();
        prblmPendText = in.readString();
        dlngResltText = in.readString();
        writrId = in.readString();
        writrNm = in.readString();
        positTeamId = in.readString();
        positTeamNm = in.readString();
        custDstnctSoNm = in.readString();
        custDstnctCd = in.readString();
        dlrId = in.readString();
        dlrNm = in.readString();
        cmpltDt = in.readString();
        prblmDstnctCd = in.readString();
        prblmDstnctNm = in.readString();
        prblmDlngStatCd = in.readString();
        prblmDlngStatNm = in.readString();
        firstInputDt = in.readString();
        trsmisLclasCd = in.readString();
        trsmisLclasNm = in.readString();
        trsmisSclasId = in.readString();
        trsmisSclasNm = in.readString();
        trsmisYn = in.readString();
        cmpltAlamYn = in.readString();
        jobNm = in.readString();
        applyDe = in.readString();
        aporNm = in.readString();
    }

    public static final Creator<Trsmis> CREATOR = new Creator<Trsmis>() {
        @Override
        public Trsmis createFromParcel(Parcel in) {
            return new Trsmis(in);
        }

        @Override
        public Trsmis[] newArray(int size) {
            return new Trsmis[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(trsmisId);
        dest.writeString(prblmText);
        dest.writeString(prblmPendText);
        dest.writeString(dlngResltText);
        dest.writeString(writrId);
        dest.writeString(writrNm);
        dest.writeString(positTeamId);
        dest.writeString(positTeamNm);
        dest.writeString(custDstnctSoNm);
        dest.writeString(custDstnctCd);
        dest.writeString(dlrId);
        dest.writeString(dlrNm);
        dest.writeString(cmpltDt);
        dest.writeString(prblmDstnctCd);
        dest.writeString(prblmDstnctNm);
        dest.writeString(prblmDlngStatCd);
        dest.writeString(prblmDlngStatNm);
        dest.writeString(firstInputDt);
        dest.writeString(trsmisLclasCd);
        dest.writeString(trsmisLclasNm);
        dest.writeString(trsmisSclasId);
        dest.writeString(trsmisSclasNm);
        dest.writeString(trsmisYn);
        dest.writeString(cmpltAlamYn);
        dest.writeString(jobNm);
        dest.writeString(applyDe);
        dest.writeString(aporNm);
    }
}