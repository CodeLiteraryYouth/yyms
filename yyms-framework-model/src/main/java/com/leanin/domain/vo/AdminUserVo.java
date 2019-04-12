package com.leanin.domain.vo;

import com.leanin.domain.dao.UserDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * 管理员用户操作类
 * @author Administrator
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserVo extends UserDao {



	private List<Long> roleIds; 	//角色id集合
}