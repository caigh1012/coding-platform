package cn.caigh.coding_platform.controller;

import cn.caigh.coding_platform.pojo.dto.login.PwdLoginDto;
import cn.caigh.coding_platform.pojo.dto.login.UserRegisterDto;
import cn.caigh.coding_platform.pojo.vo.login.CaptchaVerifyVo;
import cn.caigh.coding_platform.pojo.vo.login.LoginVo;
import cn.caigh.coding_platform.service.CaptchaService;
import cn.caigh.coding_platform.service.LoginService;
import cn.caigh.coding_platform.pojo.vo.common.ResultVo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginCtrl {
  @Autowired
  private LoginService loginService;

  @Autowired
  private CaptchaService captchaService;

  /**
   * 用户注册
   */
  @PostMapping(value = "/register.json")
  public ResultVo<String> register(@RequestBody @Valid UserRegisterDto userRegisterDto) {
    CaptchaVerifyVo captchaVerifyVo = captchaService.verifyCaptcha(userRegisterDto.getCaptchaId(), userRegisterDto.getCaptchaCode());
    if (!captchaVerifyVo.isVerifyPass()) {
      return ResultVo.failed(captchaVerifyVo.getVerifyMessage());
    }
    return loginService.register(userRegisterDto);
  }

  /**
   * 密码登录
   */
  @PostMapping(value = "/login/pwd.json")
  public ResultVo<LoginVo> pwdLogin(@RequestBody @Valid PwdLoginDto pwdLoginDto) {
    CaptchaVerifyVo captchaVerifyVo = captchaService.verifyCaptcha(pwdLoginDto.getCaptchaId(), pwdLoginDto.getCaptchaCode());
    System.out.println(captchaVerifyVo);
    if (!captchaVerifyVo.isVerifyPass()) {
      return ResultVo.failed(captchaVerifyVo.getVerifyMessage());
    }
    LoginVo loginVo = loginService.pwdLogin(pwdLoginDto);
    return ResultVo.success(loginVo);
  }

  /**
   * 退出登录
   */
  @GetMapping(value = "/logout.json")
  public ResultVo<String> loginOut(@AuthenticationPrincipal UserDetails userDetails) {
    String username = userDetails.getUsername();
    return loginService.loginout(username);
  }
}
