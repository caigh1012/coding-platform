package cn.caigh.coding_platform.service;

import cn.caigh.coding_platform.pojo.entity.Menu;

import java.util.List;

public interface UserService {
  /**
   * 查询用户的角色列表
   */
  List<String> roleListByMobile(String username);

  /**
   * 查询用户的菜单列表
   */
  List<Menu> menuListByMobile(String username);
}
