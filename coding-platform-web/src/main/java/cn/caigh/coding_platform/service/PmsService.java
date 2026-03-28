package cn.caigh.coding_platform.service;

import cn.caigh.coding_platform.pojo.dto.pms.AddUserDto;
import cn.caigh.coding_platform.pojo.dto.pms.DeleteUserDto;
import cn.caigh.coding_platform.pojo.entity.Menu;
import cn.caigh.coding_platform.pojo.entity.Role;
import cn.caigh.coding_platform.pojo.entity.User;
import cn.caigh.coding_platform.pojo.vo.common.ResultVo;

import java.util.List;

public interface PmsService {
  /**
   * 用户列表（全部用户）
   */
  List<User> userList();

  /**
   * 菜单列表（全菜单）
   */
  List<Menu> menuList();

  /**
   * 角色列表（全部角色）
   */
  List<Role> roleList();

  /**
   * 删除用户
   */
  int deleteUser(DeleteUserDto deleteUserDto);

  /**
   * 添加用户
   */
  ResultVo<String> addUser(AddUserDto dto);
}
