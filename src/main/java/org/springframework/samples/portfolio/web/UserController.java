/*
 * Copyright 2002-2015 the original author or authors.
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
package org.springframework.samples.portfolio.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.samples.portfolio.dto.UserDTO;
import org.springframework.samples.portfolio.repository.UserRepository;
import org.springframework.samples.portfolio.service.IUserService;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
public class UserController {

    private SimpUserRegistry userRegistry;
    @Autowired
    IUserService userService;

    @Autowired
    public UserController(SimpUserRegistry userRegistry) {
        this.userRegistry = userRegistry;
    }
    @RequestMapping(value="/",method = RequestMethod.GET)
    public String homepage(){
        return "index";
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<String> listUsers() {
        List<String> result = new ArrayList<String>();
        for (SimpUser user : this.userRegistry.getUsers()) {
            result.add(user.toString());
        }
        return result;
    }

    @SubscribeMapping("/hello")
    @SendToUser("/topic/helloAll")
    public String helloAll(Message<Object> message) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);
        String userName = sha.getLogin();

        return userName + ":" + message.getPayload();
    }

    @PostMapping
    @RequestMapping(value="/login",method = RequestMethod.POST)
    public UserDTO login(@RequestBody UserDTO userDTO) throws Exception {
        return userService.login(userDTO);
    }
}
