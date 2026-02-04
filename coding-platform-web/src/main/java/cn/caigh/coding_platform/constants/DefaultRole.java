package cn.caigh.coding_platform.constants;

/**
 * 默认角色
 */
public enum DefaultRole {
  Admin("admin");

  private final String roleId;

  DefaultRole(String roleId) {
    this.roleId = roleId;
  }

  public String getRoleId() {
    return roleId;
  }
}
