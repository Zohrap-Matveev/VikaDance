package am.matveev.dance.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
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

    @ExceptionHandler({ImageNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleException(ImageNotFoundException ex){
        ErrorResponse response = new ErrorResponse(
                "Image with this id not found",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({WrongPasswordException.class})
    public ResponseEntity<ErrorResponse> handleException(WrongPasswordException ex){
        ErrorResponse response = new ErrorResponse(
                "Your password does not match",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        if (ex.getMessage().contains("title")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title must be unique");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data integrity violation occurred");
        }
    }
}
