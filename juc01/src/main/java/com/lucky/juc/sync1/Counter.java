package com.lucky.juc.sync1;

class Counter {
    public static final Object lock = new Object();
    public static int count = 0;
}