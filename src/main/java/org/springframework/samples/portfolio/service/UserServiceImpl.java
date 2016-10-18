package org.springframework.samples.portfolio.service;

import com.gs.collections.impl.map.mutable.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.portfolio.entity.User;
import org.springframework.samples.portfolio.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by thinhdd on 10/17/2016.
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserRepository userRepository;

    Map<String, List<String>> userSessionMap = new ConcurrentHashMap<String, List<String>>();

    @Override
    public List<String> findSessionUser(String userName) {
        if(userSessionMap.containsKey(userName)){
            return userSessionMap.get(userName);
        }
        return new ArrayList<String>();
    }

    @Override
    public void validateUser(String userName, String sessionId) throws IllegalArgumentException {

        User user = userRepository.findUserByUserName(userName);
        if(user!=null)
        {
            throw new IllegalArgumentException("User not existed");
        }
        List<String> sessionIds = userSessionMap.get(userName);
        if(sessionIds==null)
        {
            sessionIds = new ArrayList<String>();
            sessionIds.add(sessionId);
        }
        else
        {
            sessionIds.add(sessionId);
        }
        userSessionMap.put(userName, sessionIds);
    }

    @Override
    public void removeUserSession(String userName, String sessionId) {
        List<String> sessionIds = userSessionMap.get(userName);
        if(sessionIds!=null)
        {
            sessionIds.remove(userName);
        }
    }
}
