package org.springframework.samples.portfolio.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.samples.portfolio.service.IUserService;

/**
 * Created by thinhdd on 10/17/2016.
 */
public class ClientConnectInterceptorHandler extends ChannelInterceptorAdapter {

    private static final Logger logger = LoggerFactory
            .getLogger(ClientConnectInterceptorHandler.class);

    @Autowired
    IUserService userService;

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);

        if (sha.getCommand() == null) {
            return;
        }

        String token = sha.getLogin();
        String passcode = sha.getPasscode();
        String sessionId = sha.getSessionId();
        switch (sha.getCommand()) {

            case CONNECT:
                logger.info("STOMP Connect [sessionId: " + sessionId + "]");
                userService.validateUser(token, sessionId);
                break;
            case CONNECTED:
                logger.info("STOMP Connected [sessionId: " + sessionId + "]");
                break;
            case DISCONNECT:
                logger.info("STOMP Disconnect [sessionId: " + sessionId + "]");
                userService.removeUserSession(token, sessionId);
                break;
            default:
                break;

        }
    }
}
