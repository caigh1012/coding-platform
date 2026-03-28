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
