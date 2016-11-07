package org.springframework.samples.portfolio.service;

import com.gs.collections.impl.map.mutable.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.portfolio.dto.UserDTO;
import org.springframework.samples.portfolio.entity.Token;
import org.springframework.samples.portfolio.entity.User;
import org.springframework.samples.portfolio.repository.TokenRepository;
import org.springframework.samples.portfolio.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.support.UUIDUtils;

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

    @Autowired
    TokenRepository tokenRepository;

    Map<String, List<String>> userSessionMap = new ConcurrentHashMap<String, List<String>>();

    @Override
    public List<String> findSessionUser(String userName) {
        User user = userRepository.findUserByUserName(userName);
        if (user != null && userSessionMap.containsKey(user.getId())) {
            return userSessionMap.get(userName);
        }
        return new ArrayList<String>();
    }

    @Override
    public void validateUser(String tokenData, String sessionId) throws IllegalArgumentException {

        Token token = tokenRepository.findTokenByTokenData(tokenData);
        if (token == null) {
            throw new IllegalArgumentException("User not existed or session login expired date ");
        }
        List<String> sessionIds = userSessionMap.get(token.getUserId());
        if (sessionIds == null) {
            sessionIds = new ArrayList<String>();
            sessionIds.add(sessionId);
        } else {
            sessionIds.add(sessionId);
        }
        userSessionMap.put(token.getUserId(), sessionIds);
    }

    @Override
    public void removeUserSession(String userName, String sessionId) {
        User user = userRepository.findUserByUserName(userName);
        if (user != null) {
            List<String> sessionIds = userSessionMap.get(user.getId());
            if (sessionIds != null) {
                sessionIds.remove(sessionId);
                userSessionMap.put(user.getId(), sessionIds);
            }
        }

    }

    @Override
    public UserDTO login(UserDTO userDTO) throws Exception {
        User user = userRepository.findUserByUserNameAndPassword(userDTO.getUserName(), userDTO.getPassword());
        if(user==null)
        {
            throw new Exception("User not exist");
        }
        // todo you can change way make token for other as follow logic project (hash, rsa ....)
        String tokenGen = String.valueOf(UUIDUtils.random());
        Token token = new Token();
        token.setUserId(user.getId());
        token.setTokenData(tokenGen);
        token = tokenRepository.save(token);
        userDTO.setToken(token.getTokenData());
        return userDTO;
    }
}
