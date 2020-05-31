package com.lian.mycollection.producerAndConsumer;

import lombok.Data;

/**
 * 面包
 * @author Ted
 * @version 1.0
 * @date 2020/5/30 21:21
 */
@Data
public class Bread {

    private String name;

    public Bread(String name){
        this.name = name;
    }
}
