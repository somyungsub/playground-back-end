package prototype.hexa.common.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import prototype.hexa.common.api.ApiResponse;

import java.util.Locale;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
class GlobalExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler({
            IllegalStateException.class, IllegalArgumentException.class,
            TypeMismatchException.class, HttpMessageNotReadableException.class,
            MissingServletRequestParameterException.class, MultipartException.class,
            MethodArgumentNotValidException.class
    })
    public ApiResponse<?> handleBadRequestException(Exception e) {
        log.info("400 Bad request exception occurred: {}", e.getMessage(), e);
        return createResponse(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ApiResponse<?> handleNotFoundException(Exception e) {
        log.info("404 NotFound exception occurred: {}", e.getMessage(), e);
        return createResponse(HttpStatus.NOT_FOUND, e);
    }

    @ExceptionHandler(HttpMediaTypeException.class)
    public ApiResponse<?> handleHttpMediaTypeException(Exception e) {
        log.info("415 UNSUPPORTED_MEDIA_TYPE exception occurred: {}", e.getMessage(), e);
        return createResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, e);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResponse<?> handleMethodNotAllowedException(Exception e) {
        log.info("405 METHOD_NOT_ALLOWED HttpRequestMethodNotSupportedException occurred: {}", e.getMessage(), e);
        return createResponse(HttpStatus.METHOD_NOT_ALLOWED, e);
    }

    @ExceptionHandler(GlobalException.class)
    public ApiResponse<?> handleGlobalException(GlobalException e) {
        log.error("Unexpected GlobalException occurred: {}", e.getMessage(), e);
        String message = getMessage(e.getMessage(), e.getParams());
        return createResponse(HttpStatus.INTERNAL_SERVER_ERROR, message, e.getMessage());
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ApiResponse<?> handleException(Exception e) {
        log.error("Unexpected exception occurred: {}", e.getMessage(), e);
        return createResponse(HttpStatus.INTERNAL_SERVER_ERROR, e);
    }

    private String getMessage(String code, Object[] params) {
        return messageSource.getMessage(code, params, Locale.KOREA);
    }

    private ApiResponse<?> createResponse(HttpStatus status, Throwable throwable) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return ApiResponse.error(throwable, status);
    }
    private ApiResponse<?> createResponse(HttpStatus status, String message, String messageCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return ApiResponse.error(message, messageCode, status);
    }
}
