package com.leanin.domain.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OnlineEduVo extends OnlineEdu {

    private AdminUserVo adminUserVo;

    private FormInfoVo formInfoVo;

}
