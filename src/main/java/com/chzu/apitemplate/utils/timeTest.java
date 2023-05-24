package com.chzu.apitemplate.utils;

import org.junit.jupiter.api.Test;

import java.util.Date;

/**
 * @author water king
 * @time 2023/5/24
 */
public class timeTest {

    @Test
    void demo() {
        System.out.println(new Date(System.currentTimeMillis() + 1000*3600*24));
    }
}
