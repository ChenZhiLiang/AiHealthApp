package com.app.aihealthapp.core.eventbus;

/**
 * author：chenzl
 * Create time: 2017/11/16 0016 09:07
 * describe:事件，EventBus订阅发布。
 * e_mail：chenzhiliang@guangxirenrenparking.com
 */
public class Event<T> {
    private int code;
    private int intMessage;
    private String eventMessage;
    private T data;

    public Event(int code) {
        this.code = code;
    }
    public Event(int code,int intMessage){
        this.code = code;
        this.intMessage = intMessage;
    }
    public Event(int code, int intMessage, String eventMessage){
        this.code = code;
        this.intMessage = intMessage;
        this.eventMessage = eventMessage;
    }
    public Event(int code, int intMessage, String eventMessage, T data){
        this.code = code;
        this.intMessage = intMessage;
        this.eventMessage = eventMessage;
        this.data =data;
    }
    public Event(int code,int intMessage,T data){
        this.code = code;
        this.intMessage = intMessage;
        this.data = data;

    }
    public Event(int code, String eventMessage){
        this.code = code;
        this.eventMessage = eventMessage;
    }
    public Event(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public Event(int code, String eventMessage, T data){
        this.code = code;
        this.eventMessage = eventMessage;
        this.data = data;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getEventMessage() {
        return eventMessage;
    }

    public void setEventMessage(String eventMessage) {
        this.eventMessage = eventMessage;
    }

    public int getIntMessage() {
        return intMessage;
    }

    public void setIntMessage(int intMessage) {
        this.intMessage = intMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
