package com.comic.backend.reponse;


/**
 * Created by Admin on 4/11/2018.
 */
public class ResponseEntity<T> {
    public Boolean success;
    public String exceptionMessage;
    public T dataResponse;

    public ResponseEntity() {
    }

    public ResponseEntity(Boolean success, String exceptionMessage, T dataResponse) {
        this.success = success;
        this.exceptionMessage = exceptionMessage;
        this.dataResponse = dataResponse;
    }
    public ResponseEntity(Boolean success, String exceptionMessage) {
        this.success = success;
        this.exceptionMessage = exceptionMessage;
    }
    public ResponseEntity(T dataResponse) {
        this.success = true;
        this.dataResponse = dataResponse;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }


    public T getDataResponse() {
        return dataResponse;
    }

    public void setDataResponse(T dataResponse) {
        this.dataResponse = dataResponse;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }
}
