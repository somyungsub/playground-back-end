package springai.service.data;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record WeatherResponse(
        @Schema(description = "일별(3일) 예보 정보 리스트")
        List<WeatherForecast> weather
) {
  // 일별 예보 (hourly 제외)
  private record WeatherForecast(
          @Schema(description = "천문 정보(일출, 일몰 등)") List<Astronomy> astronomy,
          @Schema(description = "해당 날짜(yyyy-MM-dd)") String date,
          @Schema(description = "평균 기온(섭씨)") int avgtempC,
          @Schema(description = "평균 기온(화씨)") int avgtempF,
          @Schema(description = "최고 기온(섭씨)") int maxtempC,
          @Schema(description = "최고 기온(화씨)") int maxtempF,
          @Schema(description = "최저 기온(섭씨)") int mintempC,
          @Schema(description = "최저 기온(화씨)") int mintempF,
          @Schema(description = "일조 시간(시간 단위)") double sunHour,
          @Schema(description = "적설량(센티미터)") double totalSnow_cm,
          @Schema(description = "자외선 지수") int uvIndex
  ) { }

  // 천문 정보
  private record Astronomy(
          @Schema(description = "달 밝기(%)") int moon_illumination,
          @Schema(description = "달의 위상(예: Full Moon 등)") String moon_phase,
          @Schema(description = "달 뜨는 시각") String moonrise,
          @Schema(description = "달 지는 시각") String moonset,
          @Schema(description = "해 뜨는 시각") String sunrise,
          @Schema(description = "해 지는 시각") String sunset
  ) {}
}
