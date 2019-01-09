package com.hua.recyclerhelper;

/**
 * @author hua
 * @version V1.0
 * @date 2019/1/9 17:05
 */

public class Item {

    int type;
    String name;

    public Item(int type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public String toString() {
        return "type = " + type + ", name = " + name;
    }
}
