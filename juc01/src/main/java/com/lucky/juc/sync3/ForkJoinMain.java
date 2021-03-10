package com.lucky.juc.sync3;

import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @FileName: ForkJoinMain.java
 * @description:
 * @author: 欧阳小广
 * @Date: 2021-03-10
 **/
public class ForkJoinMain implements Serializable {

    public static void main(String[] args) {
        // 创建2000个随机数组成的数组:
        long[] array = new long[2000];
        long expectedSum = 0;
        for (int i = 0; i < array.length; i++) {
            array[i] = random();
            expectedSum += array[i];
        }
        System.out.println("Expected sum: " + expectedSum);
        // fork/join:
        ForkJoinTask<Long> task = new SumTask(array, 0, array.length);
        long startTime = System.currentTimeMillis();
        Long result = ForkJoinPool.commonPool().invoke(task);
        long endTime = System.currentTimeMillis();
        System.out.println("Fork/join sum: " + result + " in " + (endTime - startTime) + " ms.");
    }

//    ReadWriteLock
//    ReentrantReadWriteLock

    static Random random = new Random(0);

    static long random() {
        return random.nextInt(10000);
    }
}
