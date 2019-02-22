package com.leanin.oauth.service.impl;

import com.alibaba.fastjson.JSON;
import com.leanin.domain.dto.MenuPermissionDto;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.MenuPermissionVo;
import com.leanin.oauth.mapper.MenuMapper;
import com.leanin.oauth.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;


    @Override
    public DataOutResponse findMenuByUser(String userCode) {
        log.info("用户的工号为:"+userCode);
        List<MenuPermissionDto> menuList=menuMapper.findMenuByUser(userCode);
        log.info("该用户的菜单列表为:"+ JSON.toJSONString(menuList));
        return ReturnFomart.retParam(200,getResult(menuList));
    }

    @Override
    public DataOutResponse findMenuList(String menuName) {
        log.info("菜单名称为:"+menuName);
        List<MenuPermissionDto> menuList=menuMapper.findMenuList(menuName);
        log.info("所有的菜单列表为:"+JSON.toJSONString(menuList));
        return ReturnFomart.retParam(200,getResult(menuList));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse updateMenuStatus(Long menuPid) {
        MenuPermissionVo menuVo=menuMapper.findMenuById(menuPid);
        log.info("该节点的菜单信息为:"+JSON.toJSONString(menuVo));
        menuVo.setMenuState(-1L);
        menuMapper.updateMenuInfo(menuVo);
        List<MenuPermissionVo> menuList=menuMapper.findMenuByPid(menuPid);
        log.info("该节点的子节点信息为:"+JSON.toJSONString(menuList));
        for(MenuPermissionVo menu:menuList) {
            menu.setMenuState(-1L);
            menuMapper.updateMenuInfo(menu);
            updateMenuStatus(menu.getId());
        }
        return ReturnFomart.retParam(200,menuList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse addMenuInfo(MenuPermissionVo menu) {
        log.info("添加的菜单信息为:"+JSON.toJSONString(menu));
        menuMapper.addMenuInfo(menu);
        return ReturnFomart.retParam(200,menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse updateMenuInfo(MenuPermissionVo menu) {
        log.info("修改的菜单信息为:"+JSON.toJSONString(menu));
        menuMapper.updateMenuInfo(menu);
        return ReturnFomart.retParam(200,menu);
    }

    /**
     * 递归查找子菜单
     *
     * @param id
     *            当前菜单id
     * @param rootMenu
     *            要查找的列表
     * @return
     */
    private List<MenuPermissionDto> getChild(Long id, List<MenuPermissionDto> rootMenu) {
        // 子菜单
        List<MenuPermissionDto> childList = new LinkedList<>();
        for (MenuPermissionDto menu : rootMenu) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (menu.getMenuPid()!=0) {
                if (menu.getMenuPid().equals(id)) {
                    childList.add(menu);
                }
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (MenuPermissionDto menu : childList) {
            // 递归
            menu.setChildren(getChild(menu.getId(), rootMenu));
        }
        // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        //对递归菜单进行排序
        List<MenuPermissionDto> listSort=new ArrayList<>(childList);
        Collections.sort(listSort);
        return listSort;
    }

    /**
     * 处理返回结果
     * @param menuList
     * @return
     */
    private List<MenuPermissionDto> getResult(List<MenuPermissionDto> menuList) {
        for(MenuPermissionDto menuDto:menuList) {
            menuDto.setChildren(getChild(menuDto.getId(),menuList));
        }
        List<MenuPermissionDto> resultList=new ArrayList<>();
        for(int j=0;j<menuList.size();j++) {
            if(menuList.get(j).getMenuPid()==0) {
                resultList.add(menuList.get(j));
            }
        }
        return resultList;
    }
}
