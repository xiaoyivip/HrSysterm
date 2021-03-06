package cn.edu.hzvtc.dao;

import cn.edu.hzvtc.entity.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_employee
     *
     * @mbg.generated Tue Jun 08 12:53:44 CST 2021
     */
    int insert(Employee record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_employee
     *
     * @mbg.generated Tue Jun 08 12:53:44 CST 2021
     */
    List<Employee> selectAll();

    List<Employee> selectAllWithDept();

    Employee selectByPrimaryKey(Integer empId);

    int deleteByPrimaryKey(Integer empId);

    List<Employee> selectByIdList(@Param("ids") List<Integer> ids);

    int deleteByIdList(@Param("ids")List<Integer> ids);

    List<Employee> selectByEmpName(@Param("empName") String empName);

    Integer updateByPrimaryKey(Employee employee);
}