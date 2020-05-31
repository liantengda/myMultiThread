package com.lian.mycollection.producerAndConsumer.myLockVersion;

import com.lian.mycollection.producerAndConsumer.Bread;
import com.lian.mycollection.producerAndConsumer.synchronizedVersion.SynchronizedBucket;
import com.lian.mycollection.threadLocal.MyThreadLocal;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/31 10:20
 */
@Slf4j
public class MyLockConsumer implements Runnable {
    private static Integer consumerNum = 0;
    private String name;
    private MyLockBucket myLockBucket;

    public MyLockConsumer(MyLockBucket myLockBucket, String name){
        this.name = name;
        this.myLockBucket = myLockBucket;
        consumerNum++;
    }
    private Bread takeOut(){
        Bread bread = null;
        try{
            MyLockBucket.myLock.lock();
            if(myLockBucket.size()>=consumerNum){
                bread = myLockBucket.takeOut();
                Thread.sleep(1000);
                System.out.println(name+"消费"+bread.getName());
            }
            System.out.println(myLockBucket.getAllThing());
        }catch (Exception e){
            log.info(MyThreadLocal.getByKey("ThreadDetail",String.class)+"发生异常");
            log.info("异常原因---->"+e.toString());
        }finally {
            MyLockBucket.myLock.unlock();
        }
        return bread;
    }
    @Override
    public void run() {
        MyThreadLocal.put("ThreadDetail",Thread.currentThread().getName()+":"+name);
        while (true){
            takeOut();
        }
    }
}
