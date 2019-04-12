package com.leanin.domain.response;

import com.leanin.domain.vo.MenuPermissionVo;
import com.leanin.domain.vo.RoleInfoVo;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class RoleInfoReP extends RoleInfoVo {

    List<MenuPermissionVo> menus;
}
