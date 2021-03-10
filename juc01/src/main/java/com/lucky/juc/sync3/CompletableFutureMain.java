package com.lucky.juc.sync3;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

/**
 * @FileName: CompletableFutureMain.java
 * @description:
 * @author: 欧阳小广
 * @Date: 2021-03-10
 **/
public class CompletableFutureMain implements Serializable {


    public static void main(String[] args) throws InterruptedException {
        // 两个CompletableFuture执行异步查询编码:
        CompletableFuture<String> cxfromSina = CompletableFuture.supplyAsync(() -> {
            return queryCode("中国石油", "https://finance.sina.com.cn/code/");
        });
        CompletableFuture<String> cxfrom163 = CompletableFuture.supplyAsync(() -> {
            return queryCode("中国石油", "https://money.163.com/code/");
        });
        // 用anyOf合并为一个新的CompletableFuture:
        CompletableFuture<Object> codeQuery = CompletableFuture.anyOf(cxfromSina, cxfrom163);

        codeQuery.thenAccept((a)->{
                System.out.println("code："+a);
        });
        // 两个CompletableFuture执行异步查询价格:
        CompletableFuture<Double> cfFetchFromSina = codeQuery.thenApplyAsync((code) -> {
            return fetchPrice((String) code, "https://finance.sina.com.cn/price/");
        });
        CompletableFuture<Double> cxprice163 = codeQuery.thenApplyAsync((code) -> {
            return fetchPrice((String) code, "https://money.163.com/code/");

        });

        // 用anyOf合并为一个新的CompletableFuture:
        CompletableFuture<Object> priceQuery = CompletableFuture.anyOf(cfFetchFromSina, cxprice163);

        priceQuery.thenAccept((result)->{
            System.out.println("price："+result);
        });

        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
        Thread.sleep(200);

    }


    static String queryCode(String name, String url) {
        System.out.println("quert code from " + url + "....");

        try {
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "601857";
    }


    static Double fetchPrice(String code, String url) {
        System.out.println("quert Price from " + url + "....");

        try {
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 5 + Math.random() * 20;
    }
}
