package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);
    //id
    //name
    //username
    //password
    //phone
    //sex
    //id_number
    //status
    //create_time
    //update_time
    //create_user
    //update_user

    //    private Long id;
    //
    //    private String username;
    //
    //    private String name;
    //
    //    private String password;
    //
    //    private String phone;
    //
    //    private String sex;
    //
    //    private String idNumber;
    //
    //    private Integer status;
    //
    //    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //    private LocalDateTime createTime;
    //
    //    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //    private LocalDateTime updateTime;
    //
    //    private Long createUser;
    //
    //    private Long updateUser;
    @Select("insert into employee (name,username,password,phone,sex,id_number,status,create_time,update_time,create_user,update_user) " +
            "values (#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void insert(Employee employee);

    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    void update(Employee employee);

    @Select("select * from employee where id = #{id}")
    Employee getById(Long id);
}
