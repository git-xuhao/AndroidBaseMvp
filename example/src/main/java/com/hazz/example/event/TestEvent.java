package com.hazz.example.event;

/**
 * @author xuhao
 * @date 2018/6/13 16:26
 * @desc
 */
public class TestEvent {
    long id;
    String name;
    public TestEvent(long id,String name) {
        this.id= id;
        this.name= name;
    }
    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
}
