package cn.caigh.coding_platform.service;

import cn.caigh.coding_platform.pojo.entity.User;

import java.util.List;

public interface UserService {
  /**
   * 用户列表
   */
  List<User> userList();
}
