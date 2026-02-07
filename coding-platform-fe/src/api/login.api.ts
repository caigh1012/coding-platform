import { LoginRsp, PwdLoginDto } from '@/interfaces/login/login.interface';

import { http } from './http';

/**
 * 密码登录
 */
export function postPwdLogin(dto: PwdLoginDto) {
  return http.post<LoginRsp>('login', dto);
}
