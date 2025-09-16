package springai.controller;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import springai.service.AiService;

@RestController
@RequestMapping("/ai")
public class AiController {

  private final AiService aiService;

  public AiController(
          @Qualifier("AiServiceMulti") AiService aiService
//          @Qualifier("AiServiceBasic") AiService aiService
  ) {
    this.aiService = aiService;
  }

  @GetMapping("/ask")
  public String ask(@RequestParam String q) {
    return aiService.ask(q);
  }

  @GetMapping("/ask-response")
  public ChatResponse askResponse(@RequestParam String q) {
    return aiService.askResponse(q);
  }

  @GetMapping(value = "/ask-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<String> askStream(@RequestParam String q) {
    return aiService.askStream(q);
  }

}
