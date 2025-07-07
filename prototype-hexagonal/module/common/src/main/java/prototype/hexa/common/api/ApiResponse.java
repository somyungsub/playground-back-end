package prototype.hexa.common.api;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {

    private final ApiHeader header;
    private final T body;

    private ApiResponse(ApiHeader header, T body) {
        this.header = header;
        this.body = body;
    }

    public static <T> ApiResponse<T> ok(T response) {
        return new ApiResponse<>(ApiHeader.ok(), response);
    }

    public static ApiResponse<?> error(Throwable throwable, HttpStatus status) {
        ApiError apiError = ApiError.of(throwable.getMessage(), status.getReasonPhrase(), status.value());
        return new ApiResponse<>(ApiHeader.error(apiError), null);
    }

    public static ApiResponse<?> error(String message, String messageCode, HttpStatus status) {
        ApiError apiError = ApiError.of(message, messageCode, status.value());
        return new ApiResponse<>(ApiHeader.error(apiError), null);
    }

}
