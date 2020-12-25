package com.alian;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class test {
    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        //passwordEncoder.encode("123456")加密方法
        //执行多次加密后的结果均不一样
        //执行第一次:$2a$10$RSlzzO4xAubBLZMB8jPL8eMFX17gM61JfrgINpzOiaEsBdtKBuSDe
        //执行第二次:$2a$10$2uQWdP9qAXGK4H0um0gtG.o1QzbGhBlbxx8YjX526jIrVBNKyziFe
        //执行第三次:$2a$10$8CWttAISfamloSErsqEAPemALyZvj8VWaUCBJKcm2GE1dhFR.7oYG
        //security加密方式是动态加盐
        System.out.println(passwordEncoder.encode("123"));
        //比较明文和密文是否一致
        System.out.println(passwordEncoder.matches("123","$2a$10$lstWyJ6NlbdZ2uVr8ncsHOUKsG0VAT4FzDdy0gqy6gbaOqpp6ouDq"));
    }
}
