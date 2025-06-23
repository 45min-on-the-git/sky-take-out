package com.sky.controller.admin;


import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import com.sky.service.EmployeeService;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类管理
 */
@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "分类管理")
public class CategoryController {
    //service层
    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "新增分类")
    @PostMapping
    public Result<String> save(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增分类：{}", categoryDTO);
        categoryService.save(categoryDTO);
        return Result.success();
    }

    @ApiOperation(value = "根据id删除分类")
    @DeleteMapping
    public Result<String> deleteById(Long id) {
        log.info("即将删除：{}", id);
        categoryService.deleteById(id);
        return Result.success();
    }

    @ApiOperation(value = "修改分类")
    @PutMapping()
    public Result<String> update(@RequestBody CategoryDTO categoryDTO) {
        categoryService.update(categoryDTO);
        return Result.success();
    }

    @ApiOperation(value = "启用禁用分类")
    @PostMapping("/status/{status}")
    //前面是修改成什么状态，后面是要修改的id
    public Result<String> startOrStop(Long id, @PathVariable Integer status) {
        log.info("修改id：{}，修改状态{}", id, status);
        categoryService.startOrStop(status, id);
        return Result.success();
    }

    @ApiOperation(value = "分类分页查询")
    @GetMapping("/page")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("分类分页查询：{}", categoryPageQueryDTO);
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    //TODO:该接口疑似并不适用，根据分类查询的功能使用的是分类分页查询
    @ApiOperation(value = "根据类型查询分类")
    @GetMapping("/list")
    public Result<List<Category>> selectByType(Integer type) {
        log.info("需要查找的类型为：{}", type);
        List<Category> list = categoryService.list(type);
        return Result.success(list);
    }

}
