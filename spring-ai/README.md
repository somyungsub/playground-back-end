# Spring AI

## 기본개념
### LangChain과 차이점
- LangChain 은 랭체인 프레임워크 생태계를 토대로 작성하게됨 (파이썬 기반)
- 사용법위주로 작성을 하게 됨
- SPring AI 는 랭체인에서 영감 -> 랭체인이 하는 역할을 어느정도수행하며 목표로 삼음
- Spring ai -> 벡터스토어가 좀더 표준화되어서.. 제공

### AI Model Providers
- Ollama
  - 로컬환경에서 대규모 언어 모델 (LLM) 실행
  - 오프라인 사용

### 프로젝트
- Ollama, OpenAI 체크
- spring boot 버전 : ?
- jdk 21?? 버전확인해보기
- maven, gradle

## 프로젝트 세팅
- jdk 17이상 (권장21), Spring Boot 3점대이상 (3.2 이상 권장)

## OpenAI
- github -> model 선택 -> api key 발급 -> key 적용
- https://github.com/marketplace/models
- 모델선택 > use this model > language(java, python, ...), sdk 선택 > get developer key, or create personal access token
  - Model 활성화 > 유효기간 등 
  - api-key : 토큰키 입력ㅎㅎ

### ollama
- 로컬에 ollama 설치필요
  - mac(brew) : brew install ollama
  - 로컬설치 : https://ollama.com/download/
  ```shell
    # 1.ollama 버전확인
    $ ollama --version
    
    # 2.실행 확인(로컬서버)
    $ curl http://localhost:11434/api/tags

    # 3.json 응답이없으면, 수동실행 후 -> 재확인
    $ ollma serve
    #//0.0.0.0:* app://* file://* tauri://* vscode-webview://* vscode-file://*] OLLAMA_SCHED_SPREAD:false http_proxy: https_proxy: no_proxy:]"
    #time=2025-09-15T09:58:38.288+09:00 level=INFO source=images.go:477 msg="total blobs: 0"
    #time=2025-09-15T09:58:38.288+09:00 level=INFO source=images.go:484 msg="total unused blobs removed: 0"
    #time=2025-09-15T09:58:38.289+09:00 level=INFO source=routes.go:1384 msg="Listening on 127.0.0.1:11434 (version 0.11.10)"
    #    time=2025-09-15T09:58:38.355+09:00 level=INFO source=types.go:131 msg="inference compute" id=0 library=metal variant="" compute="" driver=0.0 name="" total="21.3 GiB" available="21.3 GiB"
    #    [GIN] 2025/09/15 - 09:59:51 | 200 |    4.024833ms |       127.0.0.1 | GET      "/api/tags"
  
    # 4.앱실행시, 초기로딩에 pulling 발생
    #    2025-09-15T10:01:36.230+09:00  INFO 43140 --- [           main] o.s.a.o.management.OllamaModelManager    : Start pulling model: hf.co/rippertnt/HyperCLOVAX-SEED-Text-Instruct-1.5B-Q4_K_M-GGUF
    #    2025-09-15T10:01:39.554+09:00  INFO 43140 --- [ctor-http-nio-2] o.s.a.o.management.OllamaModelManager    : Pulling the 'hf.co/rippertnt/HyperCLOVAX-SEED-Text-Instruct-1.5B-Q4_K_M-GGUF' model - Status: pulling manifest
    #    2025-09-15T10:02:23.490+09:00  INFO 43140 --- [ctor-http-nio-2] o.s.a.o.management.OllamaModelManager    : Pulling the 'hf.co/rippertnt/HyperCLOVAX-SEED-Text-Instruct-1.5B-Q4_K_M-GGUF' model - Status: pulling 95659a99a087
    #    2025-09-15T10:02:26.366+09:00  INFO 43140 --- [ctor-http-nio-2] o.s.a.o.management.OllamaModelManager    : Pulling the 'hf.co/rippertnt/HyperCLOVAX-SEED-Text-Instruct-1.5B-Q4_K_M-GGUF' model - Status: pulling 62fbfd9ed093
    #    2025-09-15T10:02:29.245+09:00  INFO 43140 --- [ctor-http-nio-2] o.s.a.o.management.OllamaModelManager    : Pulling the 'hf.co/rippertnt/HyperCLOVAX-SEED-Text-Instruct-1.5B-Q4_K_M-GGUF' model - Status: pulling b78301c0df4d
    #    2025-09-15T10:02:31.507+09:00  INFO 43140 --- [ctor-http-nio-2] o.s.a.o.management.OllamaModelManager    : Pulling the 'hf.co/rippertnt/HyperCLOVAX-SEED-Text-Instruct-1.5B-Q4_K_M-GGUF' model - Status: pulling 2ea534a0536f
    #    2025-09-15T10:02:32.378+09:00  INFO 43140 --- [ctor-http-nio-2] o.s.a.o.management.OllamaModelManager    : Pulling the 'hf.co/rippertnt/HyperCLOVAX-SEED-Text-Instruct-1.5B-Q4_K_M-GGUF' model - Status: verifying sha256 digest
    #    2025-09-15T10:02:32.378+09:00  INFO 43140 --- [ctor-http-nio-2] o.s.a.o.management.OllamaModelManager    : Pulling the 'hf.co/rippertnt/HyperCLOVAX-SEED-Text-Instruct-1.5B-Q4_K_M-GGUF' model - Status: writing manifest
    #    2025-09-15T10:02:32.381+09:00  INFO 43140 --- [ctor-http-nio-2] o.s.a.o.management.OllamaModelManager    : Pulling the 'hf.co/rippertnt/HyperCLOVAX-SEED-Text-Instruct-1.5B-Q4_K_M-GGUF' model - Status: success
    #    2025-09-15T10:02:32.382+09:00  INFO 43140 --- [           main] o.s.a.o.management.OllamaModelManager    : Completed pulling the 'hf.co/rippertnt/HyperCLOVAX-SEED-Text-Instruct-1.5B-Q4_K_M-GGUF' model
    #    2025-09-15T10:02:32.499+09:00  INFO 43140 --- [           main] o.s.a.o.management.OllamaModelManager    : Start pulling model: mxbai-embed-large
    #    2025-09-15T10:02:34.368+09:00  INFO 43140 --- [ctor-http-nio-2] o.s.a.o.management.OllamaModelManager    : Pulling the 'mxbai-embed-large' model - Status: pulling manifest
    #    2025-09-15T10:03:01.185+09:00  INFO 43140 --- [ctor-http-nio-2] o.s.a.o.management.OllamaModelManager    : Pulling the 'mxbai-embed-large' model - Status: pulling 819c2adf5ce6
    #    2025-09-15T10:03:03.008+09:00  INFO 43140 --- [ctor-http-nio-2] o.s.a.o.management.OllamaModelManager    : Pulling the 'mxbai-embed-large' model - Status: pulling c71d239df917
    #    2025-09-15T10:03:04.850+09:00  INFO 43140 --- [ctor-http-nio-2] o.s.a.o.management.OllamaModelManager    : Pulling the 'mxbai-embed-large' model - Status: pulling b837481ff855
    #    2025-09-15T10:03:06.019+09:00  INFO 43140 --- [ctor-http-nio-2] o.s.a.o.management.OllamaModelManager    : Pulling the 'mxbai-embed-large' model - Status: pulling 38badd946f91
    #    2025-09-15T10:03:06.573+09:00  INFO 43140 --- [ctor-http-nio-2] o.s.a.o.management.OllamaModelManager    : Pulling the 'mxbai-embed-large' model - Status: verifying sha256 digest
    #    2025-09-15T10:03:06.573+09:00  INFO 43140 --- [ctor-http-nio-2] o.s.a.o.management.OllamaModelManager    : Pulling the 'mxbai-embed-large' model - Status: writing manifest
    #    2025-09-15T10:03:06.574+09:00  INFO 43140 --- [ctor-http-nio-2] o.s.a.o.management.OllamaModelManager    : Pulling the 'mxbai-embed-large' model - Status: success
    #    2025-09-15T10:03:06.574+09:00  INFO 43140 --- [           main] o.s.a.o.management.OllamaModelManager    : Completed pulling the 'mxbai-embed-large' model
    #    2025-09-15T10:03:06.655+09:00  INFO 43140 --- [           main] o.s.b.web.embedded.netty.NettyWebServer  : Netty started on port 9090
    #    2025-09-15T10:03:06.662+09:00  INFO 43140 --- [           main] springai.SpringAiApplication             : Started SpringAiApplication in 91.533 seconds (process running for 92.097)

  ```
