package com.lian.mycollection.producerAndConsumer.ProducerNumberAndRevertOrderOutPut;

import com.lian.mycollection.MyLock.MyLock;
import com.lian.mycollection.producerAndConsumer.Bread;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/30 21:27
 */
@Slf4j
public class MyLockBucket {

    public static Integer thingsNum = 0;

    private List<Integer> container = new ArrayList<>();

    private static final Integer numArrayLength = 8;

    public static MyLock myLock =  new MyLock();
//public static ReentrantLock reentrantLock = new ReentrantLock();

    public Integer numArrayLength(){
        return numArrayLength;
    }

    public Integer size(){
        return container.size();
    }

    public void putInto(Integer bread){
        container.add(bread);
    }

    public Integer takeOut(){
        Integer integer = container.get(container.size() - 1);
        System.out.print(integer);
        container.remove(integer);
        return integer;
    }

    public List<Integer> getAllThing(){
        return container;
    }



    public static void main(String[] args) {
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = 0;i<100;i++){
            integers.add(i);
        }
        Random random = new Random();
        int i = random.nextInt();

        System.out.println(integers.get(i&(integers.size()-1)));
    }
}
