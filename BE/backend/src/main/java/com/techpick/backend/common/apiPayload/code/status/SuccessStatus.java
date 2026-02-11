package com.techpick.backend.common.apiPayload.code.status;

import com.techpick.backend.common.apiPayload.code.BaseCode;
import com.techpick.backend.common.apiPayload.code.ErrorReasonDto;
import com.techpick.backend.common.apiPayload.code.ReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

    // 일반적인 응답
    _OK(HttpStatus.OK, "COMMON200", "성공입니다."),

    // 사용자 관련 응답
    USER_INFO_SUCCESS(HttpStatus.OK, "USER2001", "사용자 정보 조회 성공입니다."),

    // 아티클 관련 응답
    ARTICLE_DETAIL_SUCCESS(HttpStatus.OK, "ARTICLE2001", "아티클 상세 조회 성공입니다."),
    ARTICLE_LIST_SUCCESS(HttpStatus.OK, "ARTICLE2002", "아티클 전체 조회 성공입니다."),

    // 북마크 관련 응답
    BOOKMARK_ADDED_SUCCESS(HttpStatus.OK, "BOOKMARK2001", "북마크 추가 성공입니다."),
    BOOKMARK_DELETED_SUCCESS(HttpStatus.OK, "BOOKMARK2002", "북마크 삭제 성공입니다.");
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDto getReason() {
        return ReasonDto.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .build();
    }

    @Override
    public ReasonDto getReasonHttpStatus() {
        return ReasonDto.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}
