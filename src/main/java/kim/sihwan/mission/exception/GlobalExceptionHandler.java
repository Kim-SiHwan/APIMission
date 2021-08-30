package kim.sihwan.mission.exception;

import kim.sihwan.mission.exception.customException.*;
import kim.sihwan.mission.exception.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponseDto> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info("MethodArgumentNotValidException \n 비어있는 타입 혹은 이름 입력 \n {}", e.getMessage());
        return new ResponseEntity<>(errorResponseDto(ErrorCode.NOT_BLANK.getCode(), ErrorCode.NOT_BLANK.getDesc()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponseDto> bindException(BindException e) {
        log.info("BindException : 비어있는 타입 혹은 이름 입력 : {}", e.getMessage());
        return new ResponseEntity<>(errorResponseDto(ErrorCode.NOT_BLANK.getCode(), ErrorCode.NOT_BLANK.getDesc()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidProductTypeException.class)
    protected ResponseEntity<ErrorResponseDto> invalidProductTypeException(InvalidProductTypeException e) {
        log.info("InvalidProductTypeException : 잘못된 타입을 입력 : {}", e.getMessage());
        return new ResponseEntity<>(errorResponseDto(ErrorCode.INVALID_TYPE.getCode(), ErrorCode.INVALID_TYPE.getDesc()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> productNotFoundException(ProductNotFoundException e) {
        log.info("ProductNotFoundException : 존재하지 않는 품목을 입력 : {}", e.getMessage());
        return new ResponseEntity<>(errorResponseDto(ErrorCode.PRODUCT_NOT_FOUND.getCode(), ErrorCode.PRODUCT_NOT_FOUND.getDesc()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiServerException.class)
    protected ResponseEntity<ErrorResponseDto> apiServerException(ApiServerException e) {
        log.info("ApiServerException : 외부 API 서버 문제 발생 : {}", e.getMessage());
        return new ResponseEntity<>(errorResponseDto(ErrorCode.API_SERVER_ERROR.getCode(), ErrorCode.API_SERVER_ERROR.getDesc()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ApiCookieException.class)
    protected ResponseEntity<ErrorResponseDto> apiCookieException(ApiCookieException e) {
        log.info("ApiCookieException : 외부 API 서버 문제 발생 :{}", e.getMessage());
        return new ResponseEntity<>(errorResponseDto(ErrorCode.API_COOKIE_ERROR.getCode(), ErrorCode.API_COOKIE_ERROR.getDesc()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnknownServerException.class)
    protected ResponseEntity<ErrorResponseDto> unknownServerException(UnknownServerException e) {
        log.info("UnknownServerException : 아직 확인하지 못한 오류 발생 : {}", e.getMessage());
        return new ResponseEntity<>(errorResponseDto(ErrorCode.UNKNOWN.getCode(), ErrorCode.UNKNOWN.getDesc()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorResponseDto errorResponseDto(final String code,final String message) {
        return new ErrorResponseDto(false, code, message);

    }
}
