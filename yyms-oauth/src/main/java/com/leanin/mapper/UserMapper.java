package com.leanin.mapper;

import com.leanin.domain.dto.AdminUserDto;
import com.leanin.domain.vo.AdminUserVo;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;

/**
 * 用户mapper
 * @author Administrator
 */
public interface UserMapper {

    /**
     * 根据用户工号查询用户信息
     * @param userCode
     * @return
     */
    AdminUserDto findUserByUserCode(@Param("userCode") String userCode);
    /**
     * 根绝UserId查询用户信息
     * @param userId
     * @return
     */
    AdminUserDto findUserById(@Param("userId") Long userId);

    /**
     * 根据用户工号查询用户列表
     * @param userCode
     * @return
     */
    List<AdminUserDto> findUserList(@Param("userCode") String userCode);

    /**
     * 添加用户信息
     * @param user
     * @return
     */
    int addUserInfo(AdminUserVo user);

    /**
     * 增加用户角色中间表信息
     * @param userId
     * @param roleId
     * @return
     */
    int addUserRole(Long userId,Long roleId);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    int updateUserInfo(AdminUserVo user);

    /**
     * 修改用户角色信息
     * @param userId
     * @param roleId
     * @return
     */
    int updateUserRole(Long userId,Long roleId);
}
