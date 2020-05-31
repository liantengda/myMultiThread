package com.lian.mycollection.threadLocal;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Ted
 * @version 1.0
 * @date 2020/5/30 15:19
 */
public class MyThreadLocal {

    private static ThreadLocal<Map<String,Object>> params = new ThreadLocal<Map<String,Object>>();

    /**
     * 为当前线程增加属于自己的参数
     * @param key
     * @param value
     */
    public static void put(String key,Object value){
        if(params.get()!=null){
            params.get().put(key,value);
        }else {
            HashMap<String, Object> data = new HashMap<>();
            data.put(key,value);
            params.set(data);
        }
    }

    /**
     * 更新所有参数值，用新的替换老的
     * @param param
     */
    public static void putAllParams(Map param){
        params.set(param);
    }

    /**
     * 返回指定参数
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getByKey(String key,Class<T> clazz){
        if(params.get()==null){
            return null;
        }

        return (T)(params.get().get(key));
    }

    /**
     * 返回所有参数
     * @return
     */
    public static Map<String,Object> getAllParams(){
        return params.get();
    }

    /**
     * 清空保存的数据
     */
    public static void clear(){
        params.set(new HashMap<String,Object>());
    }

    public static void main(String[] args) {

        for (int i =0;i<100;i++)
            new Thread(
                ()-> {
                    System.out.println(Thread.currentThread().getName());
                    MyThreadLocal.put("ThreadName","Thread"+Thread.currentThread().getName());
                    if(MyThreadLocal.getByKey("ThreadName",String.class).contains("70")){
                        System.out.println("捕捉到的线程为--->"+Thread.currentThread());
                        System.out.println("当前线程为70线程--->，进行装填火箭操作");
                        HashMap<String, Rocket> seventyNumParam = new HashMap<>();
                        Rocket rocket = new Rocket();
                        rocket.setName("羊毛七号");
                        rocket.setType("迷你核弹型");
                        seventyNumParam.put("7号",rocket);
                        MyThreadLocal.putAllParams(seventyNumParam);
                    }
                    System.out.println("当前线程--->"+Thread.currentThread()+"携带物品-->"+MyThreadLocal.getAllParams());
                }
            ).start();
    }
}

/**
 * 火箭类
 */
@Data
class Rocket{
    private String name;
    private String type;
}
