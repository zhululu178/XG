package com.letsup.habit.app.backend.vo;

/**
 * rabbitmq发送的消息内容
 */
public class MessageVo {
    private String type;
    private Long id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
