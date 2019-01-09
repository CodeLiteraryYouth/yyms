package com.leanin.mapper;

import com.leanin.domain.vo.AdminUserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Administrator
 */
public interface AdminUserMapper {

	/**
	 * 增加管理员用户
	 * @param adminUser
	 * @return
	 */
    int insertAdminUser(AdminUserVo adminUser);

    /**
     * 修改管理员用户
     * @param adminUser
     * @return
     */
    int updateAdminUser(AdminUserVo adminUser);
    
    /**
     * 查询管理员用户列表
     * @param userCode
     * @return
     */
    List<AdminUserVo> findUserinfo(@Param("userCode") String userCode);
    
    /**
     * 注销管理员用户
     * @param adminId
     * @return
     */
    int updateUserState(Long adminId);
    
    /**
     * 根据用户手机号查询用户
     * @param phone
     * @return
     */
    AdminUserVo findAdminUserByPhone(String phone);
}