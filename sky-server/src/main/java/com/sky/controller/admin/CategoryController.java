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
        log.info("新增分类：{}",categoryDTO);
        categoryService.save(categoryDTO);
        return Result.success();
    }

    //TODO:不确定这个接口里的传参对不对，需要看一下老师的
    @ApiOperation(value = "根据id删除分类")
    @DeleteMapping("/{id}")
    public Result xxxmethod2() {
        return Result.success();
    }

    @ApiOperation(value = "修改分类")
    @PutMapping()
    public Result xxxmethod3() {
        return Result.success();
    }

    @ApiOperation(value = "启用禁用分类")
    @PostMapping("/status/{status}")
    public Result xxxmethod4() {
        return Result.success();
    }

    @ApiOperation(value = "分类分页查询")
    @GetMapping("/page")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    @ApiOperation(value = "根据类型查询分类")
    @GetMapping("/list")
    public Result<List<Category>> selectByType(Integer type) {
        List<Category> list = categoryService.lst(type);
        return Result.success(list);
    }

}
