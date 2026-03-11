package cn.caigh.coding_platform.service;

import cn.caigh.coding_platform.pojo.dto.login.PwdLoginDto;
import cn.caigh.coding_platform.pojo.dto.login.UserRegisterDto;
import cn.caigh.coding_platform.pojo.vo.login.LoginVo;
import cn.caigh.coding_platform.pojo.vo.common.ResultVo;

public interface LoginService {
  /**
   * 用户注册
   */
  ResultVo<String> register(UserRegisterDto userRegisterDto);

  /**
   * 密码登录
   */
  LoginVo pwdLogin(PwdLoginDto pwdLoginDto);

  /**
   * 退出登录
   */
  ResultVo<String> loginout(String username);
}
