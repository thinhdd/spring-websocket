package org.springframework.samples.portfolio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.samples.portfolio.interceptor.ClientConnectInterceptorHandler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableScheduling
@ComponentScan("org.springframework.samples")
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/portfolio").withSockJS();
	}

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/queue/", "/topic/");
        registry.enableStompBrokerRelay("/queue/", "/topic/").setRelayHost("localhost").setRelayPort(61613);
        registry.setApplicationDestinationPrefixes("/app");
    }


    @Override
    public void configureClientInboundChannel(final ChannelRegistration registration) {
        registration.taskExecutor().corePoolSize(1);
        registration.setInterceptors(clientConnectInterceptorHandler());
    }

    @Override
    public void configureClientOutboundChannel(final ChannelRegistration registration) {
        registration.taskExecutor().corePoolSize(2);
    }

    @Bean
    ClientConnectInterceptorHandler clientConnectInterceptorHandler(){
        return new ClientConnectInterceptorHandler();
    };


}
