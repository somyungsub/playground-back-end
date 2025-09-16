package springai.service.data;

import java.util.List;

// openai 모델 포맷으로 해야 나옴 -> 로그의 text 속성 값 확인
public record EmotionEvaluation (
        Emotion emotion,
        List<String> reason
) {}

