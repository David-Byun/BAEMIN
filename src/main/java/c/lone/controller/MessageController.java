package c.lone.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    /*
        메시지 전송에는 @MessageMapping을 사용하고 주소에 들어가는 변수값은 @PathVariable 대신 @DestinationVariable 을 사용
        사용자가 주문을 완료할 시 /order-complete-message/{storeId}로 메세지를 보냅니다

        WebSocketConfig에서 설정한 setApplicationDestinationPrefixes로 인해 실제 주소는
        /message/order-complete-message/{storeId} 이지만 message가 생략됩니다.

        사용자 주문 완료 > /message/order-complete-message/1과 같은 주소로 메세지를 보냄 > 컨트롤러에서 StoreId와 message를 받고
        SendTo에 지정된 주소로 message 리턴
     */

    @MessageMapping("/order-complete-message/{storeId}")
    @SendTo("/topic/order-complete/{storeId}")
    public String message(@DestinationVariable long storeId, String message) {
        return message;
    }
}
