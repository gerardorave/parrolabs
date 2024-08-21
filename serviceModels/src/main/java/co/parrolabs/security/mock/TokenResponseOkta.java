package co.parrolabs.security.mock;

import co.parrolabs.security.OktaTokenResponse;

public class TokenResponseOkta {

    public static OktaTokenResponse oktaTokenResponseOauth(){
        OktaTokenResponse response = new OktaTokenResponse();
        response.setIdToken("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6InNLV29IZWF5dXlYdUpOeW45REM2SiJ9."+
                "eyJpc3MiOiJodHRwczovL2Rldi1tb3g1bGx4dXk3Z2VlM3FyLnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJRdnViQlN6Zz"+
                "JYeWEwRzR6YVBTT1RyeE1sanNGMEh4RkBjbGllbnRzIiwiYXVkIjoiaHR0cDovL3BydWViYTItYXBpIiwiaWF0IjoxNj"+
                "gwODE1NjcxLCJleHAiOjE2ODA5MDIwNzEsImF6cCI6IlF2dWJCU3pnMlh5YTBHNHphUFNPVHJ4TWxqc0YwSHhGIiwiZ3"+
                "R5IjoiY2xpZW50LWNyZWRlbnRpYWxzIn0.q4HEWambp2NrC6yoItfhPwJ7H_QsMy1S8_fkYzlHBXBtQpJpfHlnpwAQl"+
                "WHs0NhEHsqV_BBLh9b38CMzghn8yZ7wIJhFW3wk6lxBnv2sxxTcNDDHM4du2sYThkM6SD8tbC2IMO06Z9v1Qa0-VT0_tCe"+
                "gfJMHxWTme8S-GOZORTwTfK8iYvssJzF_q1_uKX6yFjpVMIp8fLV6tX8X_nrpL5Ssx4U8Yme43GJ1KYqcCARZFHm5do3HPr"+
                "7uN-HgVRKWwo9okxg0BJ20ksSvWtjub_KLOCq4ViTV2W_6-AhbQePB3jc3iSL-43C2Vkk8HUBG5I_IkDSDU2GKLR-oilE8Bw");
        return response;
    }
}
