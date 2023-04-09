package com.lue.rasp.context;


/**
 * 请求上下文
 * 使用toString 生成 json
 *
 * @author jrasp
 */
public class Context {

    // 原始 http request 对象
    private Object request;

    // 原始 http response 对象
    private Object response;

    public Object getRequest() {
        return request;
    }

    public void setRequest(Object request) {
        this.request = request;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }
}
