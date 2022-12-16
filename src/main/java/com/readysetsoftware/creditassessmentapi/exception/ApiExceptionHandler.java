package com.readysetsoftware.creditassessmentapi.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.readysetsoftware.creditassessmentapi.data.payload.response.ApiError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    ZonedDateTime dateNow = ZonedDateTime.now(ZoneId.of("Australia/Canberra"));
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers, HttpStatus httpStatus, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiError apiError = new ApiError(status, e.getMessage(), e.getLocalizedMessage(), dateNow);
        return new ResponseEntity<>(apiError, status);
    }
    @ExceptionHandler({ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
        //1. Create payload containing exception details
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiError apiError = new ApiError(status, e.getMessage(), e.getLocalizedMessage(), dateNow);
        //2. Return response entity
        return new ResponseEntity<>(apiError, status);
    }

    /*
      MethodArgumentNotValidException:
      This exception is thrown when an argument annotated with @Valid failed validation
   */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> errors = new ArrayList<String>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }

        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ApiError apiError = new ApiError(status, ex.getMessage(), errors, dateNow);
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    /*
     * MissingServletRequestParameterException:
     * This exception is thrown when the request is missing a parameter
     */

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String error = ex.getParameterName() + " parameter is missing.";
        ApiError apiError = new ApiError(status, ex.getMessage(), error, dateNow);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());

    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request){

        String error = ex.getMethod() + " is not allowed.";
        ApiError apiError = new ApiError(status, ex.getMessage(), error, dateNow);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());

    }

    /*
     * TypeMismatchException – This exception is thrown when trying to set bean property with the wrong type.
     * MethodArgumentTypeMismatchException – This exception is thrown when method argument is not the expected type:
     */
    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
        ApiError apiError = new ApiError(badRequest, ex.getLocalizedMessage(), error, dateNow);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());

    }

    @ExceptionHandler(UnauthorizedException.class)
    protected ResponseEntity<Object> unauthorizedException(UnauthorizedException ex, WebRequest request) {

        HttpStatus badRequest = HttpStatus.UNAUTHORIZED;
        String error = "UNAUTHORIZED";
        ApiError apiError = new ApiError(badRequest, ex.getMessage(), error, dateNow);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> resourceNotFound(ResourceNotFoundException ex) {

        HttpStatus badRequest = HttpStatus.NOT_FOUND;
        String error = "NOT_FOUND";
        ApiError apiError = new ApiError(badRequest, ex.getMessage(), error, dateNow);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());

    }

}
