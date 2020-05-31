package com.lian.mycollection.producerAndConsumer.synchronizedVersion;

import com.lian.mycollection.producerAndConsumer.Bread;

import java.util.*;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/30 21:27
 */
public class SynchronizedBucket {

    public static Integer thingsNum = 0;

    private List<Bread> container = new ArrayList<>();

    private static final Integer capacity = 8;



    public Integer capacity(){
        return capacity;
    }

    public Integer size(){
        return container.size();
    }

    public void putInto(Bread object){
        container.add(object);
    }

    public Bread takeOut(){
        Random random = new Random();
        Bread t = container.get(random.nextInt()&(container.size()-1));
        container.remove(t);
        return t;
    }

    public List<Bread> getAllThing(){
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
