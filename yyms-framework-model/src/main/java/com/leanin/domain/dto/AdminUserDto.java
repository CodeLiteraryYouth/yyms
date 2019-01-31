package com.leanin.domain.dto;

import com.leanin.domain.vo.MenuPermissionVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
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
public class AdminUserDto implements UserDetails {

	private Long adminId;	//管理员ID
	
	private String adminName;	//管理员名称
	
	private String workNum;	//用户工号
	
	private String password;	//密码
	
	private String wardCode;	//用户科室编码
	
	private String jobTitle;	//用户职称
	
	private String adminDesc;	//用户自我介绍
	
	private String phone;	//用户手机号
	
	private String areaCode;	//院区编码
	
	private String idCard;	//身份证号
	
	private Integer sex;	//性别
	
	private String birthday;	//管理员出生日期
	
	private String organAscri;	//机构归属
	
	private String remark;	//备注

	private List<RoleInfoDto> roleList;	//该用户的角色信息

	@Override
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
	}
}