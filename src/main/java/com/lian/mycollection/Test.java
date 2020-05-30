package com.lian.mycollection;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/29 22:20
 */
public class Test {
    public final static ThreadLocal<String> RESOURCE = new ThreadLocal<>();
    public static void main(String[] args) {
        Thread aaa = new Thread(() -> RESOURCE.set("aaa"));

        System.out.println(RESOURCE.get());

        new Thread(()-> System.out.println(RESOURCE.get())).start();
    }
}