- https://ollama.com/search
- GGUF Hugging Face Models
  - https://huggingface.co/
  - https://huggingface.co/models
    - Hugging Face 및 llama.cpp 생태계에서 널리사용되는 모델
    - 모델명 : hf.co/<username>/<model-repository> 
- spring-ai-stater-model-ollama
- Local LLM 테스트를 위해 국내 오픈 모델 사용
  - 약 1.5GB 메모리 필요

### 통신구조
- 보통 Flux 활용
- SSE(Server-Sent-Events)
  - 클라이언트 단방향 실시간 메시지 전달을 위한 HTTP 기반 스트리밍 기술
  - 대부분 이형태의 통신구조를 활용함

### Chat options
- top-k, top-p, temperature

### 프롬프트 엔지니어링
- zero-shot
- one-shot
- few-shot
- system-prompting
  - 전체적인 맥락, 일반적인, 대화흐름 적응에 필요한 내용 지정
  - system or user
- role-prompting
  - ai에 특정역할, 전문가적 관점 등 스타일 부여
  - system or user
- contextual-prompting
  - ai에 추가배경정보(도메인, 청중, 제약등)을 전달해 더 맞춤화된 응답유도
  - 지시문등
  - user
- chain or Thought (CoT)
  - 문제해결과정을 여러 단계로 나누어 논리적으로 추론하도록 유도하는 프롬프트 기법
  - Zero-shot CoT
  - Few-shot CoT
  - prompt에 명시 -> 생각해서 추론하도록 유도
