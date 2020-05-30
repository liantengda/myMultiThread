package com.lian.mycollection.common.myReference;

import java.lang.ref.WeakReference;

/**
 * 弱引用
 * @author Ted
 * @version 1.0
 * @date 2020/5/30 11:03
 */
public class TestWeakReference {

    class User {
        // 模拟内存占用3M，以更好观察gc前后的内存变化
        private byte[] memory = new byte[3*1024*1024];
    }

    public void testWeakReference(){
        User user = new User();
        WeakReference<User> ret = new WeakReference<>(user);
        System.out.println("GC前： " + ret.get());
        user = null;
        System.gc();
        System.out.println("GC后： " + ret.get());
    }

    public static void main(String[] args) {
        TestWeakReference testWeakReference = new TestWeakReference();
        testWeakReference.testWeakReference();
    }
}
