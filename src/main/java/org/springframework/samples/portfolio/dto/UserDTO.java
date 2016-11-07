package org.springframework.samples.portfolio.dto;

/**
 * Created by thinhdd on 11/7/2016.
 */
public class UserDTO {
    String userName;
    String password;
    String token;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
