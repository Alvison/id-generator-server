package cn.superid.id_generator.net.beans;

import cn.superid.net.beans.Message;

import java.util.Map;

/**
 * Created by 维 on 2014/9/9.
 */
public class IdRequest extends Message {
    private String eventName = "id_generate";
    private String requestId;
    private String table;
    private Map<String, Object> record;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public Map<String, Object> getRecord() {
        return record;
    }

    public void setRecord(Map<String, Object> record) {
        this.record = record;
    }
}
