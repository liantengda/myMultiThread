package com.lian.mycollection.producerAndConsumer.myLockVersion;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/31 9:47
 */
public class MyLockBakery {

    public static void main(String[] args) {
        MyLockBucket myLockBucket = new MyLockBucket();
        MyLockProducer producer1 = new MyLockProducer("面包师傅1", myLockBucket);
        MyLockProducer producer2 = new MyLockProducer("面包师傅2", myLockBucket);
        MyLockConsumer consumer1 = new MyLockConsumer(myLockBucket, "消费者1");

        new Thread(producer1).start();
        new Thread(producer2).start();
        new Thread(consumer1).start();
    }
}
