package com.oneshoppoint.yates.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * Created by robinson on 4/9/16.
 */
public class RestMessage<T> {
    private String status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<FieldError> errors;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String next;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String prev;

    public RestMessage(String status,String message) {
        this.status = status;
        this.message = message;
    }

    public RestMessage(String status,String message,List<FieldError> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public RestMessage(String status,T data) {
        this.status = status;
        this.data = data;
    }

    public RestMessage(String status,T data,String prev,String next) {
        this.status = status;
        this.data = data;
        this.prev = prev;
        this.next = next;
    }

    public String getStatus() {
        return this.status;
    }


    public String getMessage() {
        return this.message;
    }

    public List<FieldError> getErrors() { return this.errors;}

    public T getData() {
        return this.data;
    }

    public String getPrev() {
        return this.prev;
    }

    public String getNext() {
        return this.next;
    }
}
