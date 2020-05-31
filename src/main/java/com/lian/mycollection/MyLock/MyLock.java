package com.lian.mycollection.MyLock;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/30 21:04
 */
public class MyLock {

    //线程的状态 0 表示当前没有线程占用 1 表示有线程占用
    AtomicInteger status = new AtomicInteger();
    // 等待的线程
    LinkedBlockingDeque<Thread> waiters =  new LinkedBlockingDeque<Thread>();
    //当前线程拥有者
    Thread ownerThread = null;


    public void lock(){
        if(!tryLock()){
            waiters.add(Thread.currentThread());
            for (;;){
                if(tryLock()){
                    waiters.poll();
                    return;
                }else {
                    LockSupport.park();
                }
            }
        }
    }

    public boolean tryLock(){
        if(status.get()==0){

            if(status.compareAndSet(0,1)){
                ownerThread = Thread.currentThread();
                return true;
            }else if(ownerThread==Thread.currentThread()){
                status.set(status.get()+1);
            }
        }
        return false;
    }

    public void  unlock(){
        if(ownerThread!=Thread.currentThread()){
            throw new RuntimeException("非法操作");
        }

        if(status.decrementAndGet()==0){
            ownerThread = null;
            Thread t = waiters.peek();
            if(t!=null){
                LockSupport.unpark(t);
            }
        }
    }

}
