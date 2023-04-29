package com.yellow.test.exception.handler;

import com.yellow.test.exception.CommonErrorDetail;
import com.yellow.test.exception.ServiceRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.Date;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ServiceRuntimeException.class)
    public ResponseEntity handleServiceException(ServiceRuntimeException exception, HttpServletRequest request) {
        CommonErrorDetail commonErrorDetail = prepareBuilder(exception, request)
                .status(exception.getStatus())
                .build();
        return new ResponseEntity<>(commonErrorDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity handleServiceException(AccessDeniedException exception, HttpServletRequest request) {
        CommonErrorDetail commonErrorDetail = prepareBuilder(exception, request)
                .status(HttpStatus.FORBIDDEN.value())
                .title("Access denied")
                .build();
        return new ResponseEntity<>(commonErrorDetail, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, IllegalArgumentException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity handleValidationException(Exception exception, HttpServletRequest request) {
        CommonErrorDetail commonErrorDetail = prepareBuilder(exception, request)
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Validation error")
                .build();
        return new ResponseEntity<>(commonErrorDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleServiceException(ConstraintViolationException exception, HttpServletRequest request) {
        CommonErrorDetail commonErrorDetail = prepareBuilder(exception, request)
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Data handling error")
                .build();
        return new ResponseEntity<>(commonErrorDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            HttpRequestMethodNotSupportedException.class,
            MissingServletRequestParameterException.class
    })
    public ResponseEntity handleWebException(Exception exception, HttpServletRequest request) {
        CommonErrorDetail commonErrorDetail = prepareBuilder(exception, request)
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Data handling error")
                .build();
        return new ResponseEntity<>(commonErrorDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RuntimeException.class, Exception.class})
    public ResponseEntity<CommonErrorDetail> handleGeneralException(Exception exception, HttpServletRequest request) {
        CommonErrorDetail commonErrorDetail = prepareBuilder(exception, request)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .title("Internal application error")
                .build();
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(commonErrorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private CommonErrorDetail.CommonErrorDetailBuilder prepareBuilder(Exception exception, HttpServletRequest request) {
        return CommonErrorDetail.builder()
                .timestamp(new Date().getTime())
                .detail(exception.getMessage())
                .exception(exception.getClass().getName())
                .method(request.getMethod())
                .requestedPath(request.getServletPath());
    }

}