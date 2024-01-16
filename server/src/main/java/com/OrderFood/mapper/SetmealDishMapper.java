package com.OrderFood.mapper;

import com.OrderFood.entity.SetmealDish;
import com.OrderFood.vo.DishItemVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * 新增套餐与菜品关系
     * @param setmealDishes
     */
    void insertBatch(List<SetmealDish> setmealDishes);

    /**
     * 根据套餐id删除套餐与菜品的关系
     * @param setmealId
     */
    @Delete("delete from setmeal_dish where setmeal_id = #{setmealId}")
    void delete(Long setmealId);

    /**
     * 根据DishId获取套餐与菜品的关系
     * @param DishId
     * @return
     */
    @Select("select * from setmeal_dish where dish_id =#{DishId}")
    List<SetmealDish> getSetmealDishesByDishId(Long DishId);

    /**
     * 根据id获取套餐与菜品的关系
     * @param setmealId
     * @return
     */
    @Select("select * from setmeal_dish where setmeal_id =#{setmealId}")
    List<SetmealDish> getSetmealDishesBySetmealId(Long setmealId);

    /**
     * 根据套餐id查询包含的菜品
     * @param id
     * @return
     */
    @Select("select sd.name,sd.copies,d.image,d.description from setmeal_dish sd left outer join dish d on d.id = sd.dish_id " +
            "where sd.setmeal_id = #{id}")
    List<DishItemVO> getDishesWithImage(Long id);
}
