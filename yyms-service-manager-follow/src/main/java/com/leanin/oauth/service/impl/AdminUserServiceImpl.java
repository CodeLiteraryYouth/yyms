package com.leanin.oauth.service.impl;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.AdminUserVo;
import com.leanin.mapper.AdminUserMapper;
import com.leanin.oauth.service.AdminUserService;
import com.leanin.utils.CompareUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * AdminUserServiceImpl class
 * 
 * @author zd
 * @date 2018/12/24
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {

	@Autowired
	private AdminUserMapper adminMapper;
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse insertAdminUser(AdminUserVo adminUser) {
		AdminUserVo admin=adminMapper.findAdminUserByPhone(adminUser.getPhone());
		if(CompareUtil.isEmpty(admin)) {
			return ReturnFomart.retParam(1011, admin);
		}
		adminMapper.insertAdminUser(adminUser);
		return ReturnFomart.retParam(200, adminUser);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse updateAdminUser(AdminUserVo adminUser) {
		adminMapper.updateAdminUser(adminUser);
		return ReturnFomart.retParam(200, adminUser);
	}

	@Override
	public DataOutResponse findUserinfo(String userCode) {
		return ReturnFomart.retParam(200,adminMapper.findUserinfo(userCode));
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public DataOutResponse updateUserState(Long adminId) {
		adminMapper.updateUserState(adminId);
		return ReturnFomart.retParam(200, adminId);
	}

}
