package com.OrderFood.service.impl;

import com.OrderFood.constant.MessageConstant;
import com.OrderFood.constant.StatusConstant;
import com.OrderFood.context.BaseContext;
import com.OrderFood.dto.EmployeeDTO;
import com.OrderFood.dto.EmployeeLoginDTO;
import com.OrderFood.dto.EmployeePageQueryDTO;
import com.OrderFood.entity.Employee;
import com.OrderFood.exception.AccountLockedException;
import com.OrderFood.exception.AccountNotFoundException;
import com.OrderFood.exception.PasswordErrorException;
import com.OrderFood.mapper.EmployeeMapper;
import com.OrderFood.result.PageResult;
import com.OrderFood.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        //将密码进行MD5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /**
     * 新增员工
     *
     * @param employeeDTO
     */
    public void addNew(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        //  复制相同属性
        BeanUtils.copyProperties(employeeDTO,employee);
        //  员工状态 默认=1
        employee.setStatus(StatusConstant.ENABLE);
        // 设置默认密码=123456
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        //  成功1 失败0
        val insert = employeeMapper.insert(employee);

        //  如果数据库新增行失败
        if(insert==0){
            throw new RuntimeException("数据库插入失败");
        }

    }

    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return
     */
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        //  Mybatis分页插件，传入页数和分页大小
        PageHelper.startPage(employeePageQueryDTO.getPage(),employeePageQueryDTO.getPageSize());
        //  下一条sql进行分页，自动加入limit关键字分页
        //  PageHelper帮助处理字符串拼接 employeeMapper的pageQuery只管查询数据
        val employees = employeeMapper.pageQuery(employeePageQueryDTO);
        //  获取返回结果的total和List
        val total = employees.getTotal();
        val result = employees.getResult();
        //  生成PageResult传回给前端
        return new PageResult(total,result);
    }

    /**
     * 启用禁用员工账号
     * @param status
     * @param id
     */
    public void startOrStop(Integer status, Long id) {
        val employee = new Employee();
        employee.setId(id);
        employee.setStatus(status);
        employeeMapper.update(employee);
    }

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    public Employee getById(Long id) {

        val employee = employeeMapper.getById(id);

        return employee;
    }

    /**
     * 编辑员工信息
     * @param employeeDTO
     */
    @Override
    public void update(EmployeeDTO employeeDTO) {
        val employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        employeeMapper.update(employee);
    }


}
