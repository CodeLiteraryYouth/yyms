package com.leanin.oauth.service.impl;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.MenuPermissionVo;
import com.leanin.oauth.repository.MenuRepository;
import com.leanin.oauth.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuRepository menuRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse addMenu(MenuPermissionVo menu) {
        MenuPermissionVo permissionVo = menuRepository.findByMenuNameAndMenuIsDelete(menu.getMenuName(), menu.getMenuIsDelete());
        if (permissionVo != null){
            return ReturnFomart.retParam(3001,permissionVo);
        }
        MenuPermissionVo save = menuRepository.save(menu);
        return ReturnFomart.retParam(200,save);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse delMenu(String idsStr) {
        String[] ids = idsStr.split(",");
        for (String id : ids) {
            long parseLong = Long.parseLong(id);
            MenuPermissionVo permissionVo = menuRepository.findByIdAndMenuIsDelete(parseLong, 1);
            permissionVo.setMenuIsDelete(2);
            menuRepository.save(permissionVo);
        }
        return ReturnFomart.retParam(200,"操作成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse updateMenu(MenuPermissionVo menu) {
        MenuPermissionVo permissionVo = menuRepository.findByIdAndMenuIsDelete(menu.getId(), 1);
        if (permissionVo == null ){
            return ReturnFomart.retParam(3102,menu);
        }
        MenuPermissionVo save = menuRepository.save(menu);
        return ReturnFomart.retParam(200,save);
    }

    @Override
    public DataOutResponse findByPid(Integer pid) {
        List<MenuPermissionVo> menuPermissionVos = menuRepository.findByMenuPidAndMenuIsDelete(pid, 1);
        return ReturnFomart.retParam(200,menuPermissionVos);
    }
}
