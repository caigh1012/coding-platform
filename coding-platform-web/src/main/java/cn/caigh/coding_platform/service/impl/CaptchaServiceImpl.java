package cn.caigh.coding_platform.service.impl;

import cn.caigh.coding_platform.constants.RedisKey;
import cn.caigh.coding_platform.pojo.vo.login.CaptchaVerifyVo;
import cn.caigh.coding_platform.pojo.vo.login.CaptchaVo;
import cn.caigh.coding_platform.pojo.vo.common.ResultVo;
import cn.caigh.coding_platform.service.CaptchaService;
import cn.caigh.coding_platform.utils.RedisUtil;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CaptchaServiceImpl implements CaptchaService {
  @Autowired
  private RedisUtil redisUtil;

  @Override
  public ResultVo<CaptchaVo> generateCaptcha() {
    // 生成验证码（宽度、高度、验证码位数、干扰线数量）
    LineCaptcha captcha = CaptchaUtil.createLineCaptcha(200, 100, 4, 50);

    String key = IdUtil.fastSimpleUUID();
    String code = captcha.getCode();

    String captchaKey = RedisKey.getCaptchaKey(key);
    redisUtil.set(captchaKey, code, 60); // 1 分钟内有效
    CaptchaVo captchaVo = new CaptchaVo();

    captchaVo.setCaptchaId(key);
    captchaVo.setCaptcha(captcha.getImageBase64());

    return ResultVo.success(captchaVo);
  }

  @Override
  public CaptchaVerifyVo verifyCaptcha(String captchaId, String code) {
    String captchaKey = RedisKey.getCaptchaKey(captchaId);
    CaptchaVerifyVo captchaVerifyVo = new CaptchaVerifyVo();
    if (!redisUtil.hasKey(captchaKey)) {
      captchaVerifyVo.setVerifyMessage("验证码已过期或不存在");
      captchaVerifyVo.setVerifyPass(false);
      return captchaVerifyVo;
    }
    String verifyCode = (String) redisUtil.get(captchaKey);
    if (!verifyCode.equalsIgnoreCase(code)) {
      captchaVerifyVo.setVerifyMessage("验证码错误");
      captchaVerifyVo.setVerifyPass(false);
      return captchaVerifyVo;
    }

    captchaVerifyVo.setVerifyMessage("验证码通过");
    captchaVerifyVo.setVerifyPass(true);
    return captchaVerifyVo;
  }
}
