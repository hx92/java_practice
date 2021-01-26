package com.hx92.practice.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioServerDemo {

  private int port;
  private Selector selector;
  private ByteBuffer buffer = ByteBuffer.allocate(1024);

  NioServerDemo(int port) throws IOException {
    this.port = port;
    ServerSocketChannel server = ServerSocketChannel.open();
    server.bind(new InetSocketAddress(port));
    // 设置为非阻塞
    server.configureBlocking(false);
    // 注册一个selector
    selector = Selector.open();
    server.register(selector, SelectionKey.OP_ACCEPT);
  }

  public static void main(String[] args) throws IOException {
    new NioServerDemo(8080).listen();

  }

  public void listen() throws IOException {
    System.out.println("listen on port:" + this.port);
    // 轮询请求
    //
    while (true) {
      selector.select();
      Set<SelectionKey> keys = selector.selectedKeys();
      System.out.println("read keys size:"+keys.size());
      for (Iterator<SelectionKey> iterator = keys.iterator(); iterator.hasNext(); ) {
        SelectionKey key = iterator.next();
        iterator.remove();
        processKey(key);
      }
    }

  }

  private void processKey(SelectionKey key) throws IOException {
    if (key.isAcceptable()) {
      ServerSocketChannel server = (ServerSocketChannel) key.channel();
      SocketChannel channel = server.accept();
      channel.configureBlocking(false);
      // 准备就绪后，改为已读
      channel.register(selector, SelectionKey.OP_READ);
    } else if (key.isReadable()) {
      SocketChannel channel = (SocketChannel) key.channel();
      if (channel.read(buffer) > 0) {
        ByteBuffer flip = buffer.flip();
        String content = new String(flip.array());
        // 准备就绪后，改为可写
        SelectionKey selectionKey = channel.register(selector, SelectionKey.OP_WRITE);
        // key 上 带一个内容
        selectionKey.attach(content);
        System.out.println("receive content:" + content);
      }
    } else if (key.isWritable()) {
      SocketChannel channel = (SocketChannel) key.channel();
      channel.configureBlocking(false);
      channel.write(ByteBuffer.wrap(key.attachment().toString().getBytes()));
      // 准备就绪后，改为已读
      channel.close();
    }
  }

}
