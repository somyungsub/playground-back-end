package prototype.hexa.common.exception;

import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Set;

@Getter
public class GlobalException extends RuntimeException {
    private Object[] params;
    public GlobalException(String message) {
        super(message);
    }
    public GlobalException(String message, Object[] params) {
        super(message);
        this.params = params;
    }
    public GlobalException(String message, Object param) {
        super(message);
        this.params = ArrayUtils.toArray(param);
    }
}
