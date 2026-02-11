import { MenuItem } from '@/interfaces/user/user-menu.interface';

import { http } from './http';

/**
 * 用户菜单列表
 */
export function getMenuList() {
  return http.get<Array<MenuItem>>('/user/menu.json');
}
