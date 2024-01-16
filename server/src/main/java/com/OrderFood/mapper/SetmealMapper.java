package com.OrderFood.mapper;

import com.OrderFood.annotation.AutoFill;
import com.OrderFood.dto.SetmealPageQueryDTO;
import com.OrderFood.entity.Setmeal;
import com.OrderFood.enumeration.OperationType;
import com.OrderFood.vo.SetmealVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     * @param id
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    /**
     * 根据分类id查询套餐
     * @param categoryId
     * @return
     */
    @Select("select * from setmeal where category_id = #{categoryId} and status = 1")
    List<Setmeal> getByCategoryId(Long categoryId);

    /**
     * 根据菜品id获取套餐
     * @param dishId
     * @return
     */
    @Select("select * from setmeal_dish where dish_id = #{dishId}")
    List<Setmeal> getByDishId(Long dishId);

    /**
     * 新增套餐
     * @param setmeal
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Setmeal setmeal);

    /**
     * 套餐分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    Page<SetmealVO> page(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 根据id查询套餐
     * @param setmealId
     * @return
     */
    @Select("select * from setmeal where id = #{setmealId}")
    Setmeal getById(Long setmealId);

    /**
     * 删除套餐
     * @param setmealId
     */
    @Delete("delete from setmeal where id = #{setmealId} ")
    void delete(Long setmealId);

    /**
     * 更新套餐
     * @param setmeal
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Setmeal setmeal);


    Integer countByMap(Map map);
}
