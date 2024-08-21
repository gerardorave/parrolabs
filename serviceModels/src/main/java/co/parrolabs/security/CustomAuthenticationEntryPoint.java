package co.parrolabs.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final String ERROR_MESSAGE = "Error: Please provide valid API credentials";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    Tracer tracer;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        Map<String, String> errorResponseMap = new HashMap<>();

        errorResponseMap.put("timestamp", OffsetDateTime.now(ZoneOffset.UTC).toString());
        errorResponseMap.put("status", String.valueOf(HttpStatus.FORBIDDEN.value()));
        errorResponseMap.put("error", HttpStatus.FORBIDDEN.getReasonPhrase());
        errorResponseMap.put("message", ERROR_MESSAGE);
        errorResponseMap.put("path", request.getRequestURI());
        if (tracer.currentSpan() != null && Objects.requireNonNull(tracer.currentSpan()).context() != null) {
            errorResponseMap.put("correlationId", Objects.requireNonNull(tracer.currentSpan()).context().traceId());
        }

        final String errorResponseJson = objectMapper.writeValueAsString(errorResponseMap);

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(errorResponseJson);
    }


}
