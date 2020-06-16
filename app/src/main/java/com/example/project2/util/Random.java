package com.example.project2.util;

import java.util.UUID;

public class Random {
    public static String getRandomUID(){
        return UUID.randomUUID().toString();
    }
}