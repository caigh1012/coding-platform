package cn.caigh.coding_platform.pojo.dto.captcha;

import jakarta.validation.constraints.NotBlank;

public class TencentAppidVerifyDto {
  @NotBlank(message = "ticket不能为空")
  private String ticket;

  @NotBlank(message = "randstr不能为空")
  private String randstr;

  public TencentAppidVerifyDto() {
  }

  @Override
  public String toString() {
    return "TencentAppidVerifyDto{" +
        "ticket='" + ticket + '\'' +
        ", randstr='" + randstr + '\'' +
        '}';
  }

  public String getTicket() {
    return ticket;
  }

  public void setTicket(String ticket) {
    this.ticket = ticket;
  }

  public String getRandstr() {
    return randstr;
  }

  public void setRandstr(String randstr) {
    this.randstr = randstr;
  }
}
