package com.OrderFood.aspect;

import com.OrderFood.annotation.AutoFill;
import com.OrderFood.constant.AutoFillConstant;
import com.OrderFood.context.BaseContext;
import com.OrderFood.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

/**
 * 自定义切面，统一拦截加入了AutoFill注解的方法
 */
@Aspect
@Slf4j
@Component
public class AutoFillAspect {
    /**
     * 切入点
     */
    @Pointcut("execution(* com.OrderFood.mapper.*.*(..))&& @annotation(com.OrderFood.annotation.AutoFill)")
    public void autoFillPointCut(){}

    /**
     * 前置通知
     * 在autoFillPointCut这个切入点之前通知
     * 在有AutoFill注解的方法执行之前，进行某个操作
     */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {//   连结点提供被拦截的方法和这个方法的参数
        log.info("开始进行公共字段填充...");

        //  获服到当前被拦截的方法上的数据库操作类型INSERT还是UPDATE
        val signature = (MethodSignature)joinPoint.getSignature();  // 连结点的签名 再将该签名对象转换成方法签名对象 因为被拦截的是方法
        val annotation = signature.getMethod().getAnnotation(AutoFill.class);   // 获取方法上的注解
        val operationType = annotation.value(); //数据库操作类型

        //  获取到当前被拦截的方法的参数--实体对象
        val args = joinPoint.getArgs();
        if(args == null || args.length ==0){
            return;
        }
        val Object = args[0];

        //  准备赋值的数据
        val now = LocalDateTime.now();
        val currentId = BaseContext.getCurrentId();

        //  根据当前不同的操作类型，通过反射为对应的属性赋值
        if (operationType == OperationType.INSERT) {
            //  无法明确知道Object是什么类，也就无法使用它的方法
            //  通过反射
            val setCreateTime = Object.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
            val setCreateUser = Object.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
            val setUpdateTime = Object.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
            val setUpdateUser = Object.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

            //  用得到的方法为Object的属性赋值
            setCreateTime.invoke(Object,now);
            setCreateUser.invoke(Object,currentId);
            setUpdateTime.invoke(Object,now);
            setUpdateUser.invoke(Object,currentId);
        } else if (operationType == OperationType.UPDATE) {
            val setUpdateTime = Object.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
            val setUpdateUser = Object.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
            setUpdateTime.invoke(Object,now);
            setUpdateUser.invoke(Object,currentId);
        }
    }
}
