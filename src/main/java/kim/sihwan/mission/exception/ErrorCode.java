package kim.sihwan.mission.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    NOT_BLANK("E01", "상품 정보는 비어있을 수 없습니다."),
    INVALID_TYPE("E02","존재하지 않는 유형입니다."),
    FRUIT_NOT_FOUND("E03","목록에 존재하지 않는 과일입니다."),
    VEGETABLE_NOT_FOUND("E04","목록에 존재하지 않는 채소입니다."),

    API_SERVER_ERROR("E05","외부 API 서버 오류로 토큰을 발급할 수 없습니다."),
    API_COOKIE_ERROR("E06","외부 API 쿠키 오류로 토큰을 발급할 수 없습니다."),

    UNKNOWN("E07","알수없는 오류가 발생했습니다.");

    private final String code;
    private final String desc;

    ErrorCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    }
