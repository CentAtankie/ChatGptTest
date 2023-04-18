package com.tencent.wxcloudrun.service.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import javax.websocket.Session;
import java.io.IOException;

/**
 * @Description
 * @Author CentAtankie
 **/
@Component
public class MyWebSocketHandler extends AbstractWebSocketHandler {

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        // 处理消息
        System.out.println(1);
        session.sendMessage(new TextMessage("222"));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 连接建立
        System.out.println(2);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 连接关闭
    }

    private void sendTextMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException ex) {
            System.out.println("Error sending message: " + ex.getMessage());
        }
    }
}
