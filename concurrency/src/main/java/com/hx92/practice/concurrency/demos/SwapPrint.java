package com.hx92.practice.concurrency.demos;

import java.util.LinkedList;

/**
 * Created by Hollake on 2019\6\24 0024 20:41.
 */
public class SwapPrint {

  public static void main(String[] args) throws InterruptedException {
    LinkedList<String> list = new LinkedList<>();
    list.add("A");
    list.add("B");
    list.add("A");
    list.add("B");
    Object a = new Object();
    Object b = new Object();
    MyThread myThread1 = new MyThread(b, a, list);
    MyThread myThread2 = new MyThread(a, b, list);
    (new Thread(myThread1)).start();
    (new Thread(myThread2)).start();

  }
}

class MyThread implements Runnable {

  private LinkedList<String> list;
  private Object pre;
  private Object self;

  public MyThread(Object pre, Object self, LinkedList<String> list) {
    this.self = self;
    this.pre = pre;
    this.list = list;
  }

  @Override
  public void run() {
    while (!list.isEmpty()) {
      synchronized (pre) {//先拿到前一个线程的锁
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        synchronized (self) {//接着拿到自己的锁
          System.out.println(Thread.currentThread() + list.poll());
          self.notify();//唤醒另外一个线程
        }
        try {
          pre.wait();//释放前一个线程的锁，中止当前线程
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

      }
    }
  }

}
