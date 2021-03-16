package com.hx92.practice.concurrency.demos;

import java.util.Random;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class CallableAndFuture {

  /**
   *
   */
  public static void main(String[] args) {
    ExecutorService threadPool = Executors.newSingleThreadExecutor();
    Future<String> future = threadPool.submit(
        () -> {
          Thread.sleep(2000);
          return "hello";
        }
    );
    System.out.println("等待结果");
    try {
      System.out.println("拿到结果：" + future.get());
    } catch (Exception e) {
      e.printStackTrace();
    }

    ExecutorService threadPool2 = Executors.newFixedThreadPool(10);
    CompletionService<Integer> completionService = new ExecutorCompletionService<>(threadPool2);
    for (int i = 1; i <= 10; i++) {
      final int seq = i;
      completionService.submit(() -> {
        Thread.sleep(new Random().nextInt(5000));
        return seq;
      });
    }
    for (int i = 0; i < 10; i++) {
      try {
        System.out.println(completionService.take().get());
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }
  }


}
