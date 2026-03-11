package cn.caigh.coding_platform.service;

import cn.caigh.coding_platform.pojo.dto.user.DeleteUserDto;
import cn.caigh.coding_platform.pojo.entity.Menu;
import cn.caigh.coding_platform.pojo.entity.Role;
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

  /**
   * 角色列表
   */
  List<Role> roleList();

  /**
   * 删除用户
   */
  int deleteUser(DeleteUserDto deleteUserDto);
}
