package com.framework.core.enums;

/**
 * websocket类型枚举
 *
 * @author linliangkun
 * @date 15/7/22
 */
public enum WebSocketEnum {

    SESSION_USER_ID("userId"),

    WEBSOCKET_KEY("USER_ID");

    /** 状态值 */
    private String value;

    WebSocketEnum(String value) {
        this.value = value;
    }

    public final String getValue() {
        return value;
    }

    public WebSocketEnum getByValue(String value) {
        for (WebSocketEnum webSocketEnum : WebSocketEnum.values()) {
            if (webSocketEnum.getValue() == value) {
                return webSocketEnum;
            }
        }

        return WebSocketEnum.SESSION_USER_ID; // 默认返回启用
    }
}
