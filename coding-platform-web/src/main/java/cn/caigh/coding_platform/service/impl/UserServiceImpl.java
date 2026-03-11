package cn.caigh.coding_platform.service.impl;

import cn.caigh.coding_platform.dao.UserDao;
import cn.caigh.coding_platform.pojo.dto.user.DeleteUserDto;
import cn.caigh.coding_platform.pojo.entity.Menu;
import cn.caigh.coding_platform.pojo.entity.Role;
import cn.caigh.coding_platform.pojo.entity.User;
import cn.caigh.coding_platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
  @Autowired
  UserDao userDao;

  /**
   * 用户列表
   */
  public List<User> userList() {
    return userDao.getUserList();
  }

  /**
   * 用户菜单
   */
  public List<Menu> menuList() {
    return userDao.getMenuList();
  }

  /**
   * 角色列表
   */
  public List<Role> roleList() {
    return userDao.getRoleList();
  }

  /**
   * 删除用户
   */
  public int deleteUser(DeleteUserDto deleteUserDto) {
    return userDao.updateUserActive(deleteUserDto);
  }
}
