package test.io.nio;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.UUID;

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
