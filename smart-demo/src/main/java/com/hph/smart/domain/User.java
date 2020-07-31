package com.hph.smart.domain;

import java.util.UUID;

/**
 * 用户数据
 * @author hph
 */
public class User {

    private Long id;

    private String name;

    private Long age;

    public User(Long id, String name, Long age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }
}
