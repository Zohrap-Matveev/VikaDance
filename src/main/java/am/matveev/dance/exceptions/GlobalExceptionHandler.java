package am.matveev.dance.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler({NewsNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleException(NewsNotFoundException ex){
        ErrorResponse response = new ErrorResponse(
                "News with this id not found",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ProjectNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleException(ProjectNotFoundException ex){
        ErrorResponse response = new ErrorResponse(
                "Project with this id not found",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
