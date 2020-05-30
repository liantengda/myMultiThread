package com.lian.mycollection.common.myCollection.MySkipList.skipList;

import com.lian.mycollection.common.myCollection.MyLink.ordinaryLink.MyLink;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/23 19:50
 */
@Slf4j
public class SkipList<T> {
    private SkipListNode<T> head,tail;
    private int nodes;//节点总数
    private int listLevel;//层数
    private Random random;// 用于投掷硬币
    private static final double PROBABILITY=0.5;//向上提升一个的概率
    public SkipList() {
        random=new Random();
        clear();
    }
    /**
     *清空跳跃表
     * */
    public void clear(){
        head=new SkipListNode<T>(SkipListNode.HEAD_KEY, null);
        tail=new SkipListNode<T>(SkipListNode.TAIL_KEY, null);
        horizontalLink(head, tail);
        listLevel=0;
        nodes=0;
    }
    public boolean isEmpty(){
        return nodes==0;
    }

    public int size() {
        return nodes;
    }
    /**
     * 在最下面一层，找到要插入的位置前面的那个key
     * */
    private SkipListNode<T> findNode(int key){
        SkipListNode<T> p=head;
        while(true){
            while (p.right.key!=SkipListNode.TAIL_KEY&&p.right.key<=key) {
                p=p.right;
            }
            if (p.down!=null) {
                p=p.down;
            }else {
                break;
            }

        }
        return p;
    }
    /**
     * 查找是否存储key，存在则返回该节点，否则返回null
     * */
    public SkipListNode<T> search(int key){
        SkipListNode<T> p=findNode(key);
        if (key==p.getKey()) {
            return p;
        }else {
            return null;
        }
    }
    /**
     * 向跳跃表中添加key-value
     *
     * */
    public void put(int k,T v){
        SkipListNode<T> p=findNode(k);
        //如果key值相同，替换原来的vaule即可结束
        if (k==p.getKey()) {
            p.value=v;
            return;
        }
        SkipListNode<T> q=new SkipListNode<T>(k, v);
        backLink(p, q);
        int currentLevel=0;//当前所在的层级是0
        //抛硬币
        while (random.nextDouble()<PROBABILITY) {
            //如果超出了高度，需要重新建一个顶层
            if (currentLevel>=listLevel) {
                listLevel++;
                SkipListNode<T> p1=new SkipListNode<T>(SkipListNode.HEAD_KEY, null);
                SkipListNode<T> p2=new SkipListNode<T>(SkipListNode.TAIL_KEY, null);
                horizontalLink(p1, p2);
                verticalLink(p1, head);
                verticalLink(p2, tail);
                head=p1;
                tail=p2;
            }
            //将p移动到上一层
            while (p.up==null) {
                p=p.left;
            }
            p=p.up;

            SkipListNode<T> e=new SkipListNode<T>(k, null);//只保存key就ok
            backLink(p, e);//将e插入到p的后面
            verticalLink(e, q);//将e和q上下连接
            q=e;
            currentLevel++;
        }
        nodes++;//层数递增
    }
    //node1后面插入node2
    private void backLink(SkipListNode<T> node1,SkipListNode<T> node2){
        node2.left=node1;
        node2.right=node1.right;
        node1.right.left=node2;
        node1.right=node2;
    }
    /**
     * 水平双向连接
     * */
    private void horizontalLink(SkipListNode<T> node1,SkipListNode<T> node2){
        node1.right=node2;
        node2.left=node1;
    }
    /**
     * 垂直双向连接
     * */
    private void verticalLink(SkipListNode<T> node1, SkipListNode<T> node2){
        node1.down=node2;
        node2.up=node1;
    }
    /**
     * 打印出原始数据
     * */
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        if (isEmpty()) {
            return "跳跃表为空！";
        }
        StringBuilder builder=new StringBuilder();
        SkipListNode<T> p=head;
        while (p.down!=null) {
            p=p.down;
        }

        while (p.left!=null) {
            p=p.left;
        }
        if (p.right!=null) {
            p=p.right;
        }
        while (p.right!=null) {
            builder.append(p);
            builder.append("\n");
            p=p.right;
        }

        return builder.toString();
    }

    public static void main(String[] args) {
        SkipList<Integer> skipList=new SkipList<Integer>();
        MyLink<Integer> myLink = new MyLink<>();
        long initialSkipListStartTime = System.currentTimeMillis();
        for (int i = 0;i<10000000;i++){
            skipList.put(i,i);
        }
        long initialSkipListEndTime = System.currentTimeMillis();

        long querySkipListStartTime = System.currentTimeMillis();
        SkipListNode<Integer> search = skipList.search(7777777);
        long querySkipListEndTime = System.currentTimeMillis();

        long insertSkipListStartTime = System.currentTimeMillis();
        skipList.put(5000000,520);
        long insertSkipListEndTime = System.currentTimeMillis();

        long initialMyLinkStartTime = System.currentTimeMillis();
        for (int i = 0;i<10000000;i++){
            myLink.add(i);
        }
        long initialMyLinkEndTime = System.currentTimeMillis();

        long queryMyLinkStartTime = System.currentTimeMillis();
        Object queryMyLink = myLink.get(7777777);
        long queryMyLinkEndTime = System.currentTimeMillis();

        long insertMyLinkStartTime = System.currentTimeMillis();
        Object insertMyLink = myLink.addByIndex(5000000, 520);
        long insertMyLinkEndTime = System.currentTimeMillis();

        log.info("跳表初始化用时{},跳表插入用时{},得到数据{},跳表查询用时{},得到数据{}",(initialSkipListEndTime-initialSkipListStartTime),(insertSkipListEndTime-insertSkipListStartTime),skipList.search(1000001).getValue(),(querySkipListEndTime-querySkipListStartTime),search.getValue());
        log.info("链表初始化用时{},链表插入用时{},得到数据{},链表查询用时{},得到数据{}",(initialMyLinkEndTime-initialMyLinkStartTime),(insertMyLinkEndTime-insertMyLinkStartTime),insertMyLink,(queryMyLinkEndTime-queryMyLinkStartTime),queryMyLink);
    }
}
