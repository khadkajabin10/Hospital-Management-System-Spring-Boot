package com.example.demo.error;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiError {
    private LocalDateTime timestamp;
    private String error;
    private HttpStatus statuscode;
    public ApiError(){
        this.timestamp=LocalDateTime.now();

    }
    public ApiError(String error ,HttpStatus statuscode){
        this();//this() is a call to another constructor in the same class.
        // First, it runs the no‑arg constructor → sets timestamp = LocalDateTime.now().
        this.error=error;
        this.statuscode=statuscode;
    }
    //@NoArgsConstructor would generate a default constructor, but it wouldn’t set timestamp.
    // You want every ApiError to automatically have the current time, so you wrote your own constructor.
    //
    //@AllArgsConstructor would generate a constructor with all three fields (timestamp, error, statuscode).
    // But then you’d have to manually pass timestamp every time,
    // which defeats the purpose of auto‑setting it to LocalDateTime.now().
}
