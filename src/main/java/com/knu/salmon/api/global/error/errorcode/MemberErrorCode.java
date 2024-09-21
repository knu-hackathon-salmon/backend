package com.knu.salmon.api.global.error.errorcode;

import com.knu.salmon.api.global.error.ErrorCodeProvider;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MemberErrorCode implements ErrorCodeProvider {

    NO_EXIST_MEMBER_ID_EXCEPTION(HttpStatus.NOT_FOUND, "해당 Member Id에 해당하는 Member가 없습니다"),
    No_EXIST_EMAIL_MEMBER_EXCEPTION(HttpStatus.NOT_FOUND, "email에 해당하는 member가 없습니다"),
    NO_EXIST_PROFILE_NAME_MEMBER_EXCEPTION(HttpStatus.NOT_FOUND, "profileName에 해당하는 member가 없습니다"),
    CONFLICT_PROFILE_NAME_MEMBER_EXCEPTION(HttpStatus.CONFLICT, "이미 존재하는 profileName 입니다"),
    NO_MATCHING_MEMBER_EXCEPTION(HttpStatus.BAD_REQUEST, "해당 다이어리에 소속된 멤버가 아닙니다. 다이어리를 조회 할 권한이 없습니다"),
    ALREADY_JOIN_MEMBER_EXCEPTION(HttpStatus.BAD_REQUEST, "이미 회원가입을 완료 한 회원입니다"),
    NO_OWNER_EXCEPTION(HttpStatus.BAD_REQUEST, "해당 다이어리의 Owner가 아닙니다."),
    CAN_NOT_PUSH_WISH_EXCEPTION(HttpStatus.BAD_REQUEST, "업체는 좋아요를 누를 수 없습니다"),
    CAN_NOT_ROAD_ALL_WISH_EXCEPTION(HttpStatus.BAD_REQUEST, "업체는 내 찜 목록을 가져올 수 없습니다"),
    CAN_NOT_ROAD_ALL_FOODS_EXCEPTION(HttpStatus.BAD_REQUEST, "손님은 음식 판매 목록을 가져올 수 없습니다"),
    CAN_NOT_POST_FOOD_EXCEPTION(HttpStatus.BAD_REQUEST, "손님은 음식을 등록 할 수 없습니다");


    MemberErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    private HttpStatus httpStatus;
    private String message;

}
