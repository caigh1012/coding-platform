package cn.caigh.coding_platform.service.impl;

import cn.caigh.coding_platform.dao.UserDao;
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
}
