package com.OrderFood.controller.admin;

import com.OrderFood.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * 店铺状态管理
 */

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Slf4j
@Api(tags = "店铺操作接口")
public class ShopController {

    public static final String KEY = "SHOP_STATUS";

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 设置营业状态
     * @param status
     * @return
     */
    @PutMapping("/{status}")
    @ApiOperation("设置营业状态")
    public Result setShopStatus(@PathVariable Integer status){
        log.info("设置营业状态：{}",status == 1 ?"营业中":"打烊中");

        redisTemplate.opsForValue().set("SHOP_STATUS",status);

        return Result.success();
    }

    /**
     * 获取营业状态
     * @return
     */
    @GetMapping("status")
    @ApiOperation("获取营业状态")
    public Result getShopStatus(){
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
        log.info("店铺状态为：{}",status == 1 ?"营业中":"打烊中");
        return Result.success(status);
    }
}
