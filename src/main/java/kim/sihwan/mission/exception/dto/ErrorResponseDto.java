package kim.sihwan.mission.exception.dto;


import lombok.Getter;

@Getter
public class ErrorResponseDto {
    private final boolean success;
    private final String message;
    private final String code;


    public ErrorResponseDto(final boolean success,final String code,final String message) {
        this.success = success;
        this.code = code;
        this.message = message;

    }
}