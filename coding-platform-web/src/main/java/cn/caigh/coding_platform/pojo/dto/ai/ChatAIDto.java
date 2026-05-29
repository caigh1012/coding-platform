package cn.caigh.coding_platform.pojo.dto.ai;

import jakarta.validation.constraints.NotBlank;

public class ChatAIDto {
  @NotBlank(message = "message 不能为空")
  private String message;

  public ChatAIDto() {
  }

  @Override
  public String toString() {
    return "ChatAIDto{" +
        "message='" + message + '\'' +
        '}';
  }

  public @NotBlank(message = "message 不能为空") String getMessage() {
    return message;
  }

  public void setMessage(@NotBlank(message = "message 不能为空") String message) {
    this.message = message;
  }
}
