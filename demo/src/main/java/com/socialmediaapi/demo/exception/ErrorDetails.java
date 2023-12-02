package com.socialmediaapi.demo.exception;

import lombok.*;

import java.time.LocalDateTime;


// Whenever an exception happens we want to return it in the
// specific manner.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErrorDetails {
    private LocalDateTime timestamp;
    private String message;
    private String details;
}
