package com.restfullec.restfulwebservice.exception;

import com.restfullec.restfulwebservice.user.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/*
*  < 부가 설명 >
*  - @ExceptionHandler : @Controller, @RestController가 적용된 Bean내에서 발생하는 예외를 잡아서 하나의 메서드에서 처리해주는 기능
*  - @ExceptionHandler({ Exception1.class, Exception2.class}) 이런식으로 두 개 이상 등록도 가능
*   --- 그외 참고 : https://jeong-pro.tistory.com/195
*
*  - @RestControllerAdvice
*       : @ControllerAdvice와 같음. 다만 @ResponseBody가 추가되어 객체를 리턴할 수 있음.
* */


@RestController
@ControllerAdvice // 모든 @RestController, @controller가 실행될 때 사전에 실행이 됨. 에러가 발생하면 이 메서드가 호출 되게 함
public class CustomizedResponseEntityExceptionHandler  extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class) // 익셉션이 실행될때 해당 메서드가 실행되게.
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));  /*  <결과 예시>
                                                                                                        * { timestamp": "2020-07-15T14:24:01.971+0000"
                                                                                                        *  , "message": "ID[100] not found"
                                                                                                        *  , "details": "uri=/users/100" }
                                                                                                        */

        return new ResponseEntity<Object>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<Object>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Validation Fail", ex.getBindingResult().toString());
        return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
