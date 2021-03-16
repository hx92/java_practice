package com.hx92.practice.concurrency.demos;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号量
 */
public class SemaphoreTest {

  public static void main(String[] args) {
    ExecutorService service = Executors.newCachedThreadPool();
    int permitCount = 1;
    final Semaphore sp = new Semaphore(permitCount);
    sp.release();
    sp.release(3);
    permitCount++;
    permitCount += 3;
    for (int i = 0; i < 10; i++) {
      int finalPermitCount = permitCount;
      Runnable runnable = () -> {
        try {
          sp.acquire();
        } catch (InterruptedException e1) {
          e1.printStackTrace();
        }
        System.out.println("线程" + Thread.currentThread().getName() + "进入，当前已有" + (finalPermitCount - sp.availablePermits()) + "个并发");
        try {
          Thread.sleep((long) (Math.random() * 10000));
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println("线程" + Thread.currentThread().getName() + "即将离开");
        sp.release();
        //下面代码有时候执行不准确，因为其没有和上面的代码合成原子单元
        System.out.println("线程" + Thread.currentThread().getName() + "已离开，当前已有" + (finalPermitCount - sp.availablePermits()) + "个并发");
      };
      service.execute(runnable);
    }
    service.shutdown();
  }

}
