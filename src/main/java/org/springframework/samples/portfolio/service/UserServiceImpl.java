package org.springframework.samples.portfolio.service;

import com.gs.collections.impl.map.mutable.ConcurrentHashMap;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by thinhdd on 10/17/2016.
 */
@Service
public class UserServiceImpl implements IUserService {
    Map<String, List<String>> userSessionMap = new ConcurrentHashMap<String, List<String>>();
    @Override
    public List<String> findSessionUser(String userName) {
        if(userSessionMap.containsKey(userName)){
            return userSessionMap.get(userName);
        }
        return new ArrayList<String>();
    }

    @Override
    public void addSessionUser(String user, String sessionId) {
        List<String> sessionIds = userSessionMap.get(user);
        if(sessionIds==null)
        {
            sessionIds = new ArrayList<String>();
            sessionIds.add(sessionId);
        }
        else
        {
            sessionIds.add(sessionId);
        }
        userSessionMap.put(user, sessionIds);
    }
}
