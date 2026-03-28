import { LoginRsp, PwdLoginDto } from '@/interfaces/login/login.interface';

import { http } from './http';

/**
 * 密码登录
 */
export function postPwdLogin(dto: PwdLoginDto) {
  return http.post<LoginRsp>('/login/pwd.json', dto);
}

/**
 * 退出登录
 */
export function logout() {
  return http.get('/logout.json');
}
