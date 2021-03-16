package com.hx92.practice.concurrency.demos;

public class TraditionalThreadInterruptTest {

  private static final Object monitor = new Object();

  public static void main(String[] args) throws InterruptedException {
    interrptThreadTest(() -> {
      System.out.println("enter thread");
      System.out.println("isInterrupted:" + Thread.currentThread().isInterrupted());//isInterrupted 不会清除中断状态
      System.out.println("comment:isInterrupted 不会清除中断状态");
    });
    interrptThreadTest(() -> {
      System.out.println("enter thread");
      System.out.println("interrupted:" + Thread.interrupted());//interrupted 会清除中断状态
      System.out.println("comment:interrupted 会清除中断状态");
    });
    interrptThreadTest(() -> {
      System.out.println("enter thread");
      synchronized (monitor) {
        try {
          System.out.println("before wait");
          monitor.wait();// wait sleep 会清除中断状态
          System.out.println("after wait");
        } catch (InterruptedException e) {
          System.out.println("InterruptedException wait");
        }
        System.out.println("comment:wait sleep 会清除中断状态");
      }
    });
    interrptThreadTest(() -> {
      try {
        System.out.println("before sleep 2s");
        Thread.sleep(2000);
        System.out.println("after sleep 2s");
      } catch (InterruptedException e) {
        System.out.println("InterruptedException sleep");
      }
      System.out.println("comment:wait sleep 会清除中断状态");
    });

    synchronized (monitor) {
      try {
        System.out.println(" main start wait");
        monitor.wait(1000);// wait 超时后直接返回，不会抛出异常
        System.out.println(" main after wait");
        System.out.println("comment: wait 超时后直接返回，不会抛出异常");
      } catch (InterruptedException ignored) {
      }
    }
  }

  private static void interrptThreadTest(Runnable runnable) throws InterruptedException {
    Thread thread = new Thread(() -> {
      System.out.println(Thread.currentThread().getName() + ":isInterrupted:" + Thread.currentThread().isInterrupted() + ", thread start");
      runnable.run();
      System.out.println(Thread.currentThread().getName() + ":isInterrupted:" + Thread.currentThread().isInterrupted() + ", thread end");
    });
    thread.start();
    System.out.println("ready to interrupt");
    thread.interrupt();
    System.out.println("after interrupted");
    thread.join();
    System.out.println("-----------------------------------------");
  }

}
