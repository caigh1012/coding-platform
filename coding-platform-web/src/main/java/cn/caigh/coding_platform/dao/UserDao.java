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
  @Insert("INSERT INTO t_user(uni_phone_number, password, role_id, created_at) VALUES(#{username},#{password},#{role_id},#{created_at})")
  @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
  void registerUser(User user);

  /**
   * 用户列表
   */
  @Select("SELECT * FROM `t_user`")
  List<User> getUserList();

  /**
   * 用户菜单列表
   */
  @Select("SELECT * FROM `t_menu`")
  List<Menu> getMenuList();
}
