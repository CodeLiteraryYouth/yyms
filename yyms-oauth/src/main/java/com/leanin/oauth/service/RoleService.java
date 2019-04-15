package com.leanin.oauth.service;

import com.leanin.domain.request.RoleInfoVoReq;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.RoleInfoVo;

import javax.servlet.http.HttpServletRequest;

public interface RoleService {

    DataOutResponse addRole(RoleInfoVoReq RoleInfoVoReq, HttpServletRequest request);

    DataOutResponse delRole(String roleIds);

    DataOutResponse updateRole(RoleInfoVoReq roleInfoVoReq);

    DataOutResponse findRoleById(Long roleId);

    DataOutResponse findRolePage(Integer currentPage, Integer pageSize, String roleName);

}
