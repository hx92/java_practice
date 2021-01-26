package com.hx92.practice.nio;

public class SocketClientTest {

  public static void main(String[] args)  {
    SocketClient socketClient = new SocketClient(8080);
    socketClient.process();
    socketClient.process();
    socketClient.process();
    socketClient.process();
    socketClient.process();
    socketClient.process();
  }
}
