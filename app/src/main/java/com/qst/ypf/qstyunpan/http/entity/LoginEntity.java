package com.qst.ypf.qstyunpan.http.entity;

import java.io.Serializable;

public class LoginEntity implements Serializable {

    /**
     * countSize : 0.0B
     * id : 44
     * password : B5D9B59113086D3F9F9F108ADAAA9AB5
     * totalSize : 10.0GB
     * username : ho
     */

    private String countSize;
    private int id;
    private String password;
    private String totalSize;
    private String username;

    public String getCountSize() {
        return countSize;
    }

    public void setCountSize(String countSize) {
        this.countSize = countSize;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(String totalSize) {
        this.totalSize = totalSize;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
