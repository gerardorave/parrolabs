package co.parrolabs.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

//Outside of application service package as serialization/deserialization
//depends on the fully qualified package name.
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OktaTokenDto implements Serializable {

    private String cacheKey;

    private String email;

    private String idToken;

    private Instant createdAt;
}
