package com.leanin.oauth.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leanin.domain.dto.AddRoleDto;
import com.leanin.domain.dto.RoleInfoDto;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.RoleInfoVo;
import com.leanin.oauth.mapper.RoleMapper;
import com.leanin.oauth.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public DataOutResponse findRoleList(int page,int pageSize,String roleName) {
        log.info("搜索的角色名称为:"+roleName);
        PageHelper.startPage(page,pageSize);
        List<RoleInfoDto> roleList=roleMapper.findRoleList(roleName);
        PageInfo pageInfo=new PageInfo<>(roleList);
        return ReturnFomart.retParam(200,pageInfo);
    }

    @Override
    public DataOutResponse findRoleById(Long roleId) {
        return ReturnFomart.retParam(200,roleMapper.findRoleById(roleId));
    }

    @Override
    @Transactional
    public DataOutResponse addRoleInfo(AddRoleDto role) {
        log.info("增加的角色信息为:"+ JSON.toJSONString(role));
        RoleInfoVo roleVo=new RoleInfoVo();
        roleVo.setHospitalAreaCode(role.getHospitalAreaCode());
        roleVo.setRoleCreater(role.getRoleCreater());
        roleVo.setRoleCreateTime(role.getRoleCreateTime());
        roleVo.setRoleDesc(role.getRoleDesc());
        roleVo.setRoleName(role.getRoleName());
        roleVo.setRoleRemark(role.getRoleRemark());
        roleVo.setRoleStatus(0);
        roleMapper.addRoleInfo(roleVo);
        String mi[]=role.getMenuId().split(",");
        for(int i=0;i<mi.length;i++) {
            roleMapper.addRoleMenu(roleVo.getRoleId(),Long.parseLong(mi[i]));
        }
        return ReturnFomart.retParam(200,role);
    }

    @Override
    @Transactional
    public DataOutResponse updateRoleInfo(AddRoleDto role) {
        log.info("修改的角色信息为:"+JSON.toJSONString(role));
        RoleInfoVo roleVo=new RoleInfoVo();
        roleVo.setHospitalAreaCode(role.getHospitalAreaCode());
        roleVo.setRoleCreater(role.getRoleCreater());
        roleVo.setRoleCreateTime(role.getRoleCreateTime());
        roleVo.setRoleDesc(role.getRoleDesc());
        roleVo.setRoleName(role.getRoleName());
        roleVo.setRoleRemark(role.getRoleRemark());
        roleVo.setRoleStatus(role.getRoleStatus());
        roleVo.setRoleId(role.getRoleId());
        roleMapper.updateRoleInfo(roleVo);
        String mi[]=role.getMenuId().split(",");
        for(int i=0;i<mi.length;i++) {
            roleMapper.updateRoleMenu(role.getRoleId(),Long.parseLong(mi[i]));
        }
        return ReturnFomart.retParam(200,role);
    }
}
