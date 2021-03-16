package com.hx92.practice.concurrency.demos;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierTest {

  public static void main(String[] args) {
    ExecutorService service = Executors.newCachedThreadPool();
    final CyclicBarrier cb = new CyclicBarrier(3);
    for (int i = 0; i < 3; i++) {
      Runnable runnable = () -> {
        for (int j = 0; j < 10; j++) {
          try {
            Thread.sleep((long) (Math.random() * 1000));
            System.out.println("线程" + Thread.currentThread().getName() +
                "即将到达集合地点" + (j + 1) + "，当前已有" + (cb.getNumberWaiting() + 1) + "个已经到达，" + (cb.getNumberWaiting() == 2 ? "都到齐了，继续走啊" : "正在等候"));
            cb.await();
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      };
      service.execute(runnable);
    }
    service.shutdown();
  }
}
