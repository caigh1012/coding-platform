package cn.caigh.coding_platform.controller;

import cn.caigh.coding_platform.pojo.vo.login.CaptchaVerifyVo;
import cn.caigh.coding_platform.pojo.vo.login.CaptchaVo;
import cn.caigh.coding_platform.pojo.vo.common.ResultVo;
import cn.caigh.coding_platform.service.CaptchaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CaptchaCtrl {
  @Autowired
  private CaptchaService captchaService;


  /**
   * 生成验证码
   */
  @GetMapping(value = "/captcha.json")
  public ResultVo<CaptchaVo> generateCaptcha() {
    return captchaService.generateCaptcha();
  }

  /**
   * 图形验证码验证
   */
  @PostMapping("/captcha/verify.json")
  public ResultVo<CaptchaVerifyVo> verifyCaptcha(String captchaId, String code) {
    CaptchaVerifyVo captchaVerifyVo = captchaService.verifyCaptcha(captchaId, code);
    return ResultVo.success(captchaVerifyVo);
  }
}
