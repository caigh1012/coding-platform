package cn.caigh.coding_platform.service;

import cn.caigh.coding_platform.pojo.entity.Menu;
import cn.caigh.coding_platform.pojo.entity.User;

import java.util.List;

public interface UserService {
  /**
   * 用户列表
   */
  List<User> userList();

  /**
   * 用户菜单列表
   */
  List<Menu> menuList();
}
