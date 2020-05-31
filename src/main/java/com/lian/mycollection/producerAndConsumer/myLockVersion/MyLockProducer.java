package com.lian.mycollection.producerAndConsumer.myLockVersion;

import com.lian.mycollection.MyLock.MyLock;
import com.lian.mycollection.producerAndConsumer.Bread;
import com.lian.mycollection.threadLocal.MyThreadLocal;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/31 9:34
 */
@Slf4j
public class MyLockProducer implements Runnable {
    private String producerName;

    private MyLockBucket bucket = null;

    public MyLockProducer(String producerName, MyLockBucket bucket){
        this.producerName = producerName;
        this.bucket = bucket;
    }

    private void produce(){
        try{
            MyLockBucket.myLock.lock();
            if(bucket.size()<bucket.capacity()){
                System.out.println(producerName+"生产面包"+ ++MyLockBucket.thingsNum);
                Thread.sleep(1000);
                Bread bread = new Bread("面包"+MyLockBucket.thingsNum);
                bucket.putInto(bread);
            }
            System.out.println(bucket.getAllThing());
        }catch (Exception e){
            log.info(MyThreadLocal.getByKey("ThreadDetail",String.class)+"发生异常");
            log.info("异常原因-->"+e.toString());
        }finally {
            MyLockBucket.myLock.unlock();
        }
    }
    @Override
    public void run() {
        MyThreadLocal.put("ThreadDetail",Thread.currentThread().getName()+":"+producerName);
        while (true){
            produce();
        }
    }
}
