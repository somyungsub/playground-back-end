package springai.config.rag;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.document.DocumentTransformer;
import org.springframework.ai.document.DocumentWriter;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.model.transformer.KeywordMetadataEnricher;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;
import org.springframework.ai.rag.postretrieval.document.DocumentPostProcessor;
import org.springframework.ai.rag.preretrieval.query.expansion.MultiQueryExpander;
import org.springframework.ai.rag.preretrieval.query.transformation.TranslationQueryTransformer;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import springai.config.property.AppProperty;

import java.io.IOException;
import java.util.*;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RAGConfig {
  private final AppProperty appProperty;

  // 1. reader
  @Bean
  public DocumentReader[] documentReaders() throws IOException {
    return Arrays.stream(new PathMatchingResourcePatternResolver().getResources(appProperty.getDocumentLocation()))
            .map(TikaDocumentReader::new)
            .toArray(DocumentReader[]::new);
  }

  // 2. transformer
  @Bean
  DocumentTransformer textSplitter() {
    return new LengthTextSplitter(400,200);
  }

  @Bean
  DocumentTransformer keywordMetadata(ChatModel chatModel) {
//    SummaryMetadataEnricher
    return new KeywordMetadataEnricher(chatModel, 3);
  }

  // 3. writer
  @Bean
  DocumentWriter jsonConsoleDocumentWriter(ObjectMapper objectMapper) {
    return documents -> {
      log.info("======= save chunks size %d ======== {}", documents.size());
      try {
        log.info("{}", objectMapper.writerWithDefaultPrettyPrinter());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      log.info("======= end ========");
    };
  }

  @ConditionalOnProperty(prefix = "app", name = "vector-store-in-memory-enabled", havingValue = "true")
  @Bean
  VectorStore vectorStoreSimple(EmbeddingModel embeddingModel) {
    return SimpleVectorStore.builder(embeddingModel).build();
  }

  // 4. chain 연결
  @ConditionalOnProperty(prefix = "app", name = "pipeline-init", havingValue = "true")
  @Order(1)
  @Bean
  ApplicationRunner initEtlPipeline(
          DocumentReader[] documentReaders,
          DocumentTransformer textSplitter,
//          DocumentTransformer keywordMetadata,
          DocumentWriter[] documentWriters

  ) {
    return args -> {
      Arrays.stream(documentReaders)
              .map(DocumentReader::read)
              .map(textSplitter)
//              .map(keywordMetadata)
              .forEach(documents -> Arrays.stream(documentWriters)
                      .forEach(documentWriter -> safeWrite(documentWriter, documents))
              );
    };
  }

  private void safeWrite(DocumentWriter writer, List<Document> docs) {
    try {
      writer.write(docs);
    } catch (Exception e) {
      log.warn("Skipping failed write: {}", e.getMessage());
    }
  }

  // 5. advisor, Enable RAG
//  @Bean
//  public RetrievalAugmentationAdvisor retrievalAugmentationAdvisor(VectorStore vectorStore,
////            ChatClient.Builder chatClientBuilder,
//                                                                   Optional<DocumentPostProcessor> documentsPostProcessor) {
//    RetrievalAugmentationAdvisor.Builder retrievalAugmentationAdvisorBuilder =
//            RetrievalAugmentationAdvisor.builder()
////                        .queryExpander(MultiQueryExpander.builder().chatClientBuilder(chatClientBuilder).build())
////                        .queryTransformers(TranslationQueryTransformer.builder().chatClientBuilder(chatClientBuilder)
////                                .targetLanguage("korean").build())
//                    .queryAugmenter(ContextualQueryAugmenter.builder().allowEmptyContext(true).build())
//                    .documentRetriever(VectorStoreDocumentRetriever.builder().similarityThreshold(0.3).topK(3)
//                            .vectorStore(vectorStore).build());
//    // RAG CLI 를 위해 등록
//    documentsPostProcessor.ifPresent(retrievalAugmentationAdvisorBuilder::documentPostProcessors);
//    return retrievalAugmentationAdvisorBuilder.build();
//  }

  @Bean
  RetrievalAugmentationAdvisor retrievalAugmentationAdvisor(
          VectorStore vectorStoreSimple,
          ChatClient.Builder chatClientBuilder
  ) {

    MultiQueryExpander expander = MultiQueryExpander.builder()
            .chatClientBuilder(chatClientBuilder)
            .build();

    ContextualQueryAugmenter augmenter = ContextualQueryAugmenter.builder().build();

    VectorStoreDocumentRetriever retriever = VectorStoreDocumentRetriever.builder()
            .vectorStore(vectorStoreSimple)
            .similarityThreshold(0.3)
            .topK(3)
            .build();

    TranslationQueryTransformer transformer = TranslationQueryTransformer.builder()
            .chatClientBuilder(chatClientBuilder)
            .targetLanguage("en")
            .build();

    RetrievalAugmentationAdvisor.Builder builder = RetrievalAugmentationAdvisor.builder()
            .queryExpander(expander)
            .queryTransformers(transformer)
            .queryAugmenter(augmenter)
            .documentRetriever(retriever);

    return builder.build();
  }

  @ConditionalOnProperty(prefix = "app", name = "print-enabled", havingValue = "true")
  @Bean
  public DocumentPostProcessor printDocumentsPostProcessor() {
    return (query, documents) -> {
      log.info("[ Search Results ]");
      log.info("===============================================");

      if (CollectionUtils.isEmpty(documents)) {
        log.info("  No search results found.");
        log.info("===============================================");
        return documents;
      }

      for (int i = 0; i < documents.size(); i++) {
        Document document = documents.get(i);
        System.out.printf("▶ %d Document, Score: %.2f%n", i + 1, document.getScore());
        System.out.println("-----------------------------------------------");
        Optional.ofNullable(document.getText()).stream()
                .map(text -> text.split("\n")).flatMap(Arrays::stream)
                .forEach(line -> System.out.printf("%s%n", line));
        System.out.println("===============================================");
      }
      log.info("\n[ RAG 사용 응답 ]\n\n");
      return documents;
    };
  }

}

@Slf4j
class LengthTextSplitter extends TextSplitter {

  final int chunkSize;
  final int chunkOverlap;

  public LengthTextSplitter(int chunkSize, int chunkOverlap) {
    if (chunkSize <= 0) {
      throw new IllegalArgumentException("chunkSize must be positive.");
    }

    if (chunkOverlap < 0 || chunkOverlap >= chunkSize){
      throw new IllegalArgumentException("chunkOverlap must be >= 0 and < chunkSize.");
    }

    this.chunkSize = chunkSize;
    this.chunkOverlap = chunkOverlap;
  }

  @Override
  protected List<String> splitText(String text) {
    if (StringUtils.isEmpty(text)){
      return new ArrayList<>();
    }

    // 텍스트 길이가 overlap보다 작거나 같으면 전체를 하나의 청크로 처리
    int textLength = text.length();
    if (textLength <= chunkOverlap) {
      return List.of(text);
    }

    // 청크 사이즈 단위로 텍스트를 분할하되, overlap 만큼 겹치게 이동
    var chunks = new ArrayList<String>();
    int position = 0;
    while (position < textLength) {
      int end = Math.min(position + chunkSize, textLength);
      chunks.add(text.substring(position, end));
      int nextPosition = end - chunkOverlap;
      // nextPosition이 더 이상 앞으로 나아가지 않으면 정지
      if (nextPosition <= position) {
        break;
      }
      position = nextPosition;
    }
    return chunks;
  }
}