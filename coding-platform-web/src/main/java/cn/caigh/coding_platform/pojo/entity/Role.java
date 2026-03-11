package cn.caigh.coding_platform.pojo.entity;

public class Role {
  private Integer id;
  private String role_id;
  private String role_type;
  private String role_name;
  private String role_description;

  public Role(Integer id, String role_id, String role_type, String role_name, String role_description) {
    this.id = id;
    this.role_id = role_id;
    this.role_type = role_type;
    this.role_name = role_name;
    this.role_description = role_description;
  }

  @Override
  public String toString() {
    return "Role{" +
        "id=" + id +
        ", role_id='" + role_id + '\'' +
        ", role_type='" + role_type + '\'' +
        ", role_name='" + role_name + '\'' +
        ", role_description='" + role_description + '\'' +
        '}';
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getRole_id() {
    return role_id;
  }

  public void setRole_id(String role_id) {
    this.role_id = role_id;
  }

  public String getRole_type() {
    return role_type;
  }

  public void setRole_type(String role_type) {
    this.role_type = role_type;
  }

  public String getRole_name() {
    return role_name;
  }

  public void setRole_name(String role_name) {
    this.role_name = role_name;
  }

  public String getRole_description() {
    return role_description;
  }

  public void setRole_description(String role_description) {
    this.role_description = role_description;
  }
}
