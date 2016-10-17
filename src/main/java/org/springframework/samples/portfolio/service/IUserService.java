package org.springframework.samples.portfolio.service;

import java.util.List;

/**
 * Created by thinhdd on 10/17/2016.
 */
public interface IUserService {

    List<String> findSessionUser(String userName );

    void addSessionUser(String user,String sessionId);

}