- code prompting
  - LLM 코드를 이해하고, 생성하는 능력을 활용하는 기법
  - 코드 자동화, 문서화, 프로토타입개발에 탁월
  - 다양한 언언 간 코드 변환 및 학습 지원
- step-back 프로프팅
  - 문제를 바로풀지 않고, 관련 배경지식이나 원리를 추출한뒤 이를 바탕으로 세부질문을 해결하는 2단계 접근법
- self-consistency
  - 동일한 문제에 대해 여러번 답변을 생성한뒤, 가장 많이 나오는 답변을 최종 정답으로 선택하는 방식
- Tree of Thought(ToT)
  - 문제해결을 여러경로로 나눠서 동시에 탐색하며 각ㄱ경로를 평가 선택 가지치기 하는 방식
- Automatic Prompt Engineering
  - AI가 스스로 다양한 프롬프트를 생성평가최적화하여 사람이 직접프롬프트를 설계하는 과정을 자동화하는 기법

### Chat Flow
- Advisros -> Prompts -> Models -> Output
- spring ai 공식문서 참고

### Advisors API (AOP 기반으로 보임)
- AI 모델 입력 전과 출력 후 가로채고, 수정하고, 향상시키는 유연한 방법
- AdvisedRequest/Response
- Streaming
  - StreamAroundAdvisor
  - StreamAroundAdvisorChain
- Non-Streaming
  - CallAroundAdvisor
  - CallAroundAdvisorChain

### Chat Memory
- 여러 상호작용에 걸쳐 대화의 맥락을 유지하기 위해 메시지를 저장,검색하는 기능
- LLM이 대화의 맥락인식을 위해 유지하는 정보,
- Chat History : 사용자와 모델간 교환된 전체 대화기록
- 지원메모리 유형
  - MessageWindowChatMemory
    - 최대 N개의 메시지만 유지, 초과시 오래된 메시지디부터 삭제 (기본값 20개)
