package co.parrolabs.error;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class GenericServiceException extends RuntimeException {

    public GenericServiceException(String message) {
        super(message);
    }

    public GenericServiceException(String message, Throwable e) {
        super(message, e);
    }
}
