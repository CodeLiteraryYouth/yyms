package com.leanin.domain.request;

import com.leanin.domain.vo.RoleInfoVo;
import com.leanin.domain.vo.UserRoleVo;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class RoleInfoVoReq extends RoleInfoVo {

    private List<Long> menuIds;
}
