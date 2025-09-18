# 시큐리티 관련

## AuthorizationServerConfigurerAdapter
- oauth 관련 인증시점 예외발생시, RestControllerAdvice / ControllerAdvice / ExceptionHandler 등 컨트롤러 단에서 발생 핸들링에 잡히지 않음
- 따라서 따로 처리를 해줘야함
- AuthorizationServerConfigurerAdapter 설정 필요
  - Spring Security OAuth2에서 **Authorization Server(인증 서버)**의 설정을 커스터마이징(Override) 하기 위한 추상 클래스
### 자주 재정의(Override)하는 메서드
configure(ClientDetailsServiceConfigurer clients)
- OAuth2 Client(Client ID, Secret, grant type, scope 등) 설정
configure(AuthorizationServerEndpointsConfigurer endpoints)
- 토큰 저장소, 인증 매니저, 사용자 서비스, 예외 처리 등 설정
configure(AuthorizationServerSecurityConfigurer security)
- 엔드포인트별 보안 설정 (예: /oauth/token_key 접근 제어 등)

### Configuration
```java
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
  // ...
  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    // ...
    endpoints
            .approvalStore()
            .authorizationCodeServices()
            .tokenStore()
            .tokenServices()
            .authenticationManager()
            .userDetailsService()
            .requestFactory(y(clientDetailsService))
            .tokenGranter()
            .exceptionTranslator(); // exception 핸들링
  }
}

//...

// 구현 및 bean 등록 필요
class OAuthWebResponseExceptionTranslator extends DefaultWebResponseExceptionTranslator {
  @Override
  public ResponseEntity<OAuth2Exception> translate(Exception oAuth2Exception) throws Exception {
//    String message = StringUtils.defaultString(oAuth2Exception.getMessage(), '');
//    ResponseEntity<OAuth2Exception> responseEntity = super.translate(new InvalidTokenException(message));
//    ResponseEntity<OAuth2Exception> responseEntity = super.translate(oAuth2Exception);
  }
}
```

### 요약
- AuthorizationServerConfigurerAdapter는 Spring OAuth2 인증 서버를 “내 서비스 환경에 맞게” 상세하게 커스터마이즈하는 데 사용하는 핵심 추상 클래스
- @EnableAuthorizationServer와 함께 사용하며, 토큰, 클라이언트, 보안, 예외처리 등 모든 동작을 여기서 설정
- 커스텀 예외처리 핸들링은 http security에서 정의하는 핸들링 및 accessDeniedHandler 등록과는 별개로 따로 처리해야됨
  - exceptionTranslator 등록 및 DefaultWebResponseExceptionTranslator 구현체 bean 주입필요

