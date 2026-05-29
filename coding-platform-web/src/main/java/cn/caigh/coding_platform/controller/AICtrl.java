package cn.caigh.coding_platform.controller;

import cn.caigh.coding_platform.pojo.dto.ai.ChatAIDto;
import cn.caigh.coding_platform.pojo.dto.ai.StreamChunk;
import jakarta.validation.Valid;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
public class AICtrl {
  private final DeepSeekChatModel chatModel;

  @Autowired
  public AICtrl(DeepSeekChatModel chatModel) {
    this.chatModel = chatModel;
  }

  @GetMapping(value = "/ai/generate.json")
  public Map generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
    return Map.of("generation", chatModel.call(message));
  }

  @PostMapping(value = "/ai/generatestream.json", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<ChatResponse> generateStream(@RequestBody @Valid ChatAIDto chatAIDto) {
    System.out.println("触发:" + chatAIDto.getMessage());
    var prompt = new Prompt(new UserMessage(chatAIDto.getMessage()));
    return chatModel.stream(prompt);
  }

  @PostMapping(value = "/ai/chat.json", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<ServerSentEvent<StreamChunk>> chatStream(@RequestBody @Valid ChatAIDto chatAIDto) {
    System.out.println("触发:" + chatAIDto.getMessage());
    Prompt prompt = new Prompt(new UserMessage(chatAIDto.getMessage()));
    return chatModel.stream(prompt).map(chatResponse -> {
      String text = chatResponse.getResult().getOutput().getText();
      String finishReason = chatResponse.getResult().getMetadata().getFinishReason();
      boolean isStreamDone = "STOP".equals(finishReason);
      return new StreamChunk(text, isStreamDone);
    }).map(chunk -> ServerSentEvent.<StreamChunk>builder().data(chunk).build());
  }
}