- 여러 저장소 종류지원
  - 메모리기반(인메모리디비), 기본값
  - RDB, 영구저장
  - 분산DB (카산드라), TTL 지원, 대규모 감사 목적적합
  - Neo4J, 그래프DB, 관계형 데이터활용
- 다양한 Advisor를 통해 메모리 동작을 유연하게 구성

### Structured Output
- 구조화된 데이터의 필요성
- LLM은 자유형식의 텍스트 로 응답하여 파싱이 필요함
- 타입안정성 확보하여 명확한 Java타입으로 데이터 처리
- 설정하지 않을 경우 Raw Output 바로전달
- stream 사용시, 최종완료 후 Convertor 수행
- LLM응답을 파싱하기 위한 반복적인 코드 제거 파싱 로직 간소화
- LLM이 항상원하는 구조로 응답하지 않을 수 있으므로 결과 검증 로직 구현필요
  - OpenAI, Azure OpenAI, Ollama, Mistral AI 등에서 구조화 출력 옵션 지원
- 프롬프트에 형식지침추가
- LLM이 원하는 출력구조를 생성하도록 유도
- 컨버터가 이를 지정된 구조화된 형식으로 변환

## Spring AI Vector Database
- 텍스트, 이미지, 비디오 등 다양한 데이터를 고차원 벡터 형태로 변환하여 저장
- 키워드 매칭이 아닌, 의미적 유사성을 기반으로 문서를 검색
- RAG: 외부 지식소스 참조하여 더정확하고 최신정보기반 답변생성 
- 장기기억장치(Long-Term Memory): LLM 기반 챗봇등 이전대화나 사용자정보를 저장하고, 비슷한 내용을 검색

### Vector Similarity (유사도)
- 벡터: 방향과 크기를 가진 수학적 객체, ai에서는 주로 고차원 공간의 좌표
- 유사도: 두벡터 가 얼마나 비슷한지, 가까운지 수치로 표현, 그들 사이의 각도는 유사성을 측정하는 방법
- Law of Cosines 
- Cosine Similarity
  - 코사인 유사도가 가장 직관적, 과차원 에서도 잘 동작
- Spring AI similarity score
  - 0~1사이의 값으로 유사도를 정량화, 1에 가까울수록 더 높은 유사성을 의미
- Vector Store
  - 임베딩 모델을 사용해 임베딩 값을 데이터를 넣어 둔 Semantic Space 실체
  - Vector DB 의 구현 클래스, 임베딩 값을 저장된 값들과 비교해서 의미 기반(Semantic Space) 검색 제공

### Spring AI 벡터스토어 API
- 다양한 종류의 벡터 데이터베이스를 일관된 추상화 인터페이스 API로 사용
- 지원하는 벡터 DB 다양함
  - Azure Vector Search, 카산드라, 크로마, 엘서 , 마리아디비, 몽고디비 아틀라스, Neo4J, 오라클, PG, Redis 등등..
- SearchRequest
  - topK, similarityThreshold, filterExpression 등 지정
- Document
  - 텍스트와 메타데이터 포함한 기본 저장정보
  - Documnet + 임베딩 벡터를 추가한 정보가 실제 저장
    - VectorStore 구형네 따라 달라짐
    - NoSQL타입에서는 기본값으로 embedding 사용
      - 엘서, 오픈서치 등

### Spring AI Metadata Filters 
- VectorStore 유사도 검색시 도큐먼트의 메타데이터 필드를 기반으로 필터링 
- Filter.Expression 객체로 조건 지정
- SQL과 유사한 문자열로 조건 지정
  - country == 'BG' 등
  - 다양한 연산자 ==, != >, <, IN, AND, OR 등...
  - 사용예 : 버전과리, 날짜관리 등...

### RAG
- LLM의 단점을 극복하기 위한 유용한 기술
  - 사실기반한 정확성
  - 문맥 인식 능력에 한계
