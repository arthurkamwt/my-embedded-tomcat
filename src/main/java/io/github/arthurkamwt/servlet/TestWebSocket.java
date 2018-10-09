package io.github.arthurkamwt.servlet;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;

@ServerEndpoint("/websocket")
@Slf4j
public class TestWebSocket {

  @OnOpen
  public void onOpen(Session session) {
    log.info("ya want something?");
  }

  @OnMessage
  public void onMessage(String message, Session session) {
    log.info("got ye ws message, {}", message);

    final RemoteEndpoint.Async re = session.getAsyncRemote();
    re.sendText("aye we got em");
  }

}
