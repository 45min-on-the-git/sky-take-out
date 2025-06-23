package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;

@Slf4j
@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Override
    //@Transient可以保证该方法中的操作是原子性的，相当于事务
    @Transient
    public void saveDishAndFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        //像菜品表插入数据
        dishMapper.insert(dish);

        //获取数据库自增的主键值
        Long dishId = dish.getId();

        //像口味表插入数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {
            //将上面获取到的数据库中的dishId赋值给口味列表
            for (DishFlavor flavor : flavors) {
                flavor.setDishId(dishId);
            }
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        //这里使用dishvo而不用dish的原因在于dishvo=dish+category
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        long total = page.getTotal();
        List<DishVO> result = page.getResult();
        PageResult pageResult = new PageResult();
        pageResult.setTotal(total);
        pageResult.setRecords(result);
        return pageResult;
    }

    @Override
    public void deleteBatch(List<Long> ids) {
        //批量删除的菜品存在List中
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            //启售中的菜品需要先停售在删除
            if (dish.getStatus() == StatusConstant.ENABLE) {
                return;
            }

            //套餐关联的商品不能删除

        }
        //删除菜品后需要把口味也删除

    }

}
