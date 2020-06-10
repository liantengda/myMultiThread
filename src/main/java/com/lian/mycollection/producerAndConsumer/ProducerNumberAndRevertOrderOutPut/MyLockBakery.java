package com.lian.mycollection.producerAndConsumer.ProducerNumberAndRevertOrderOutPut;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/31 9:47
 */
public class MyLockBakery {

    public static void main(String[] args) {
        MyLockBucket myLockBucket = new MyLockBucket();
        MyLockProducer producer1 = new MyLockProducer("数字生产者", myLockBucket);
        MyLockConsumer consumer1 = new MyLockConsumer(myLockBucket, "消费者1");

        new Thread(producer1).start();
        new Thread(consumer1).start();
    }
}
