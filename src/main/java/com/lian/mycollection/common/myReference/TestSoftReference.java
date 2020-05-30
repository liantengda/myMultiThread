package com.lian.mycollection.common.myReference;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 软引用
 * @author Ted
 * @version 1.0
 * @date 2020/5/30 10:54
 */
public class TestSoftReference {
    class User {
        // 模拟内存占用3M，以更好观察gc前后的内存变化
        private byte[] memory = new byte[3*1024*1024];
    }
    /**
     * 测试弱引用在内存足够时不会被GC，在内存不足时才会被GC的特性
     * JVM参数 -Xms20m -Xmx20m -Xlog:gc  将内存大小限制在20M，并打印出GC日志
     */
    public void testSoftReference(){

        // 当仅使用强引用，脱离GC Root后将会被回收 （可以通过查看gc日志来确认该对象确实被回收）
        // 这是对照组
        User retA = new User();
        retA = null;
        System.gc();
        System.out.println("对照组GC后：" + retA);


        User retB = new User();
        // 创建弱引用类，将该引用绑定到弱引用对象上
        SoftReference<User> sortRet = new SoftReference<>(retB);
        retB = null;
        // 此时并不会被GC
        System.gc();
        retB = sortRet.get();
        System.out.println("GC后通过软引用重新获取了对象：" + retB);

        retB = null;

        // 模拟内存不足，即将发生OOM
        List<User> manyUsers = new ArrayList<>();
        for(int i = 1; i < 100000; i++){
            System.out.println("将要创建第" + i + "个对象");
            manyUsers.add(new User());
            System.out.println("创建第" + i + "个对象后, 软引用对象：" + sortRet.get());
        }
    }


    public static void main(String[] args) {
        TestSoftReference referenceTest = new TestSoftReference();
        referenceTest.testSoftReference();
    }
}
