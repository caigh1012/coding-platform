package cn.caigh.coding_platform.controller;

import cn.caigh.coding_platform.pojo.dto.PwdLoginDto;
import cn.caigh.coding_platform.pojo.dto.UserRegisterDto;
import cn.caigh.coding_platform.pojo.vo.LoginVo;
import cn.caigh.coding_platform.service.LoginService;
import cn.caigh.coding_platform.pojo.vo.common.ResultVo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginCtrl {
  @Autowired
  private LoginService loginService;

  /**
   * 用户注册
   */
  @PostMapping(value = "/register.json")
  public ResultVo<String> register(@RequestBody @Valid UserRegisterDto userRegisterDto) {
    return loginService.register(userRegisterDto);
  }

  /**
   * 密码登录
   */
  @PostMapping(value = "/login/pwd.json")
  public ResultVo<LoginVo> pwdLogin(@RequestBody @Valid PwdLoginDto pwdLoginDto) {
    LoginVo loginVo = loginService.pwdLogin(pwdLoginDto);
    return ResultVo.success(loginVo);
  }

  /**
   * 退出登录
   */
  @GetMapping(value = "/logout.json")
  public ResultVo<String> loginOut() {
    return loginService.loginout();
  }
}
