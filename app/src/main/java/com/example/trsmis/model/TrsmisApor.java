
/*
 * 지정자 모델 클래스.
 * 작성자가 '요청을 처리해줄 직원'을 선택한다.
 * 지정받는 직원의 사원명, 사원아이디, 사원직무코드를 담는다.
 */

package com.example.trsmis.model;

import lombok.Data;

@Data
public class TrsmisApor {
    /**
     * 사원명
     */
    private String empNm;
    /**
     * 사원 아이디
     */
    private String empId;
    /**
     * 사원 직무코드
     */
    private String jobCd;


}
