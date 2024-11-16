package com.sky.gzy;

import org.springframework.util.DigestUtils;

public class Md5Tester {
    public static void main(String[] args) {
        String str1 = "123456";
        String password = DigestUtils.md5DigestAsHex(str1.getBytes());
        System.out.println(password);
    }
}
