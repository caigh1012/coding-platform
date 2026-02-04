package cn.caigh.coding_platform.constants;

/**
 * 角色类型
 * 拆分：默认角色和自定义角色
 */
public enum RoleType {
  DefaultRole("0"),
  CustomRole("1");

  private final String type;

  RoleType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
