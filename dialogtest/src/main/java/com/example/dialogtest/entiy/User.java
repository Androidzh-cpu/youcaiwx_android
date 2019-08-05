package com.example.dialogtest.entiy;

import org.litepal.crud.LitePalSupport;

/**
 * Author: AND
 * Time: 2019-7-8.  上午 10:46
 * Package: com.example.dialogtest.entiy
 * FileName: User
 * Description:TODO 测试类
 */
public class User extends LitePalSupport {
    private int id;
    private String name;

    private float price;

    private byte[] cover;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }
}
