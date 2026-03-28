package cn.caigh.coding_platform.dao;

import cn.caigh.coding_platform.pojo.entity.Menu;
import cn.caigh.coding_platform.pojo.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
  /**
   * 获取或查询某个用户信息
   */
  @Select("SELECT * FROM `t_user` WHERE uni_phone_number = #{username}")
  User getUserInfo(@Param("username") String username);

  /**
   * 用户注册
   */
  @Insert("INSERT INTO t_user(uni_phone_number, password, created_at) VALUES(#{username},#{password},#{created_at})")
  @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
  void registerUser(User user);

  /**
   * 给用户设置角色
   */
  @Insert("INSERT INTO t_user_roles (user_id, role_id) VALUES (#{mobile}, #{role})")
  void addRoleByMobile(String mobile, String role);

  /**
   * 查询用户的角色列表
   */
  @Select("SELECT role_id FROM `t_user_roles` WHERE user_id=#{username}")
  List<String> roleListByMobile(String username);

  /**
   * 查询用户的菜单列表
   */
  @Select("SELECT DISTINCT m.* FROM  t_user_roles ur JOIN t_role_menus rm ON ur.role_id = rm.role_id JOIN t_menu m ON rm.menu_id = m.menu_id WHERE ur.user_id =#{username}")
  List<Menu> menuListByMobile(String username);
}
