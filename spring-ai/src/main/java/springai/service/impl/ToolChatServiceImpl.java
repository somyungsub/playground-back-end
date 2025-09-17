package springai.service.impl;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import springai.config.property.AppProperty;
import springai.service.ToolChatService;
import springai.service.data.EmotionEvaluation;
import springai.service.tool.WeatherTool;

@Service
public class ToolChatServiceImpl implements ToolChatService {
  private final ChatClient chatClient;

  public ToolChatServiceImpl(
          //          @Qualifier("openAiChatModel") ChatModel chatModel,
          @Qualifier("ollamaChatModel") ChatModel chatModel,
          Advisor[] advisors,
          AppProperty appProperty,
          WeatherTool weatherTool

  ) {
    this.chatClient = ChatClient
            .builder(chatModel)
            .defaultSystem(appProperty.getDefaultSystemPrompt())
            .defaultTools(weatherTool)
            .defaultOptions(
                    ToolCallingChatOptions.builder()
                            .internalToolExecutionEnabled(true)
                            .temperature(0.2)
                            .build()
            )
//            .defaultOptions(ChatOptions.builder().temperature(0.3).build())
            .defaultAdvisors(advisors)
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

  @Override
  public EmotionEvaluation callEmotion(Prompt prompt, String conversationId) {
    return buildChatClientRequestSpec(prompt, conversationId)
            .call()
            .entity(EmotionEvaluation.class);
  }

  private ChatClient.ChatClientRequestSpec buildChatClientRequestSpec(Prompt prompt, String conversationId) {
    return chatClient
            .prompt(prompt)
            .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId));
  }
}
