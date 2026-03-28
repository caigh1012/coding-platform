/**
 * 获取图形验证码
 */
export interface GraphCaptcha {
  /**
   * 图形验证码
   */
  readonly captcha: string;
  /**
   * 图形验证码id
   */
  readonly captchaId: string;
}

/**
 * 腾讯云滑块验证码验证
 */
export interface TencentCaptchaDto {
  /**
   * 验证码成功的票据
   */
  readonly ticket: string;
  /**
   * 随机字符串
   */
  readonly randstr: string;
}
