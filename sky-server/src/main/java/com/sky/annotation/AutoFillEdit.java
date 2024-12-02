package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//表明注解应用的位置为方法
@Target(ElementType.METHOD)
//被修饰的注解可以在运行时被反射机制读取。
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFillEdit {
    OperationType value();
}
