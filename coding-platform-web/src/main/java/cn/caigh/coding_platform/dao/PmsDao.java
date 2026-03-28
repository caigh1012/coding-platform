package cn.caigh.coding_platform.dao;

import cn.caigh.coding_platform.pojo.dto.pms.AddUserDto;
import cn.caigh.coding_platform.pojo.dto.pms.DeleteUserDto;
import cn.caigh.coding_platform.pojo.entity.Menu;
import cn.caigh.coding_platform.pojo.entity.Role;
import cn.caigh.coding_platform.pojo.entity.User;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 权限相关
 */
public interface PmsDao {
  /**
   * 用户列表（全部用户）
   */
  @Select("SELECT * FROM `t_user`")
  List<User> getUserList();

  /**
   * 菜单列表（全菜单）
   */
  @Select("SELECT * FROM `t_menu`")
  List<Menu> getMenuList();

  /**
   * 角色列表（全部角色）
   */
  @Select("SELECT * FROM `t_role`")
  List<Role> getRoleList();

  /**
   * 禁用用户或（软）删除用户
   */
  @Update("UPDATE t_user SET is_active = #{isActive} WHERE uni_phone_number = #{mobilePhone}")
  int updateUserActive(DeleteUserDto deleteUserDto);

  /**
   * 添加用户
   */
  void addUser(AddUserDto dto);
}
