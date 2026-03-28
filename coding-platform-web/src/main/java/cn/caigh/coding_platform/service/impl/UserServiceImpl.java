package cn.caigh.coding_platform.service.impl;

import cn.caigh.coding_platform.dao.UserDao;
import cn.caigh.coding_platform.pojo.entity.Menu;
import cn.caigh.coding_platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
  @Autowired
  UserDao userDao;

  /**
   * 查询用户的角色列表
   */
  public List<String> roleListByMobile(String username) {
    return userDao.roleListByMobile(username);
  }

  /**
   * 查询用户的菜单列表
   */
  public List<Menu> menuListByMobile(String username) {
    return userDao.menuListByMobile(username);
  }
}
