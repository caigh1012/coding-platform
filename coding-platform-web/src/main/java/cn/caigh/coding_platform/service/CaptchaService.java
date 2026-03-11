package cn.caigh.coding_platform.service;

import cn.caigh.coding_platform.pojo.vo.login.CaptchaVerifyVo;
import cn.caigh.coding_platform.pojo.vo.login.CaptchaVo;
import cn.caigh.coding_platform.pojo.vo.common.ResultVo;

public interface CaptchaService {
  /**
   * 获取验证码
   */
  ResultVo<CaptchaVo> generateCaptcha();

  /**
   * 校验验证码
   */
  CaptchaVerifyVo verifyCaptcha(String captchaId, String code);
}
