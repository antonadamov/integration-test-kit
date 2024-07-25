package com.example.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class TestDataUtils {

    public static String getRandomString(int length){
        return RandomStringUtils.randomAlphabetic(length);
    }

    public static String getUUID(){
        return UUID.randomUUID().toString();
    }

    public int getRandomNumber(){
        return getRandomNumber(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public int getRandomNumber(int start, int end) {
        return ThreadLocalRandom.current().nextInt(start, end);
    }



}
