package com.platform.universally.auth;

//import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
//import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
//import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platform.universally.manager.consts.StatusConsts;
//import com.platform.universally.manager.model.SysMenu;
import com.platform.universally.manager.model.SysUser;
import com.platform.universally.manager.service.ISysResourceService;
import com.platform.universally.manager.service.ISysRoleService;
import com.platform.universally.manager.service.ISysUserService;

import javax.annotation.Resource;

/**
 * 用户身份验证,授权 Realm 组件
 * 
 * @author StarZou
 * @since 2014年6月11日 上午11:35:28
 **/
@Service("securityRealm")
public class SecurityRealm extends AuthorizingRealm {

	@Resource(name = "sysUserService")
	private ISysUserService sysUserService;

	@Autowired
	private ISysRoleService sysRoleService;

	@Autowired
	private ISysResourceService sysResourceService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());


	/**
	 * 认证管理
	 * 验证当前用户的信息是否符合要求，用户名和密码
	 * 认证的方式：token认证，密码认证，验证码认证...
	 *
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//该值来源于SubjectUtils.getSubject.login(token);
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();

		if (StringUtils.isEmpty(username)) {
			throw new AuthenticationException("User name cannot be empty");
		}
		if (null == token.getCredentials()) {
			throw new AuthenticationException("Password cannot be empty");
		}
		String password = new String(upToken.getPassword());
		if (StringUtils.isEmpty(password)) {
			throw new AuthenticationException("Password cannot be empty");
		}
		// 通过数据库进行验证
		SysUser sysUser = sysUserService.getSysUserByUserName(username);
		if (sysUser == null) {
			throw new AuthenticationException("Wrong username or password");
		} else {
			if (!password.equals(sysUser.getPassword())) {
				throw new AuthenticationException("Wrong username or password");
			} else if (!StatusConsts.STATUS_NORMAL.equals(sysUser.getStatus())) {
				throw new AuthenticationException("User status is disabled");
			}
		}
		return new SimpleAuthenticationInfo(password, ByteSource.Util.bytes(username), getName());
	}

	/**
	 * 授权管理
	 * 管理当前用户的角色
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		UsernamePasswordToken principal = (UsernamePasswordToken) principals.getPrimaryPrincipal();
		String username = principal.getUsername();
		/** 添加角色 */
		try {
			List<String> roleCodeList = sysRoleService.listRoleCodesByUserCode(username);
			for (String roleCode : roleCodeList) {
				authorizationInfo.addRole(roleCode);
			}
		} catch (Exception e) {
			logger.error("listRoleCodeByManagerUsername error, username:"+ username, e);
			return authorizationInfo;
		}
		return authorizationInfo;
	}


}