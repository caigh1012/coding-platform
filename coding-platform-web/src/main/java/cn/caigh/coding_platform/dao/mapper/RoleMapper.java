package cn.caigh.coding_platform.dao.mapper;

import org.apache.ibatis.annotations.Insert;

import java.util.List;

public interface RoleMapper {
  /**
   * 批量插入角色
   */
  @Insert({
      "<script>",
      "INSERT INTO t_user_roles (user_id, role_id) VALUES ",
      "<foreach collection='list' item='roleId' separator=','>",
      "(#{username}, #{roleId})",
      "</foreach>",
      "</script>"
  })
  void batchAddRole(String username, List<String> roleList);
}
