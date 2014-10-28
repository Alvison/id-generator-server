package cn.superid.id_generator.net.beans;

import cn.superid.net.beans.Message;

/**
 * Created by 维 on 2014/9/9.
 */
public class IdResponse extends Message {
    private int code = 0;
    private String id;
    private String requestId;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
