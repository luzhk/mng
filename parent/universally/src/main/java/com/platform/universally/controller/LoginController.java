package com.platform.universally.controller;

import javax.annotation.Resource;

import com.platform.universally.manager.auth.JWTToken;
import com.platform.universally.config.jwt.JWTUtil;
import com.platform.universally.manager.response.JsonResult;
import com.platform.universally.manager.response.ResCode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.platform.universally.consts.BussiConsts;
import com.platform.universally.controller.vo.LoginCommand;
import com.platform.universally.controller.vo.SignupCommand;
import com.platform.universally.manager.model.SysUser;
import com.platform.universally.manager.service.ISysUserService;
import com.platform.universally.service.IUtilService;

/**
 * 用户登录接口
 * @author Administrator
 *
 */
@Controller
public class LoginController {

    private static transient final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Resource(name="sysUserService")
    private ISysUserService sysUserService;
    
    @Resource(name="utilService")
    private IUtilService utilService;
    
    /**
     * 完成登录后跳转到主页面
     * @param loginCommand
     * @return
     */
    @ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public JsonResult login(@RequestBody LoginCommand loginCommand){
//        String encryptedPassword = new Sha256Hash(loginCommand.getPassword(), ByteSource.Util.bytes(loginCommand.getUsername())).toBase64();
//		UsernamePasswordToken token = new UsernamePasswordToken(loginCommand.getUsername(), encryptedPassword, loginCommand.getRememberMe());
		String jwtToken = JWTUtil.sign(loginCommand.getUsername(), loginCommand.getPassword());
		JWTToken token = new JWTToken(jwtToken, loginCommand.getUsername(), loginCommand.getPassword());
        try {
            SecurityUtils.getSubject().login(token);
        } catch (AuthenticationException e) {
			logger.debug("Error authenticating.", e);
			return JsonResult.getJsonResult(ResCode.FAIL, e.getMessage());
        }
        return JsonResult.getJsonResult(ResCode.SUCCESS, jwtToken);
	}


	/**
	 * 注册的实际发生过程,注册完成后到登录页面
	 * @param command
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public JsonResult signup(@RequestBody SignupCommand command){
		SysUser record = new SysUser();
		record.setUserName(command.getUsername());
		String encryptedPassword = new Sha256Hash(command.getPassword(), ByteSource.Util.bytes(command.getUsername())).toBase64();
		record.setPassword(encryptedPassword);
		record.setStatus(BussiConsts.NORMAL_STATUS);
		String userCode = utilService.getUserSerialNo(BussiConsts.MODULE_USER, BussiConsts.SERIAL_NO_USER);
		record.setUserCode(userCode);
		try {
			sysUserService.saveSysUser(record);
		} catch (Exception e) {
			logger.error("异常信息：", e);
			return JsonResult.getJsonResult(ResCode.FAIL, false);
		}
		return JsonResult.getJsonResult(ResCode.SUCCESS, true);
	}
	
	
}
