package com.leanin.domain.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class OnlineEduVo extends OnlineEdu {

    private AdminUserVo adminUserVo;

    private FormInfoVo formInfoVo;

    private List<WardInfoVo> wardInfoVos;

}
