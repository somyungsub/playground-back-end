package prototype.hexa.common.api;

record ApiError(
        String message,
        String messageCode,
        int status
) {
    static ApiError of(String message, String messageCode, int status) {
        return new ApiError(message, messageCode, status);
    }
}
