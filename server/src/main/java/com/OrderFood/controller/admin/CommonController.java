package com.OrderFood.controller.admin;


import com.OrderFood.constant.MessageConstant;
import com.OrderFood.properties.AliOssProperties;
import com.OrderFood.result.Result;
import com.OrderFood.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;


/**
 * 通用接口
 */
@RestController
@RequestMapping("/admin/common")
@Slf4j
@Api(tags = "通用接口")
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    @Autowired
    private AliOssProperties aliOssProperties;

    /**
     * 文件上传
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(@RequestBody MultipartFile file){
        log.info("文件上传：{}", file);

        try {
            //  原始文件名
            val originalFilename = file.getOriginalFilename();
            //  获取后缀
            val suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            //
            val fileName = UUID.randomUUID().toString() + suffix;

            val filePath = aliOssUtil.upload(file.getBytes(), fileName);
            return Result.success(filePath);
        } catch (IOException e) {
            log.info("文件上传失败：{}", e);
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
