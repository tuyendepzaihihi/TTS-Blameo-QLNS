package com.example.tuyendv.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {

    private T data;
    private int status;
    private String message;

    public Response(int status, String message) {
        this.status = status;
        this.message = message;
    }

}
