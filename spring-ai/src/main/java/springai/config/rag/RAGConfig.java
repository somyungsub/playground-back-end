package springai.config.rag;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.document.DocumentTransformer;
import org.springframework.ai.document.DocumentWriter;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.model.transformer.KeywordMetadataEnricher;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;
import org.springframework.ai.rag.preretrieval.query.expansion.MultiQueryExpander;
import org.springframework.ai.rag.preretrieval.query.transformation.TranslationQueryTransformer;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Configuration
public class RAGConfig {

  // 1. reader
  @Bean
  DocumentReader documentReader(@Value("classpath:fastcampus-springai.pdf") String file) throws IOException {
    Resource resources = new PathMatchingResourcePatternResolver().getResource(file);
    return new TikaDocumentReader(resources);
  }

  // 2. transformer
  @Bean
  DocumentTransformer textSplitter(DocumentReader documentReader) {
//    LengthTextSplitter lengthTextSplitter = new LengthTextSplitter();
//    return TokenTextSplitter();
    return new LengthTextSplitter(200,100);
  }

  @Bean
  DocumentTransformer keywordMetadata(ChatModel chatModel) {
//    SummaryMetadataEnricher
    return new KeywordMetadataEnricher(chatModel, 4);
  }

  // 3. writer
  @Bean
  DocumentWriter jsonConsoleDocumentWriter(ObjectMapper objectMapper) {
    return documents -> {
      try {
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      System.out.println("======end======");
    };
  }

  @Bean
  VectorStore vectorStoreSimple(EmbeddingModel embeddingModel) {
    SimpleVectorStore.builder(embeddingModel).build();
    return SimpleVectorStore.builder(embeddingModel).build();
  }

  // 4. chain 연결
  @Order(1)
  @Bean
  ApplicationRunner initEtlPipeline(
          DocumentReader[] documentReaders,
          DocumentTransformer textSplitter,
          DocumentTransformer keywordMetadata,
          DocumentWriter[] documentWriters

  ) {
    return args -> {
      // TODO
      Arrays.stream(documentReaders)
              .map(DocumentReader::read)
              .map(textSplitter)
              .map(keywordMetadata)
              .forEach(documents -> Arrays.stream(documentWriters)
                      .forEach(documentWriter -> documentWriter.write(documents))
              );
    };
  }

  // 5. advisor
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

}

class LengthTextSplitter extends TextSplitter {

  final int chunkSize;
  final int chunkOverlap;

  LengthTextSplitter(int chunkSize, int chunkOverlap) {
    this.chunkSize = chunkSize;
    this.chunkOverlap = chunkOverlap;
  }

  @Override
  protected List<String> splitText(String text) {
    // TODO
    return List.of(text);
  }
}