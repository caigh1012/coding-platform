package cn.caigh.coding_platform.pojo.dto.pms;

import jakarta.validation.constraints.NotBlank;

/**
 * 添加用户 dto
 */
public class AddUserDto {
  @NotBlank(message = "请输入手机号")
  private String mobilePhone;

  @NotBlank(message = "添加用户的角色不能为空")
  private String roleList;

  @NotBlank(message = "图形验证码不能为空")
  private String captchaCode;

  @NotBlank(message = "验证码id不能为空")
  private String captchaId;

  @Override
  public String toString() {
    return "AddUserDto{" +
        "mobilePhone='" + mobilePhone + '\'' +
        ", roleList='" + roleList + '\'' +
        ", captchaCode='" + captchaCode + '\'' +
        ", captchaId='" + captchaId + '\'' +
        '}';
  }

  public AddUserDto() {
  }

  public AddUserDto(String mobilePhone, String roleList, String captchaCode, String captchaId) {
    this.mobilePhone = mobilePhone;
    this.roleList = roleList;
    this.captchaCode = captchaCode;
    this.captchaId = captchaId;
  }

  public @NotBlank(message = "请输入手机号") String getMobilePhone() {
    return mobilePhone;
  }

  public void setMobilePhone(@NotBlank(message = "请输入手机号") String mobilePhone) {
    this.mobilePhone = mobilePhone;
  }

  public @NotBlank(message = "添加用户的角色不能为空") String getRoleList() {
    return roleList;
  }

  public void setRoleList(@NotBlank(message = "添加用户的角色不能为空") String roleList) {
    this.roleList = roleList;
  }

  public @NotBlank(message = "图形验证码不能为空") String getCaptchaCode() {
    return captchaCode;
  }

  public void setCaptchaCode(@NotBlank(message = "图形验证码不能为空") String captchaCode) {
    this.captchaCode = captchaCode;
  }

  public @NotBlank(message = "验证码id不能为空") String getCaptchaId() {
    return captchaId;
  }

  public void setCaptchaId(@NotBlank(message = "验证码id不能为空") String captchaId) {
    this.captchaId = captchaId;
  }
}
