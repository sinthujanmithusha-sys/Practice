package com.sgic.pratice_2.exceptionHandlers;

import com.sgic.pratice_2.Utils.ResponseWrapper;
import com.sgic.pratice_2.Utils.ValidationMessages;
import com.sgic.pratice_2.enums.RestApiResponseStatusCodes;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;

import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseWrapper<?>> handleValidation(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.VALIDATION_FAILED.getCode(),
                String.join(", ", errors),
                null
        ));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseWrapper<?>> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        String message = e.getMessage().contains("foreign key")
                ? ValidationMessages. FOREIGN_KEY_CONSTRIN
                : ValidationMessages.DUPLICATE_ENTRY;
        return ResponseEntity.badRequest().body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.CONFLICT.getCode(), message, null));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResponseWrapper<?>> handleNoSuchElement(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.NOT_FOUND.getCode(), ValidationMessages.INVALID_ID, null));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseWrapper<?>> handleMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.METHOD_NOT_ALLOWED.getCode(), ValidationMessages.WRONG_API_CALL, null));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseWrapper<?>> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        return ResponseEntity.badRequest().body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.BAD_REQUEST.getCode(), ValidationMessages.MISMATCH_INPUT, null));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseWrapper<?>> handleUnreadableJson(HttpMessageNotReadableException e) {
        return ResponseEntity.badRequest().body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.BAD_REQUEST.getCode(), ValidationMessages.WRONG_JSON, null));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseWrapper<?>> handleIllegalArg(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.BAD_REQUEST.getCode(), e.getMessage(), null));
    }

//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<ResponseWrapper<?>> handleBadCredentials(BadCredentialsException e) {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseWrapper<>(
//                RestApiResponseStatusCodes.UNAUTHORIZED.getCode(), ValidationMessages.INVALID_CREDENTIAL, null));
//    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseWrapper<?>> handleConstraintViolation(ConstraintViolationException e) {
        String errors = e.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity.badRequest().body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.VALIDATION_FAILED.getCode(), errors, null));
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<ResponseWrapper<?>> handleTransactionError(TransactionSystemException e) {
        return ResponseEntity.badRequest().body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.BAD_REQUEST.getCode(), ValidationMessages.MIN_REQUIREMENT, null));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseWrapper<?>> handleResourceNotFound(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                e.getMessage(),
                null
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseWrapper<?>> handleGeneric(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.INTERNAL_SERVER_ERROR.getCode(), e.getMessage(), null));
    }
}
