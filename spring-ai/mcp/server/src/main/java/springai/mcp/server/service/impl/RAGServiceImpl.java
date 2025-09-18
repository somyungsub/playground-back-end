package springai.mcp.server.service.impl;

import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import springai.mcp.server.service.RAGService;

@Service
class RAGServiceImpl implements RAGService {
  private final ChatClient chatClient;

  public RAGServiceImpl(
//          @Qualifier("openAiChatModel") ChatModel chatModel,
          @Qualifier("ollamaChatModel") ChatModel chatModel,
          Advisor[] advisors
  ) {
    this.chatClient = ChatClient
            .builder(chatModel)
            .defaultOptions(ChatOptions.builder().temperature(0.3).build())
            .defaultAdvisors(advisors)
            .build();
  }

  @Override
  public Flux<String> stream(Prompt prompt, String conversationId, @Nullable String filterExpression) {
    return buildChatClientRequestSpec(prompt, conversationId, filterExpression)
            .stream()
            .content();
  }

  @Override
  public ChatResponse call(Prompt prompt, String conversationId, @Nullable String filterExpression) {
    return buildChatClientRequestSpec(prompt, conversationId, filterExpression)
            .call()
            .chatResponse();
  }


  private ChatClient.ChatClientRequestSpec buildChatClientRequestSpec(
          Prompt prompt,
          String conversationId,
          @Nullable String filterExpressionAsOpt
  ) {

    ChatClient.ChatClientRequestSpec chatClientSpec = chatClient
            .prompt(prompt)
            .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId));

    if (StringUtils.isNotEmpty(filterExpressionAsOpt)) {
      chatClientSpec.advisors(advisorSpec -> advisorSpec.param(VectorStoreDocumentRetriever.FILTER_EXPRESSION, filterExpressionAsOpt));
    }

    return chatClientSpec;
  }

}
