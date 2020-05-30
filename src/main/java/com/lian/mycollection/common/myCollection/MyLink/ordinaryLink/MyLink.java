package com.lian.mycollection.common.myCollection.MyLink.ordinaryLink;

import lombok.Data;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/23 13:36
 */
@Data
public class MyLink<T> {
    /**
     * 自定义尾部节点，一直指向链表尾部
     */
    private MyNode last = null;
    /**
     * 自定义头节点，一直指向链表头部
     */
    private MyNode first = null;
    /**
     * 自定义遍历节点，每次遍历完成记得都置为空
     */
    private MyNode iteratorNode = null;
    /**
     * 链表元素的数量
     */
    private int realSize = 0;

    public MyLink(){

    }

    private static class MyNode<E>{
        E item;
        MyNode<E> next;
        MyNode<E> prev;
        MyNode(MyNode<E> prev, E element, MyNode<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    public T add(T element){
        MyNode<T> newNode = new MyNode<T>(last,element,null);
        if(first == null){
            first = newNode;
            last = first;
        }else{
            last.next = newNode;
            last = newNode;
        }
        realSize++;
        return element;
    }

    public int size(){
        return realSize;
    }


    public Object get(int index){
        MyNode requireNode = pinPoint(index);
        return requireNode.item;
    }

    /**
     * 链表中间加一个节点
     * @param index
     * @param element
     * @return
     */
    public Object addByIndex(int index,Object element){
        iteratorNode = first;
        MyNode myNode = pinPoint(index);
        MyNode myNodeNext = myNode.next;
        MyNode newNode = new MyNode<>(myNode, element, myNodeNext);
        myNode.next = newNode;
        myNodeNext.prev = newNode;
        realSize++;
        return newNode.item;
    }

    /**
     * 根据index精准定位一个节点
     * @param index
     * @return
     */
    public MyNode pinPoint(int index){
        iteratorNode = first;
        for (int i = 0;i<index;i++){
            iteratorNode = iteratorNode.next;
        }
        MyNode requireNode = iteratorNode;
        iteratorNode = null;
        return requireNode;
    }


    public static void main(String[] args) {
        MyLink<Integer> integers = new MyLink<>();
        integers.add(1);
        integers.add(9);
        integers.add(10);
        Object o = integers.get(2);
        System.out.println(o);
        System.out.println(integers.size());
        Object o1 = integers.addByIndex(1, 520);
        System.out.println(o1);
    }
}