- 주요이유
  - LLM의 고정된 학습 데이터 한계 극복
  - 최신 데이터를 계속해서 업데이트 가능
  - 환각현상 감소
    - 검증된 출처 기반 답변생성으로 신뢰성 향상
    - RAG용 데이터 생성시 출처를 포함하고, 이를 검증된 출처로 사용
  - **도메인특화** 이 부분때문에 각광받는중
    - 실무에서는 네트워크 망이 폐쇄적이기 때문에 내부문서/DB 와 연동해 전문분야 질의 처리
    - 보안이 중요한 분야 LLM 활용 가능(망분리환경, 전금법등)
      - Local LLM + 내부문서DB 사용시 내부 자료 외부 유출방지

### RAG를 위해 Vector Database가 필수인가?
- 필수는 아니다
  - 이미 잘 정의된 자연어 검색방법이 있다면, 기존방식 사용하면됨. 
    - 사용자 질문 > LLM 사용 기존 검색 쿼리로 변경 > 검색결과 사용
  - 데이터 API 서비스
    - 웹검색, 법류, 의료 등 전문분야의 데이터가 API로 제공되는 서비스 사용
- Vector DB 기반 RAG 사용하는 이유
  - 내부데이터가 비정형데이터로 존재하고, 검색시스템이 부재한 경우
    - 기존 검색엔진구축보다 쉽게 구축하고, 일정수준의 결과를 얻기가 쉬움
  - 다국어 서비스 제공
    - 다국어 지원 임베딩 모델의 경우 언어가 달라도 검색이 가능
  - 기존 방식의 자연어 검색 엔진 유지보수가 어려운경우
    - 임베딩 모델 변경으로 검색성능향상 > 다시 임베딩이 필요하지만 기존 자연어 검색보다 간단하게 처리 가능

### Spring AI RAG 구현
- RetrievealAugmentationAdvisor 
  - Modular RAG 아키텍처 구현
  - 새로운 모듈이나 패러다임의 등장에 대응가능
  - 5단계 순서가 정해져 있음
    - Pre-Retrieval : 쿼리 변환 및 전처리 
    - Query Expansion: 쿼리 확장
    - **Retrieval: 문서 검색 (필수설정)**
    - Document Join & Post-Retrieval: 문서결합 및 후처리 
    - Generation & Augmentation: 문서기반 답변생성
- ETL 파이프라인 지원
  - 다양한 양식에서 chunk 추철, Vector 생성, Document 저장 지원

### Spring AI ETL 파이프라인
- source -> 도큐먼트 reader > transformer > writer -> store
- Apache Tika 
  - pdf, ms office, HTML, MD 등 다양한 파일형식
- DocumentReader
  - JsonReader
  - TextReader
  - JsoupDocumentReader
  - MarkdownDocumentReader
  - PagePdfDocumentReader
  - ParagraphPdfDocumentReader
  - TikaDocumentReader
- DocumentTransformer
  - TokenTextSplitter
  - TextSplitter Interface
  - KeywordMetadataEnricher
  - SummaryMetadataEnricher
- DocumentWriter
  - FileDocumentWriter
  - VectorStore

### VectorStore
- OpenSearch 사용
  - 엘라스틱서치 기반 
- 엘라스틱서치 사용
- 도커활용
```shell
# 인증없이 접속할 수 있도록 실행
$ docker run -d --name local-elasticsearch \
  -p 9200:9200 \
  -e "discovery.type=single-node" \
  -e "xpack.security.enabled=false" \
  -e "xpack.security.http.ssl.enabled=false" \
  -e "ES_JAVA_OPTS=-Xms512m -Xmx512m" \
  docker.elastic.co/elasticsearch/elasticsearch:8.18.3
```

## Tool Calling
- AI 모델이 외부 시스템과 상호작용 할 수 있도록 하는 기능
- 주요용도
  - 정보검색
  - 작없행
