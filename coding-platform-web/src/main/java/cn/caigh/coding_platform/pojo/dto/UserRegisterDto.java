package cn.caigh.coding_platform.pojo.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * 用户注册 dto
 */
public class UserRegisterDto {
  @NotBlank(message = "请输入用户名")
  private String username;

  @NotBlank(message = "请输入密码")
  private String password;

  public UserRegisterDto(String password, String username) {
    this.password = password;
    this.username = username;
  }

  @Override
  public String toString() {
    return "UserRegisterDto{" +
        "username='" + username + '\'' +
        ", password='" + password + '\'' +
        '}';
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
