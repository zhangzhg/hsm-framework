package com.framework.core.model;

/**
 * 保存读取的配置
 * 为了保证读取的顺序增加sort(例如com.framework..*会在com..*之前匹配)
 * 保证最小匹配
 */
public class ConfigModel implements Comparable<ConfigModel> {
    private String key;
    private String value;
    private int sort;

    public ConfigModel(String key, String value, int sort) {
        this.key = key;
        this.value = value;
        this.sort = sort;
    }

    public final String getKey() {
        return key;
    }

    public final void setKey(String key) {
        this.key = key;
    }

    public final String getValue() {
        return value;
    }

    public final void setValue(String value) {
        this.value = value;
    }

    public final int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Override
    public int compareTo(ConfigModel m) {
        int i = this.sort - m.sort;
        return i<0?-1:0;
    }
}
