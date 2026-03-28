import { GraphCaptcha } from '@/interfaces/captcha/captcah';

import { http } from './http';

/**
 * 获取腾讯云滑块 appid （加密后）
 */
export function getTencentEncryptAppid() {
  return http.get<string>('/tencent/encryptappid.json');
}

/**
 * 图形验证码
 */
export function getGraphCaptcha() {
  return http.get<GraphCaptcha>('/graph/captcha.json');
}
