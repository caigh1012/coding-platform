package cn.caigh.coding_platform.pojo.dto.pms;

import jakarta.validation.constraints.NotBlank;

/**
 * 删除用户 dto
 */
public class DeleteUserDto {
  @NotBlank(message = "手机号码不能为空")
  private String mobilePhone;

  @NotBlank(message = "isActive为必填项")
  private String isActive;

  public DeleteUserDto() {
  }

  public DeleteUserDto(String mobilePhone, String isActive) {
    this.mobilePhone = mobilePhone;
    this.isActive = isActive;
  }

  public String getMobilePhone() {
    return mobilePhone;
  }

  public void setMobilePhone(String mobilePhone) {
    this.mobilePhone = mobilePhone;
  }

  public String getIsActive() {
    return isActive;
  }

  public void setIsActive(String isActive) {
    this.isActive = isActive;
  }
}
