package com.leanin.oauth.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.leanin.domain.request.RoleInfoVoReq;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.response.RoleInfoReP;
import com.leanin.domain.vo.RoleInfoVo;
import com.leanin.domain.vo.RoleMenuInfoVo;
import com.leanin.oauth.mapper.RoleMapper;
import com.leanin.oauth.repository.RoleInfoRepository;
import com.leanin.oauth.repository.RoleMenuRepository;
import com.leanin.oauth.service.RoleService;
import com.leanin.utils.LyOauth2Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    RoleInfoRepository roleInfoRepository;

    @Autowired
    RoleMenuRepository roleMenuRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse addRole(RoleInfoVoReq roleInfoVoReq, HttpServletRequest request) {
        LyOauth2Util.UserJwt userJwt = getUser(request);
        roleInfoVoReq.setRoleCreater(userJwt.getId());
        RoleInfoVo roleInfoVo = new RoleInfoVo();
        BeanUtils.copyProperties(roleInfoVoReq,roleInfoVo);
        RoleInfoVo roleInfo = roleInfoRepository.save(roleInfoVo);
        log.info("添加角色{}", JSON.toJSONString(roleInfo));

        RoleMenuInfoVo roleMenuInfoVo = new RoleMenuInfoVo();
        if (roleInfoVoReq.getMenuIds().size() > 0){
            for (Long menuId : roleInfoVoReq.getMenuIds()) {
                roleMenuInfoVo.setId(null);
                roleMenuInfoVo.setRoleId(roleInfo.getRoleId());
                roleMenuInfoVo.setPermissionId(menuId);
                roleMenuInfoVo.setCreateTime(new Date());
                RoleMenuInfoVo save = roleMenuRepository.save(roleMenuInfoVo);
                log.info("添加角色权限关联表{}", JSON.toJSONString(save));
            }
        }
        return ReturnFomart.retParam(200,roleInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse delRole(String roleIdStr) {

        String[] roleIds = roleIdStr.split(",");
        for (String roleId : roleIds) {
            long id = Long.parseLong(roleId);
            RoleInfoVo roleInfoVo = roleInfoRepository.findByRoleIdAndAndRoleStatus(id, 1);
            if (roleInfoVo == null){
                log.info("删除的角色不存在{}", roleId);
                return ReturnFomart.retParam(3201,roleId);
            }
            roleInfoVo.setRoleStatus(2);
            roleInfoRepository.save(roleInfoVo);
        }
        log.info("删除角色的id是{}", roleIdStr);
        return ReturnFomart.retParam(200,"删除成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse updateRole(RoleInfoVoReq roleInfoVoReq) {
//        RoleInfoVo role = roleInfoRepository.findByRoleIdAndAndRoleStatus(roleInfoVo.getRoleId(), 1);
        RoleInfoReP roleInfoReP = roleMapper.findRoleId(roleInfoVoReq.getRoleId());
        if (roleInfoReP == null){
            log.error("角色不存在{}",JSON.toJSONString(roleInfoVoReq));
            return ReturnFomart.retParam(3201,roleInfoVoReq);
        }
        RoleInfoVo roleInfoVo = new RoleInfoVo();
        BeanUtils.copyProperties(roleInfoVoReq,roleInfoVo);
        RoleInfoVo save = roleInfoRepository.save(roleInfoVo);

        for (Long menuId : roleInfoVoReq.getMenuIds()) {

        }
        log.info("修改成功:{}",JSON.toJSONString(save));
        return ReturnFomart.retParam(200,save);
    }

    @Override
    public DataOutResponse findRoleById(Long roleId) {
        RoleInfoVo roleInfoVo = roleInfoRepository.findByRoleIdAndAndRoleStatus(roleId, 1);
        return ReturnFomart.retParam(200,roleInfoVo);
    }

    @Override
    public DataOutResponse findRolePage(Integer currentPage, Integer pageSize, String roleName) {
        if (currentPage == null || currentPage < 1){
            currentPage = 1;
        }
        if (pageSize == null || pageSize < 1){
            pageSize = 10;
        }
        PageHelper.startPage(currentPage,pageSize);
//        roleInfoRepository
        return null;
    }

    private LyOauth2Util.UserJwt getUser(HttpServletRequest request){
        LyOauth2Util util =new LyOauth2Util();
        LyOauth2Util.UserJwt userJwt = util.getUserJwtFromHeader(request);
        return userJwt;
    }
}
