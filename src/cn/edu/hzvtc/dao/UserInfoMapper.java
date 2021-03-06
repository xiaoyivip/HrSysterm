package cn.edu.hzvtc.dao;

import cn.edu.hzvtc.entity.Employee;
import cn.edu.hzvtc.entity.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_employee
     *
     * @mbg.generated Tue Jun 08 12:53:44 CST 2021
     */
    int insert(UserInfo userInfo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_employee
     *
     * @mbg.generated Tue Jun 08 12:53:44 CST 2021
     */
    List<UserInfo> selectAll();

    List<UserInfo> selectAllWithDept();

    Employee selectByPrimaryKey(Integer empId);

    int deleteByPrimaryKey(Integer empId);

    List<UserInfo> selectByIdList(@Param("ids") List<Integer> ids);

    int deleteByIdList(@Param("ids") List<Integer> ids);

    List<UserInfo> selectByuserName(@Param("userName") String userName);

    Integer updatePasswordByTelephone(@Param("userPassword")String userPassword,@Param("userTelephone")String userTelephone);

    /**
     * 查询用户名和密码
     * @param userName
     * @return
     */
    UserInfo selectByuserAndPassword(@Param("userName") String userName, @Param("userPassword") String userPassword);

    /**
     * 查询手机号 用于判断是否可以修改密码
     * @param userTelephone
     * @return
     */
    List<UserInfo> selectByuserTelephone(@Param("userTelephone") String userTelephone);


}