package cn.caigh.coding_platform.service.impl;

import cn.caigh.coding_platform.constants.DefaultPassword;
import cn.caigh.coding_platform.dao.PmsDao;
import cn.caigh.coding_platform.dao.UserDao;
import cn.caigh.coding_platform.pojo.dto.pms.AddUserDto;
import cn.caigh.coding_platform.pojo.dto.pms.DeleteUserDto;
import cn.caigh.coding_platform.pojo.entity.Menu;
import cn.caigh.coding_platform.pojo.entity.Role;
import cn.caigh.coding_platform.pojo.entity.User;
import cn.caigh.coding_platform.pojo.vo.common.ResultVo;
import cn.caigh.coding_platform.service.PmsService;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PmsServiceImpl implements PmsService {
  @Autowired
  PmsDao pmsDao;

  @Autowired
  UserDao userDao;

  @Autowired
  private PasswordEncoder passwordEncoder;

  /**
   * 用户列表（全部用户）
   */
  public List<User> userList() {
    return pmsDao.getUserList();
  }

  /**
   * 菜单列表（全菜单）
   */
  public List<Menu> menuList() {
    return pmsDao.getMenuList();
  }

  /**
   * 角色列表（全部角色）
   */
  public List<Role> roleList() {
    return pmsDao.getRoleList();
  }

  /**
   * 删除用户
   */
  public int deleteUser(DeleteUserDto deleteUserDto) {
    return pmsDao.updateUserActive(deleteUserDto);
  }

  /**
   * 添加用户
   */
  @Transactional
  public ResultVo<String> addUser(AddUserDto addUserDto) {
    User user = userDao.getUserInfo(addUserDto.getMobilePhone());
    if (!ObjectUtil.isNull(user)) {
      return ResultVo.failed("用户已经添加或在其他ORG中");
    }
    User userInfo = new User();
    userInfo.setUsername(addUserDto.getMobilePhone());
    userInfo.setPassword(passwordEncoder.encode(DefaultPassword.password));
    DateTime now = DateTime.now();
    userInfo.setCreated_at(now);
    userDao.registerUser(userInfo);
    return ResultVo.success();
  }
}
