package com.OrderFood.annotation;

import com.OrderFood.enumeration.OperationType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解，用于标识某个方法需要进行自动填充
 */
//  这个注解用于方法上
@Target(ElementType.METHOD)
//  持续时间
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    //  数据库操作类型
    OperationType value();
}
