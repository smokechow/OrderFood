package com.OrderFood.controller.admin;

import com.OrderFood.constant.JwtClaimsConstant;
import com.OrderFood.dto.EmployeeDTO;
import com.OrderFood.dto.EmployeeLoginDTO;
import com.OrderFood.dto.EmployeePageQueryDTO;
import com.OrderFood.entity.Employee;
import com.OrderFood.properties.JwtProperties;
import com.OrderFood.result.PageResult;
import com.OrderFood.result.Result;
import com.OrderFood.service.EmployeeService;
import com.OrderFood.utils.JwtUtil;
import com.OrderFood.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工相关接口") //Swagger相关声明
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     */
    @PostMapping("/login")
    @ApiOperation("员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //  登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        //  <"empId",1>
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 员工登出
     *
     */
    @PostMapping("/logout")
    @ApiOperation("员工登出")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 新增员工
     *
     * @param employeeDTO
     */
    @PostMapping
    @ApiOperation("新增员工")
    public Result AddNew(@RequestBody EmployeeDTO employeeDTO){
        log.info("新增员工：{}", employeeDTO);

        employeeService.addNew(employeeDTO);

        return Result.success();
    }

    /**
     * 分页查询
     * @param employeePageQueryDTO
     */
    @PostMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> page(@RequestBody EmployeePageQueryDTO employeePageQueryDTO){
        log.info("分页查询：{}", employeePageQueryDTO);

        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);

        return Result.success(pageResult);
    }

    /**
     * 启用禁用员工账号
     * @param status
     * @param id
     */
    @PostMapping("/status/{status}")
    @ApiOperation("启用禁用员工账号")
    public Result startOrStop(@PathVariable Integer status, Long id) {
        log.info("启用禁用员工账号：{},{}", status, id);

        employeeService.startOrStop(status, id);

        return Result.success();
    }

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询员工信息")
    public Result<Employee> getById(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        employee.setPassword("*****");

        return Result.success(employee);
    }

    /**
     * 编辑员工信息
     * @param employeeDTO
     * @return
     */
    @PutMapping
    @ApiOperation("编辑员工信息")
    public Result update(@RequestBody EmployeeDTO employeeDTO){
        log.info("编辑员工信息：{}", employeeDTO);

        employeeService.update(employeeDTO);
        return Result.success();
    }
}
