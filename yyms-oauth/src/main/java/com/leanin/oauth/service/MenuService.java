package com.leanin.oauth.service;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.MenuPermissionVo;

import java.util.List;

public interface MenuService {

    DataOutResponse addMenu(MenuPermissionVo menu);

    DataOutResponse delMenu(String ids);

    DataOutResponse updateMenu(MenuPermissionVo menu);

    DataOutResponse findByPid(Integer pid);

    List<MenuPermissionVo> findMenuListByUserId(Long userId);
}
