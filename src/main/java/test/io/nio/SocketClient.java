package test.io.nio;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.UUID;

public class SocketClient {

  private int port;

  public SocketClient(int port) {
    this.port = port;
  }

  public void process() {
    new Thread(() -> {
      try {
        Socket socket = new Socket(InetAddress.getLocalHost(), port);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(UUID.randomUUID().toString().getBytes());
        InputStream inputStream = socket.getInputStream();
        System.out.println("read from server: " + new String(inputStream.readAllBytes()));
      } catch (Exception ex) {
        ex.printStackTrace();
      }

    }).start();
  }
}
