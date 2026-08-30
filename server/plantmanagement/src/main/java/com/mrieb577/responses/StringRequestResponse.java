package com.mrieb577.responses;

public class StringRequestResponse extends RequestResponse {
    public String body;

    public StringRequestResponse(int code, String body){
        super(code);
        this.body = body;
    }
}
