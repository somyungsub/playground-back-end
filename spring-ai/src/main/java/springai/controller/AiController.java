package springai.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
}
