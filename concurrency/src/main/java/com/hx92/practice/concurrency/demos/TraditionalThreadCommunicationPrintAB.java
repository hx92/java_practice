package com.hx92.practice.concurrency.demos;

/**
 * 线程间通信 ，交替打印A、B
 */
public class TraditionalThreadCommunicationPrintAB {

  public static void main(String[] args) throws InterruptedException {

    final BizBean business = new BizBean();
    new Thread(
        () -> {
          for (int i = 1; i <= 50; i++) {
            try {
              business.workA();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }
    ).start();
    new Thread(
        () -> {
          for (int i = 1; i <= 50; i++) {
            try {
              business.workA();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }
    ).start();
    new Thread(
        () -> {
          for (int i = 1; i <= 50; i++) {
            try {
              business.workA();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }
    ).start();
    new Thread(
        () -> {
          for (int i = 1; i <= 50; i++) {
            try {
              business.workB();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }
    ).start();
    new Thread(
        () -> {
          for (int i = 1; i <= 50; i++) {
            try {
              business.workB();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }
    ).start();
    new Thread(
        () -> {
          for (int i = 1; i <= 50; i++) {
            try {
              business.workB();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }
    ).start();

  }

  static class BizBean {

    private boolean printA = true;

    synchronized void workA() throws InterruptedException {
      while (!printA) {
        synchronized (this) {
          this.wait();
        }
      }
      System.out.println("A");
      printA = false;
      synchronized (this) {
        this.notifyAll();
      }
    }

    synchronized void workB() throws InterruptedException {
      while (printA) {
        this.wait();
      }
      System.out.println("B");

      printA = true;
      synchronized (this) {
        this.notifyAll();
      }
    }
  }

}
