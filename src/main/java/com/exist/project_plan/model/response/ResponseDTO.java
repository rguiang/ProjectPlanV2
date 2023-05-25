package com.exist.project_plan.model.response;

import com.exist.project_plan.model.enums.ResponseStatus;

import java.io.Serializable;
public class ResponseDTO<T> implements Serializable {

    private ResponseStatus status;
    private T data;

    public ResponseDTO(ResponseStatus status, T data) {
        this.status = status;
        this.data = data;
    }
}
