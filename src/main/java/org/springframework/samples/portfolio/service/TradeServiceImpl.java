/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.portfolio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.samples.portfolio.dto.MessageDTO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class TradeServiceImpl implements TradeService {

    @Autowired
    IUserService userService;
    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public TradeServiceImpl(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }


    @Scheduled(fixedDelay = 1500)
    public void sendSpecificUser() {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessage("Giấy xác nhận chuyển tiền");
        messageDTO.setTitle("Xác nhận chuyển tiền");

        for (String sessionId : userService.findSessionUser("thinhdd")) {
            this.messagingTemplate.convertAndSendToUser(sessionId, "/queue/position-updates", messageDTO, createHeaders(sessionId));
        }
    }

    @Scheduled(fixedDelay = 1500)
    public void sendAllUser() {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessage("Giấy xác nhận chuyển tiền");
        messageDTO.setTitle("Xác nhận chuyển tiền");
        this.messagingTemplate.convertAndSend("/queue/for-me", messageDTO);
    }

    private MessageHeaders createHeaders(String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }

}
