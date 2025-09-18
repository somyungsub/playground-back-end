package springai.mcp.client.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import springai.mcp.client.service.McpChatService;

import java.util.Arrays;


@Service
@Slf4j
class McpChatServiceImpl implements McpChatService {

  private final ChatClient chatClient;

  @Value("${app.chat.default-system-prompt:''}")
  private String systemPrompt;

  public McpChatServiceImpl(
          @Qualifier("openAiChatModel") ChatModel chatModel,
//          @Qualifier("ollamaChatModel") ChatModel chatModel,
          Advisor[] advisors,
          ToolCallbackProvider[] toolCallbackProviders
  ) {
    ToolCallback[] toolCallbacks = Arrays.stream(toolCallbackProviders)
            .flatMap(provider -> Arrays.stream(provider.getToolCallbacks()))
            .peek(callback -> {
              ToolDefinition def = callback.getToolDefinition();
              log.info("ToolDefinition – name: {}, description: {}, schema: {}", def.name(), def.description(),
                      def.inputSchema());
            })
            .toArray(ToolCallback[]::new);

    this.chatClient = ChatClient
            .builder(chatModel)
            .defaultSystem(systemPrompt)
            .defaultOptions(ToolCallingChatOptions.builder()
                    .internalToolExecutionEnabled(true)// 생략해도 true가 기본값
                    .temperature(0.2).build()
            )
            .defaultAdvisors(advisors)
            .defaultToolCallbacks(toolCallbacks)
            .build();

  }


  @Override
  public Flux<String> stream(Prompt prompt, String conversationId) {
    return buildChatClientRequestSpec(prompt, conversationId)
            .stream()
            .content();

  }

  @Override
  public ChatResponse call(Prompt prompt, String conversationId) {
    return buildChatClientRequestSpec(prompt,conversationId)
            .call()
            .chatResponse();
  }

  private ChatClient.ChatClientRequestSpec buildChatClientRequestSpec(Prompt prompt, String conversationId) {
    return chatClient
            .prompt(prompt)
            .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId));
  }

}
