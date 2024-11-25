package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;

@Mapper
public interface CategoryMapper {
    //private Long id;
    //
    ////类型: 1菜品分类 2套餐分类
    //private Integer type;
    //
    ////分类名称
    //private String name;
    //
    ////顺序
    //private Integer sort;
    //
    ////分类状态 0标识禁用 1表示启用
    //private Integer status;
    //
    ////创建时间
    //private LocalDateTime createTime;
    //
    ////更新时间
    //private LocalDateTime updateTime;
    //
    ////创建人
    //private Long createUser;
    //
    ////修改人
    //private Long updateUser;
    @Select("insert into category (type,name,sort,status,create_time,update_time,create_user,update_user) values (#{type},#{name},#{sort},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void insert(Category category);

    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);
}
