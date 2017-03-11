package com.mango.framework.base;

/**
 * Created by Administrator on 2016/1/31.
 */
public class BaseEvent<T> {
    /**
     * 通用事件类型
     */
    public static final int TYPE_create= 0;
    public static final int TYPE_add = 1;
    public static final int TYPE_edit = 2;
    public static final int TYPE_update = 3;
    public static final int TYPE_delete = 4;
    public static final int TYPE_select = 5;
    public static final int TYPE_unSelect = 6;
    public static final int TYPE_close = 7;
    public static final int TYPE_refresh = 8;
    public String action;
    public int type;
    public T data;

    public BaseEvent() {
    }
    public BaseEvent(T data) {
        this.data = data;
    }

    public BaseEvent(int type) {
        this.type = type;
    }
    public BaseEvent(T data, int type) {
        this.type = type;
        this.data = data;
    }

    public BaseEvent(String action) {
        this.action = action;
    }
    public BaseEvent(int type,String action) {
        this.action = action;
        this.type = type;
    }
}
