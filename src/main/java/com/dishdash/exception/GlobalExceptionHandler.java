package com.dishdash.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler
//    public ResponseEntity<ErrorResponse> handleException(EmployeeNotFoundException exc) {
//        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), exc.getMessage());
//
//        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
//    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exc) {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    public static class ErrorResponse {
        private final int status;
        private final String message;
        private final long timestamp;

        public ErrorResponse(int status, String message) {
            this.status = status;
            this.message = message;
            this.timestamp = System.currentTimeMillis();
        }

        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

        public long getTimestamp() {
            return timestamp;
        }

        @Override
        public String toString() {
            return "ErrorResponse{" +
                    "status=" + status +
                    ", message='" + message + '\'' +
                    ", timestamp=" + timestamp +
                    '}';
        }
    }
}
