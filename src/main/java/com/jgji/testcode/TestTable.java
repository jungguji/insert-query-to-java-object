package com.jgji.testcode;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TestTable {

    private String name;
    private int age;
    private String homepage;
    private String tag;
    private String nickname;
    private String address;
    private String detailAddress;
    private String gender;

    @Builder
    public TestTable(String name, int age, String homepage, String tag, String nickname, String address, String detailAddress, String gender) {
        this.name = name;
        this.age = age;
        this.homepage = homepage;
        this.tag = tag;
        this.nickname = nickname;
        this.address = address;
        this.detailAddress = detailAddress;
        this.gender = gender;
    }
}