- 필요한것
  - AI application 에 Tool에 대응하는 기능 개발
  - AI 모델이 이해 할 수 있는 Tool 에 대응하는 Tool Spec 정의
- Adviser 에서 RAG 나 API를 호출하는 것 과 다른 것인가?
  - 호출 요청을 AI가 함
    - 다른 Tool을 호출
    - 사용자에게 직접 질문으로 요청
- 파이썬 -> LangChain의 Tool / Agent 과 유사

### Tool Specification
- LLM이 외부함수 or API, 도구 등을 호출할 수 있도록 함수의 정의와 입력파라미터, 사용목적을 JSON Schema 형태로 명확하게 기술하는 방식
- 구성요소 
  - type: 도구의 형태
  - name: 도구의 이름
  - description: 도구의 목적 및 설명
  - parameters: 입력값의 이름,타입,필수 여부 등
  - 모델별 상이할 수 있음
- @Toll 애노테이션, @ToolParam
- 스웨거의 @Schema
- Jackson @JsonProperty, @JsonPropertyDescription
- class, method
- @Bean 기반 동적 Tool 등록 지원
- Bean 메서드명 == Tool 이름
- @Description : Tool 설명
- 메서드를 직접 Tool로 변환
  - MethodToolCallback.Builder -> 정보설정 -> ChatClient.tools에 직접등록
- 함수형 인터페이스 
  - FunctionToolCallback.Builder -> 설정 
- 대부분은 @Tool @ToolParam으로 사용하면 됨

### Spring AI Tool Execution
- 도구정의 -> chat 요청에 포함 전달
- AI모델이 도구 호출 필요시, 도구명과 인자를 응답으로 전송
- AI 애플리케이션이 Tool명으로 실제 개발된 Tool 실행
- AI 애플리케이션이 Tool 결과 최종처리 판단 (Return Direct 기능)
- Tool 실행 결과를 모델에 전달
- AI 모델이 Tool 결과를 활용해 최종 응답 생성

### 외부 API 연동 Tool Specification 개발(날씨조회)
- Tools
  - wttr.in 서비스 사용
    - 인증없이 무료 날씨 조회 서비스
    - curl wttr.in 만으로 현재날씨와 향후 3일간 예보 제공
    - 다양한 포맷 출력 제공

## Spring AI MCP
- Model Context Protocol
- AI 모델이 외부 도구 및 리소스와 구조적으로 상호작용 할 수 있게 해주는 표준 프로토콜
- MCP는 클라이언트-서버 모델
- 3-layer 아키텍처
- 클라이언트/서버계층
  - McpClient
    - 서버 연결관리
  - McpServer
    - Tools, 리소스, 다양한 기능 제공
- 세션계층
  - 통신상태 및 패턴 관리, 메시지 교환의 일관성 보장
- 트랜스포트 계층
  - JSON-RPC 메시지 직렬화/역직렬화
  - STDIO, Streamable HTTP 지원
- MCP Java SDK 를 기반으로 Spring 애플리케이션에서 MCP를 쉽게 사용할 수 있도록 지원
- Spring Boot 스타터를 제공하여 MCP 클라이언트와 서버를 간편하게 설정
- Tool 기능 활용하면서, MCP 구축
  - ToolCallingManager API 활용
  - ChatModelAPI
  - AI Model
  - MCP -> Tool 관리, Tool 기반 활용

```shell
# 파이썬
$ pip install mcp_weather_server
```

## Spring AI Examples
- 공식 Spring AI 예제
  - https://github.com/spring-projects/spring-ai-examples
- 커뮤니티 예제 및 Spring AI 관련 자료
  - https://github.com/spring-ai-community/awesome-spring-ai
  - 강의
    - Spring AI
      - https://github.com/JM-Lab/spring-ai-playground
      - https://github.com/JM-Lab/spring-ai-local-cli-chatbot
      - https://github.com/JM-Lab/spring-ai-fast-campus-course
    - AI Agent 파이썬
      - https://github.com/gongwon-nayeon/fastcampus-aiagent