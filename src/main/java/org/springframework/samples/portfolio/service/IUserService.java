package org.springframework.samples.portfolio.service;

import org.springframework.samples.portfolio.dto.UserDTO;

import java.util.List;

/**
 * Created by thinhdd on 10/17/2016.
 */
public interface IUserService {

    List<String> findSessionUser(String userName );

    void validateUser(String token, String sessionId) throws IllegalArgumentException;

    void removeUserSession(String token, String sessionId);

    UserDTO login(UserDTO userDTO) throws Exception;
}
