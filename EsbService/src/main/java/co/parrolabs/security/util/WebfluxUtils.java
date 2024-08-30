package co.parrolabs.security.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.concurrent.TimeoutException;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class WebfluxUtils {

    public static boolean is5xxServerErrorOrTimeout(Throwable throwable) {
        return throwable instanceof WebClientResponseException && ((WebClientResponseException) throwable).getStatusCode().is5xxServerError()
                || throwable instanceof TimeoutException;
    }
/*
    public static boolean is5xxServerError(Throwable throwable) {
        return throwable instanceof WebClientResponseException && ((WebClientResponseException) throwable).getStatusCode().is5xxServerError();
    }

    public static boolean is4xxServerError(Throwable throwable) {
        return throwable instanceof WebClientResponseException && ((WebClientResponseException) throwable).getStatusCode().is4xxClientError();
    }
*/
}