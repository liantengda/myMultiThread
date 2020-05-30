package com.lian.mycollection.common.myReference;

/**
 *
 * 强引用是指创建一个对象并它赋值给一个引用，引用是存在JVM中的栈（还有方法区)中的。具有强引用的对象，垃圾回收器绝对不会去回收它，
 * 直到内存不足以分配时，抛出OOM。
 * 大多数情况，我们new一个对象，并把它赋值给一个变量，这个变量就是强引用。
 *
 * @author Ted
 * @version 1.0
 * @date 2020/5/30 10:48
 */
public class TestFinalReference {

    //方法区中的类静态属性引用的对象
    private static Object finalRet1 = new Object();
    //方法区中的常量引用的对象
    private static final Object finalRet2 = new Object();

    void methodA(){
        //栈上的局部变量引用的对象
        Object finalRet3 = new Object();
    }
    //JNI中引用的对象
    native void methodB();
}
