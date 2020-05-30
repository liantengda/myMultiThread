package com.lian.mycollection;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/23 12:08
 */
public class testArray {

    private static Integer initialCapacity = 100000000;
    private static Integer arrayRealSize = 0;
    public static void main(String[] args) {
        Integer [] originArray = new Integer[initialCapacity];
        Long initialStartTime = System.currentTimeMillis();
        for(int i = 0;i<initialCapacity-10;i++){
            originArray[i] = i;
            arrayRealSize++;
        }
        Long initialEndTime = System.currentTimeMillis();
        System.out.println("初始化用时---->"+(initialEndTime-initialStartTime));
        Long startTime = System.currentTimeMillis();
        Object[] newArray = add(originArray, 3, 520);
        Long endTime = System.currentTimeMillis();
        System.out.println("插入用时---->"+(endTime-startTime));
        System.out.println(newArray[3]);

    }

    public static Object[] add(Object[] oldArray,int index,Object element){
        if(arrayRealSize<initialCapacity){
            for (int i = arrayRealSize;i>index;i--){
                oldArray[i]=oldArray[i-1];
            }
            oldArray[index] = element;
            arrayRealSize++;
        }
        return oldArray;
    }
}
