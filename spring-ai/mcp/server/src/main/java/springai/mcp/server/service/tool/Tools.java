package springai.mcp.server.service.tool;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;
import springai.mcp.server.service.RAGService;

@Component
@Slf4j
@RequiredArgsConstructor
public class Tools {

  private final RAGService ragService;

  @Tool(description = "Spring AI 강의에 대해 RAG 기반으로 답변을 제공", returnDirect = true)
  public String ragTool(
          @ToolParam(description = "Spring AI 강의에 대한 질문") String userPrompt
  ) {
    log.info("rag tool prompt : {} :", userPrompt);
    return ragService.call(createPrompt(userPrompt), "MCP-SERVER", "")
            .getResult()
            .getOutput()
            .getText();
  }

  private static Prompt createPrompt(String userPrompt) {
    return Prompt.builder()
            .messages(
                    UserMessage.builder().text(userPrompt).build()
            )
            .build();
  }

}
