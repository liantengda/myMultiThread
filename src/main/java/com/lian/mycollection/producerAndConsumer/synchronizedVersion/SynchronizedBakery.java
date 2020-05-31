package com.lian.mycollection.producerAndConsumer.synchronizedVersion;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/30 22:10
 */
public class SynchronizedBakery {
    public static void main(String[] args) {
        SynchronizedBucket synchronizedBucket = new SynchronizedBucket();
        SynchronizeProducer backer1 = new SynchronizeProducer("面包师傅1", synchronizedBucket);
        SynchronizeProducer backer2 = new SynchronizeProducer("面包师傅2", synchronizedBucket);
        SynchronizeConsumer synchronizeConsumer1 = new SynchronizeConsumer(synchronizedBucket,"消费者1");
        SynchronizeConsumer synchronizeConsumer2 = new SynchronizeConsumer(synchronizedBucket,"消费者2");
        new Thread(backer1).start();
        new Thread(backer2).start();
        new Thread(synchronizeConsumer1).start();
        new Thread(synchronizeConsumer2).start();
    }
}
