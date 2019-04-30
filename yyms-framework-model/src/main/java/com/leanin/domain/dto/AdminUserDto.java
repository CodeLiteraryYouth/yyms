package com.leanin.domain.dto;

import com.leanin.domain.vo.MenuPermissionVo;
import com.leanin.domain.vo.RoleInfoVo;
import com.leanin.domain.vo.WardInfoVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 管理员用户操作类
 * @author Administrator
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserDto implements Serializable {

	private Long adminId;				//管理员ID

	private String adminName;			//管理员名称

	private String workNum;				//用户工号

	private String password;			//密码

	private String wardCode;			//用户科室编码

	private String jobTitle;			//用户职称

	private String adminDesc;			//用户自我介绍

	private String phone;				//用户手机号

	private String areaCode;			//院区编码

	private String idCard;				//身份证号

	private Integer sex;				//性别

	private String birthday;			//管理员出生日期

	private String organAscri;			//机构归属

	private String remark;				//备注

	private Integer state;				//-1：注销 0：未注销

	private String hospitalName;		//医院名称

	private Integer userType;			//用户类型

	private String menu;				//权限字符串

	private List<RoleInfoVo> roleList;	//该用户的角色信息

	private List<MenuPermissionVo> menuPermissionVoList; //权限集合

	private List<WardInfoVo> wardCodeList;			//用户科室编码



	/*@Override
	public Set<GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<>();
		for (RoleInfoDto role : this.roleList) {
			for (MenuPermissionVo menu : role.getMenuList()) {
				authorities.add(new SimpleGrantedAuthority(menu.getMenuName()));
			}
		}
		return authorities;
	}

	@Override
	public String getUsername() {
		return workNum;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}*/
}