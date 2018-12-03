/**
 * elven.site Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package com.simple.demo.model;

/**
 * @author qiusheng.wu
 * @Filename Student.java
 * @description
 * @Version 1.0
 * @History <li>Author: qiusheng.wu</li>
 * <li>Date: 2017/10/30 22:36</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public class Student {
    private int id;
    private String name;
    private String sex;
    private int age;

    public Student() {
    }

    public Student(int id, String name, String sex, int age) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}