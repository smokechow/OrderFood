package com.OrderFood;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement    //  开启注解方式的事务管理
@EnableCaching  //  开启缓存注解功能
@EnableScheduling //    开启spring task定时任务调度功能
//@MapperScan("com.baomidou.mybatisplus.samples.quickstart.mapper")
@Slf4j
public class OrderFoodApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderFoodApplication.class, args);
        log.info("server started");
    }
}
