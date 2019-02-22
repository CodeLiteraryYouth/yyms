package com.leanin.oauth.service;


import com.leanin.domain.dto.AddUserDto;
import com.leanin.domain.response.DataOutResponse;

/**
 * 用户的Service
 */
public interface UserService {

    /**
     * 根绝UserId查询用户信息
     * @param userId
     * @return
     */
    DataOutResponse findUserById(Long userId);

    /**
     * 根据用户工号查询用户列表
     * @param
     * @return
     */
    DataOutResponse findUserList(int page,int pageSize,String adminName,String adminWorkNum);

    /**
     * 添加用户信息
     * @param user
     * @return
     */
    DataOutResponse addUserInfo(AddUserDto user);


    /**
     * 修改用户信息
     * @param user
     * @return
     */
    DataOutResponse updateUserInfo(AddUserDto user);

}
