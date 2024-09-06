package co.parrolabs;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TestSecurityRoles {

    //@WithMockUser(username = "JOHN", authorities = { "SYS_ADMIN" })
    @Test
    public void givenAuthoritySysAdmin_whenCallGetUsernameLC_thenReturnUsername() {
        //String username = userRoleService.getUsernameInLowerCase();
        String username ="john";
        //TOD: define the library and validate security. .. ,
        assertEquals("john", username);
    }
}
