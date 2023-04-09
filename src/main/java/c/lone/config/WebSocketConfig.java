package c.lone.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/*
    주문 들어올 경우 새로고침해야만 알수 있기 때문에, 웹소켓과 STOMP를 이용하여 새로운 주문이 접수되면 관리자페이지에 메세지를 보내고,
    이 메시지를 수신하면 새로운 주문 데이터를 서버에 요청하도록 추가
 */

@EnableWebSocketMessageBroker
@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    /*
        sockJS 클라이언트가 Websocket 핸드셰이크를 하기 위해 연결할 endpoint를 지정해야 하는데 클라이언트가 연결되고
        http://localhost:8080/endpoint/info?t=??으로 웹소켓 통신이 가능한지 확인한 후
        응답이 websocket:true이면 웹소켓이 연결 됨
        enableSimpleBroker은 특정 주소가 붙은 클라이언트에게 메세지를 전송하며 setApplicationDestinationPrefixes
        은 클라이언트에게서 메세지를 받을 서버주소의 경로임.
     */

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/message");
    }

}
