package study.board.exhandler.advice;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jdk.jshell.spi.ExecutionControl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import study.board.exhandler.ErrorResult;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(ExecutionControl.UserException e) {
        ErrorResult errorResult = new ErrorResult("User-Ex", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult validException(MethodArgumentNotValidException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("400", e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler
//    public ErrorResult exHandler(Exception e) {
//
//        return new ErrorResult("Ex", "내부오류");
//    }

    /**
     * UnsupportedJwtException : jwt가 예상하는 형식과 다른 형식이거나 구성
     * MalformedJwtException : 잘못된 jwt 구조
     * ExpiredJwtException : JWT의 유효기간이 초과
     * SignatureException : JWT의 서명실패(변조 데이터)
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response ServerException2(Exception e) {
        log.error("[exceptionHandler] ex", e);
        return new Response("500", "서버 에러");
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response MissingRequestHeaderException(Exception e) {
        e.printStackTrace();
        return new Response("400", "MissingRequestHeaderException");
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response UnsupportedJwtException(Exception e) {
        e.printStackTrace();
        return new Response("401", "UnsupportedJwtException");
    }

    @ExceptionHandler(MalformedJwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response MalformedJwtException(Exception e) {
        e.printStackTrace();
        return new Response("402", "MalformedJwtException");
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response ExpiredJwtException(Exception e) {
        e.printStackTrace();
        return new Response("403", "ExpiredJwtException");
    }

    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response SignatureException(Exception e) {
        e.printStackTrace();
        return new Response("404", "SignatureException");
    }

    //Response DTO
    @Data
    @AllArgsConstructor
    static class Response {
        private String code;
        private String msg;
    }


}
