package com.cs.springbootwebtuts.advices;

import com.cs.springbootwebtuts.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleNoSuchElementException(ResourceNotFoundException ex) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .build();
        ApiResponse<?> apiResponse = buildApiResponseErrorEntity(apiError);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGenericException(Exception ex) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("An unexpected error occurred: " + ex.getMessage())
                .build();
        ApiResponse<?> apiResponse = buildApiResponseErrorEntity(apiError);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        List<String> list = exception.getBindingResult().getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .toList();


        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Input Validation Failed for the following fields : ")
                .subErrors(list)
                .build();
        ApiResponse<?> apiResponse = buildApiResponseErrorEntity(apiError);
        return ResponseEntity.status(apiError.getStatus()).body(apiResponse);
    }

    private ApiResponse<?> buildApiResponseErrorEntity(ApiError apiError) {
        return new ApiResponse<>(apiError);
    }
}
