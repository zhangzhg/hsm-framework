package com.framework.user.constant;

public enum DicDataEnum {
    /**
     * 用户状态正常
     */
    userActive(1, "正常"),
    /**
     * 用户已锁定
     */
    userLocked(2, "锁定");


    //编码
    private int id;
    //名称
    private String name;

    DicDataEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getIntId() {
        return id;
    }
}