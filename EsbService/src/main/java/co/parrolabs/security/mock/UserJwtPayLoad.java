package co.parrolabs.security.mock;

import co.parrolabs.security.User;

import java.util.Arrays;

public class UserJwtPayLoad {

    public static User userJwtMocked() {
        User user= new User();
        user.setId("QvubBSzg2Xya0G4zaPSOTrxMljsF0HxF");
        user.setEmail("gerardorave@gmail.com");
        user.setGroups(Arrays.asList(new String[]{"admin", "user"}));
        return user;
    }
}
