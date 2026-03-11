package cn.caigh.coding_platform.pojo.vo.login;

public class CaptchaVerifyVo {
  private boolean isVerifyPass;
  private String verifyMessage;

  public CaptchaVerifyVo() {
  }

  public CaptchaVerifyVo(String verifyMessage, boolean isVerifyPass) {
    this.verifyMessage = verifyMessage;
    this.isVerifyPass = isVerifyPass;
  }

  public boolean isVerifyPass() {
    return isVerifyPass;
  }

  public void setVerifyPass(boolean verifyPass) {
    isVerifyPass = verifyPass;
  }

  public String getVerifyMessage() {
    return verifyMessage;
  }

  public void setVerifyMessage(String verifyMessage) {
    this.verifyMessage = verifyMessage;
  }
}
