package com.OrderFood.mapper;

import com.OrderFood.annotation.AutoFill;
import com.OrderFood.dto.EmployeePageQueryDTO;
import com.OrderFood.entity.Employee;
import com.OrderFood.enumeration.OperationType;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 新增员工
     * @param #{name}, #{username}, #{password}, #{phone},#{sex}, #{idNumber},#{status}, #{createTime}, #{updateTime},#{createUser},#{updateUser}
     */
    @AutoFill(value = OperationType.INSERT)
    @Insert("insert into employee (name, username, password, phone, sex, id_number, status, create_time, update_time, create_user, update_user) " +
            "VALUE (#{name}, #{username}, #{password}, #{phone},#{sex}, #{idNumber},#{status}, #{createTime}, #{updateTime},#{createUser},#{updateUser}) ")
    Integer insert(Employee employee);

    /**
     * 根据用户名查询员工
     * @param employeePageQueryDTO
     */
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 修改员工信息
     * @param employee
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Employee employee);

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    @Select("select * from employee where id = #{id}")
    Employee getById(Long id);
}
