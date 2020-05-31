package com.lian.mycollection.producerAndConsumer.synchronizedVersion;

import com.lian.mycollection.producerAndConsumer.Bread;
import com.lian.mycollection.threadLocal.MyThreadLocal;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/30 21:53
 */
@Slf4j
public class SynchronizeConsumer implements Runnable {

    private static Integer consumerNum = 0;
    private String name;
    private SynchronizedBucket synchronizedBucket;

    public SynchronizeConsumer(SynchronizedBucket synchronizedBucket, String name){
        this.name = name;
        this.synchronizedBucket = synchronizedBucket;
        consumerNum++;
    }
    private synchronized Bread takeOut(){

//        synchronized (synchronizedBucket){
            Bread bread = null;
            try{
                if(synchronizedBucket.size()<consumerNum){
                    synchronizedBucket.wait();
                }
                Thread.sleep(1);
                bread = synchronizedBucket.takeOut();
                System.out.println(name+"消费"+bread.getName());
                System.out.println(synchronizedBucket.getAllThing());
            }catch (Exception e){
                log.info(MyThreadLocal.getByKey("ThreadDetail",String.class)+"发生异常");
                log.info("异常原因---->"+e.toString());
            }
//            synchronizedBucket.notifyAll();
        this.notifyAll();
            return bread;
//        }
    }
    @Override
    public void run() {
        MyThreadLocal.put("ThreadDetail",Thread.currentThread().getName()+":"+name);
        while (true){
           takeOut();
        }
    }
}
