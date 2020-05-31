package com.lian.mycollection.producerAndConsumer.synchronizedVersion;

import com.lian.mycollection.producerAndConsumer.Bread;
import com.lian.mycollection.threadLocal.MyThreadLocal;
import lombok.extern.slf4j.Slf4j;

/**
 * 生产者
 * @author Ted
 * @version 1.0
 * @date 2020/5/30 21:19
 */
@Slf4j
public class SynchronizeProducer implements Runnable {

    private String producerName;
    /**
     * 面包桶
     */
    private SynchronizedBucket synchronizedBucket = null;

    public SynchronizeProducer(String producerName, SynchronizedBucket synchronizedBucket){
        this.producerName = producerName;
        this.synchronizedBucket = synchronizedBucket;
    }

    /**
     * 生产面包
     */
    private void produce(){

        synchronized (synchronizedBucket){

            if(synchronizedBucket.size()>= synchronizedBucket.capacity()){
                try {
                    synchronizedBucket.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SynchronizedBucket.thingsNum++;
            System.out.println(producerName+"生产面包"+SynchronizedBucket.thingsNum);
            Bread bread = new Bread("面包"+SynchronizedBucket.thingsNum);
            try{
                MyThreadLocal.put("ThreadDetail",Thread.currentThread().getName()+":"+producerName);
                synchronizedBucket.putInto(bread);
            }catch (Exception e){
                log.info(MyThreadLocal.getByKey("ThreadDetail",String.class)+"发生异常");
                log.info("异常原因-->"+e.toString());
            }
            System.out.println(synchronizedBucket.getAllThing());
            synchronizedBucket.notifyAll();
        }
    }

    @Override
    public void run() {
        while (true){
           produce();
        }
    }
}
