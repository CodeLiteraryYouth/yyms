package com.leanin.oauth.repository;

import com.leanin.domain.vo.MenuPermissionVo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MenuRepository extends JpaRepository<MenuPermissionVo,Long> {

    MenuPermissionVo findByMenuNameAndMenuIsDelete(String menuName,Integer isDelete);

    MenuPermissionVo findByIdAndMenuIsDelete(Long id,Integer isDelete);

    List<MenuPermissionVo> findByMenuPidAndMenuIsDelete(Integer pid,Integer isDel);

}
