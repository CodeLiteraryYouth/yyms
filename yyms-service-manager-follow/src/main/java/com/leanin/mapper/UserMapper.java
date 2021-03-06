package com.leanin.mapper;

import com.leanin.domain.dto.AdminUserDto;
import com.leanin.domain.vo.AdminUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    AdminUserDto findUserByWorkNum(@Param("workNum") String workNum);


    void addUser(@Param("adminUserVo") AdminUserVo adminUserVo);

    AdminUserVo findUserId(@Param("adminUserId") Long adminUserId);

    void updateUser(@Param("adminUserVo") AdminUserVo adminUserVo);

    List<AdminUserVo> findUserPage(@Param("userName") String userName, @Param("workNum") String workNum);

    String findUserName(@Param("adminId") Long adminId);
}
