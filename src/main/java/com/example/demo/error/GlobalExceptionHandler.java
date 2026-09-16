package com.example.demo.error;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
// GlobalExceptionHandler
//@RestControllerAdvice

//is mainly used for exceptions that happen while Spring is processing your controller layer.
public class GlobalExceptionHandler {
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiError> handleUsernameNotfoundException(UsernameNotFoundException ex){
        ApiError apiError=new ApiError("username not found with user "+ex.getMessage(),HttpStatus.NOT_FOUND);
        //here we call   public ApiError(String error ,HttpStatus statuscode) this
        return  new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);//apiError (the body) and HttpStatus.NOT_FOUND this send status code
        //.ok(...) → quick shortcut for success (200).
        //        //
        //        //new ResponseEntity<>(..., status) → full control,
        //        // lets you set any status code (404, 400, 401, etc.) and a custom body.

    }
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException ex)
    {
        ApiError apiError = new ApiError("Authentication failed: " + ex.getMessage(), HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex) {
        ApiError apiError = new ApiError("Access denied: " + ex.getMessage(), HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN); }
    @ExceptionHandler(JwtException.class) public ResponseEntity<ApiError> handleJwtException(JwtException ex) {
        ApiError apiError = new ApiError("Invalid or expired JWT: " + ex.getMessage(), HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED); }
    @ExceptionHandler(Exception.class) public ResponseEntity<ApiError> handleGenericException(Exception ex) {
        ApiError apiError = new ApiError("Unexpected error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR); }
}
