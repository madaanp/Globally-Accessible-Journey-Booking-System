package com.roadpass.authorization.response;

public class JsonResponse<T> {
    public static final int SUCCESS_CODE = 200;
    public T data;
    public int code;
    public String msg = "";
    public JsonResponse() {
        this.code = SUCCESS_CODE;
    }
    public JsonResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public JsonResponse(T data) {
        this.code = SUCCESS_CODE;
        this.data = data;
    }
}