package co.parrolabs.security.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class AuthorizationConstants {
    public static final String ADMIN_API_GROUP = "hasAuthority('admin')";
}