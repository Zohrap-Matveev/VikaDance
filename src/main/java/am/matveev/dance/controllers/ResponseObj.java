package am.matveev.dance.controllers;

import java.io.Serializable;

public class ResponseObj <T> implements Serializable{

    private int status;
    private String message;
    private T body;

    public ResponseObj() {
    }

    public int getStatus(){
        return status;
    }

    public void setStatus(int status){
        this.status = status;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public T getBody(){
        return body;
    }

    public void setBody(T body){
        this.body = body;
    }
}
/*
@PostMapping("/news")
    public ResponseEntity<ResponseObj<NewsDTO>> postNews(@RequestBody NewsRequest request){
        ResponseObj<NewsDTO> responseObj = new ResponseObj<>();

        if(! checkService.checkAdminPassword(request.getEnteredPassword())){
            responseObj.setMessage("Wrong password");
            responseObj.setStatus(HttpStatus.UNAUTHORIZED.value());
            return ResponseEntity.ok(responseObj);
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access.");
        }

        NewsDTO n = newsService.createNews(request.getNewsDTO());
        responseObj.setMessage("Wrong password");
        responseObj.setStatus(HttpStatus.UNAUTHORIZED.value());
        responseObj.setBody(n);
        return ResponseEntity.ok(responseObj);
    }
 */