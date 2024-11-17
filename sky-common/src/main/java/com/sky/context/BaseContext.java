package com.sky.context;

public class BaseContext {
    //根据每次请求都会使用同一个线程的特性，该本地线程变量可以保证在一个线程里面是通用的，并且不会存储到下一次请求
    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }

    public static void removeCurrentId() {
        threadLocal.remove();
    }

}
