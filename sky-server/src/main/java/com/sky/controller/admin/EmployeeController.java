package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
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
@Api(tags = "员工登录退出的接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @ApiOperation(value = "员工登录")
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);
        //这里调用service层的登录逻辑代码，是否成功由service层自行判断
        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌--这里需要单独学习一下
        Map<String, Object> claims = new HashMap<>();
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
     * 退出
     *
     * @return
     */
    @ApiOperation(value = "员工退出")
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }


    @PostMapping()
    @ApiOperation(value = "新增员工")
    //如果前端返回的数据是json格式就要加@RequestBody注解
    public Result<String> save(@RequestBody EmployeeDTO employeeDTO) {
        log.info("新增员工：{}",employeeDTO);
        employeeService.save(employeeDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation(value = "员工分页查询")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("员工分页查询，参数为：{}",employeePageQueryDTO);
        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping("/status/{status}")
    @ApiOperation(value = "启用停用员工")
    //注意，该接口前面的status使用地址传参，因此需要@PathVariable注解，而后面的status是id又由get方法用&拼接在地址的后面，因此不需要注解
    public Result startOrStop(@PathVariable Integer status,Long id) {
        log.info("启停种类：{}，启停账号：{}",status,id);
        employeeService.startOrStop(status,id);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "数据回显")
    public Result<Employee> getById(@PathVariable Long id) {
        Employee employee =  employeeService.getById(id);
        return Result.success(employee);
    }

    //这里和新增员工一样都没有加路径，但却可以区分两个请求的原因是一个是post方法，一个是put方法
    //这里需要前端确定传回的数据均不为空，否则为空的地方会被替换成空白
    @PutMapping()
    @ApiOperation(value = "修改员工信息")
    public Result update(@RequestBody EmployeeDTO employeeDTO){
        employeeService.update(employeeDTO);
        return Result.success();
    }
}
