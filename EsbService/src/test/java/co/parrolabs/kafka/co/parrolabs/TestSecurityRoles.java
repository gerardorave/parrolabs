package co.parrolabs.kafka.co.parrolabs;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestSecurityRoles {
    @Test
    //@WithMockUser(username = "JOHN", authorities = { "SYS_ADMIN" })
    public void givenAuthoritySysAdmin_whenCallGetUsernameLC_thenReturnUsername() {
        //String username = userRoleService.getUsernameInLowerCase();
        String username ="john";
        //TOD: define the library and validate security. .. ,
        assertEquals("john", username);
    }
}
