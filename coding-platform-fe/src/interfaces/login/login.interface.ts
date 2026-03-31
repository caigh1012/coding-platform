/**
 * 密码登录 dto
 */
export interface PwdLoginDto {
  /**
   * 用户名
   */
  readonly username: string;
  /**
   * 密码
   */
  readonly password: string;

  /**
   * 图形验证码id
   */
  readonly captchaId: string;

  /**
   * 图形验证码
   */
  readonly captchaCode: string;
}

/**
 * 返回 Login 数据
 */
export interface LoginRsp {
  /**
   * 请求返回的token
   */
  readonly token: string;
}
