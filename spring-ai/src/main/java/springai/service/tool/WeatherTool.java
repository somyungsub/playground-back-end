package springai.service.tool;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import springai.service.data.WeatherResponse;

@Service
@RequiredArgsConstructor
public class WeatherTool {
  private final WebClient webClient;

  @Tool(name = "weatherTool", description = "지역이름을 받아서 날씨를 조회합니다.", returnDirect = true)
  public String callWeather(@ToolParam(description = "지역 이름") String location) {
    return webClient
            .get()
            .uri(uriBuilder ->
                    uriBuilder.scheme("https:").host("wttr.in").path(location.replace(" ", "+"))
                            .queryParam("lang", "ko")
                            .queryParam("format", "현재+%l의+날씨는+%C+상태이며,+기온은+%t,+체감+기온은+%f,+풍속은+%W,+습도는+%h,+강수량은+%p입니다")
                            .build()
            )
            .retrieve()
            .bodyToMono(String.class)
            .block();
  }

  @Tool(description = "지역이름을 받아서 현재 3일간의 날씨와 천문 정보(달의 위상과 해와 달의 뜨고 지는 시각)를 조회합니다.")
  public WeatherResponse callWeatherDetails(@ToolParam(description = "지역 이름") String location) {
    return webClient
            .get()
            .uri(uriBuilder ->
                    uriBuilder.scheme("https:").host("wttr.in").path(location.replace(" ", "+"))
                            .queryParam("lang", "ko")
                            .queryParam("format", "j1")
                            .build()
            )
            .retrieve()
            .bodyToMono(WeatherResponse.class)
            .block();
  }
}
