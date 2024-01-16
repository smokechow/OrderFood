package com.OrderFood.service;


import com.OrderFood.dto.SetmealDTO;
import com.OrderFood.dto.SetmealPageQueryDTO;
import com.OrderFood.entity.Setmeal;
import com.OrderFood.result.PageResult;
import com.OrderFood.vo.DishItemVO;
import com.OrderFood.vo.SetmealVO;

import java.util.List;

/**
 * <p>
 * 套餐 服务类
 * </p>
 *
 * @author nkko
 * @since 2023-12-31
 */
public interface SetmealService {

    /**
     * 新增套餐
     * @param setmealDTO
     */
    void addNew(SetmealDTO setmealDTO);

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    SetmealVO getById(Long id);

    /**
     * 套餐分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult page(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 批量删除套餐
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * 修改套餐
     * @param setmealDTO
     */
    void updateWithDish(SetmealDTO setmealDTO);

    /**
     * 套餐起售、停售
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 根据id获取套餐与菜品的关系
     * @param categoryId
     * @return
     */
    List<Setmeal> getByCategoryId(Long categoryId);

    /**
     * 根据套餐id查询包含的菜品
     * @param id
     * @return
     */
    List<DishItemVO> getDishes(Long id);
}
