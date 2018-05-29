package com.bwei.www.jingdong.eventMessage;


public class MessageEventBus {
    private String msg;
    public MessageEventBus(String msg) {

        this.msg = msg;
    }
    public String getMsg(){
        return msg;
    }

    @Override
    public String toString() {
        return "MessageEventBus{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
