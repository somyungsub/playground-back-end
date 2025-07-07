package prototype.hexa.common.api;

record ApiHeader(
        boolean success,
        String message,
        String messageCode,
        int status
) {
    private static final String SUCCESS_MESSAGE = "success";

    static ApiHeader ok() {
        return new ApiHeader(true, SUCCESS_MESSAGE, "OK", 200);
    }

    static ApiHeader error(ApiError apiError) {
        return new ApiHeader(false, apiError.message(), apiError.messageCode(), apiError.status());
    }

}
