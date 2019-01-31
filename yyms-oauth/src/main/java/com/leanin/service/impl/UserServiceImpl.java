package com.leanin.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leanin.domain.dto.AddUserDto;
import com.leanin.domain.dto.AdminUserDto;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.AdminUserVo;
import com.leanin.mapper.UserMapper;
import com.leanin.service.UserService;
import com.leanin.utils.CompareUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户Service实现类
 * @author Administrator
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public DataOutResponse findUserById(Long userId) {
        return ReturnFomart.retParam(200,userMapper.findUserById(userId));
    }

    @Override
    public DataOutResponse findUserList(int page,int pageSize,String userCode) {
        log.info("用户工号为:"+userCode);
        PageHelper.startPage(page, pageSize);
        List<AdminUserDto> userList=userMapper.findUserList(userCode);
        log.info("用户的列表:"+ JSON.toJSONString(userList));
        PageInfo pageInfo=new PageInfo<>(userList);
        return ReturnFomart.retParam(200,pageInfo);
    }

    @Override
    @Transactional
    public DataOutResponse addUserInfo(AddUserDto user) {
        log.info("添加的用户信息为:"+JSON.toJSONString(user));
        AdminUserDto userInfo=userMapper.findUserByUserCode(user.getWorkNum());
        if(CompareUtil.isNotEmpty(userInfo)) {
            return ReturnFomart.retParam(4000,userInfo);
        }
        AdminUserVo userVo=new AdminUserVo();
        userVo.setAdminName(user.getAdminName());
        userVo.setAdminDesc(user.getAdminDesc());
        userVo.setAreaCode(user.getAreaCode());
        userVo.setBirthday(user.getBirthday());
        userVo.setIdCard(user.getIdCard());
        userVo.setJobTitle(user.getJobTitle());
        userVo.setOrganAscri(user.getOrganAscri());
        userVo.setPassword(user.getPassword());
        userVo.setPhone(user.getPhone());
        userVo.setRemark(user.getRemark());
        userVo.setSex(user.getSex());
        userVo.setWardCode(user.getWardCode());
        userVo.setWorkNum(user.getWorkNum());
        userVo.setAdminState(0);
        userMapper.addUserInfo(userVo);
        //保存该用户的角色信息
        String ri[]=user.getRoleId().split(",");
        for(int i=0;i<ri.length;i++) {
            userMapper.addUserRole(userVo.getAdminId(),Long.parseLong(ri[i]));
        }
        return ReturnFomart.retParam(200,user);
    }

    @Override
    @Transactional
    public DataOutResponse updateUserInfo(AddUserDto user) {
        log.info("修改的用户信息为:"+JSON.toJSONString(user));
        AdminUserVo userVo=new AdminUserVo();
        userVo.setAdminName(user.getAdminName());
        userVo.setAdminDesc(user.getAdminDesc());
        userVo.setAreaCode(user.getAreaCode());
        userVo.setBirthday(user.getBirthday());
        userVo.setIdCard(user.getIdCard());
        userVo.setJobTitle(user.getJobTitle());
        userVo.setOrganAscri(user.getOrganAscri());
        userVo.setPassword(user.getPassword());
        userVo.setPhone(user.getPhone());
        userVo.setRemark(user.getRemark());
        userVo.setSex(user.getSex());
        userVo.setWardCode(user.getWardCode());
        userVo.setWorkNum(user.getWorkNum());
        userVo.setAdminState(0);
        userMapper.updateUserInfo(userVo);
        //保存该用户的角色信息
        String ri[]=user.getRoleId().split(",");
        for (int i=0;i<ri.length;i++) {
            userMapper.updateUserRole(userVo.getAdminId(),Long.parseLong(ri[i]));
        }
        return ReturnFomart.retParam(200,user);
    }
}
