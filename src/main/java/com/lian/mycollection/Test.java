package com.lian.mycollection;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/29 22:20
 */
@Slf4j
public class Test {

    public static Integer count = 0;

    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        for (int i = 0;i<1000;i++){
            Thread thread = new Thread(() -> {
                reentrantLock.lock();
                try{
                    count++;
                }catch (Exception e){
                    log.info(e.getMessage());
                }finally {
                    reentrantLock.unlock();
                }
            });
            thread.start();
        }

        try {
            Thread.sleep(2000);
            System.out.println(count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
