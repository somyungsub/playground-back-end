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
- api-key : 토큰키 입력

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
- 