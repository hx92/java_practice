package com.hx92.practice.concurrency.demos;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionCommunication {

  /**
   *
   */
  public static void main(String[] args) {

    final Business business = new Business();
    new Thread(
        () -> {
          for (int i = 1; i <= 50; i++) {
            business.sub(i);
          }
        }
    ).start();
    new Thread(
        () -> {
          for (int i = 1; i <= 50; i++) {
            business.sub(i);
          }
        }
    ).start();
    new Thread(
        () -> {
          for (int i = 1; i <= 50; i++) {
            business.sub(i);
          }
        }
    ).start();

    for (int i = 1; i <= 150; i++) {
      business.main(i);
    }

  }

  static class Business {

    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    private boolean bShouldSub = true;

    public void sub(int i) {
      lock.lock();
      try {
        while (!bShouldSub) {
          try {
            condition.await();
          } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
//        for (int j = 1; j <= 10; j++) {
//          System.out.println("sub thread sequence of " + j + ",loop of " + i);
//        }
        System.out.println(Thread.currentThread().getName() + " i=" + i);
        bShouldSub = false;
        condition.signalAll();
      } finally {
        lock.unlock();
      }
    }

    public void main(int i) {
      lock.lock();
      try {
        while (bShouldSub) {
          try {
            condition.await();
          } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
//        for (int j = 1; j <= 100; j++) {
//          System.out.println("main thread sequence of " + j + ",loop of " + i);
//        }
        System.out.println(Thread.currentThread().getName() + " i=" + i);
        bShouldSub = true;
        condition.signalAll();
      } finally {
        lock.unlock();
      }
    }

  }
}
