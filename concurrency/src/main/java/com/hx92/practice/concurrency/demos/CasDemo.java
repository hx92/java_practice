package com.hx92.practice.concurrency.demos;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import org.springframework.objenesis.instantiator.util.UnsafeUtils;
import sun.misc.Unsafe;

/**
 * CAS示例
 */
public class CasDemo {

  private static final int loopTimes = 100;
  static Unsafe unsafe = UnsafeUtils.getUnsafe();

  /**
   * addBean模拟add操作 <br/>其中，CasAddBean 是cas版本<br/> DirectAddBean线程不安全的版本<br/>
   *
   * <br/>executorComplionVer： 使用executorCompletionService实现多线程
   *
   * <br/>threadVer： 直接使用thread实现多线程
   *
   * <hr/> <br/>结果：
   * <br/><pre>
   *   num=100
   *   num=100
   *   cas over
   *   num=98
   *   num=96
   *   normal over
   * </pre>
   */
  public static void main(String[] args) throws InterruptedException, ExecutionException {
    System.out.println("cas start");
    threadVer(new CasAddBean());
    executorComplionVer(new CasAddBean());
    System.out.println("cas over");
    System.out.println("thread unsafe start");
    threadVer(new DirectAddBean());
    executorComplionVer(new DirectAddBean());
    System.out.println("thread unsafe over");
  }

  interface AddBean {

    void add();

    int getNum();
  }

  static class DirectAddBean implements AddBean {

    private int num;

    public void add() {
      num++;
    }

    @Override
    public int getNum() {
      return num;
    }
  }

  static class CasAddBean implements AddBean {

    private int num;

    public void add() {
      for (; ; ) {
        int current = num;
        int newNum = current + 1;

        long num = 0;
        try {
          num = unsafe.objectFieldOffset(CasAddBean.class.getDeclaredField("num"));
        } catch (NoSuchFieldException e) {
          e.printStackTrace();
        }
        if (unsafe.compareAndSwapInt(this, num, current, newNum)) {
          break;
        }
      }
    }

    @Override
    public int getNum() {
      return num;
    }

  }


  public static void executorComplionVer(AddBean casBean) throws InterruptedException, ExecutionException {
    ExecutorCompletionService<Integer> executorCompletionService = new ExecutorCompletionService<>(Executors.newFixedThreadPool(10,
        r -> {
          Thread thread = new Thread(r);
          thread.setDaemon(true);
          return thread;
        }));
    for (int i = 0; i < loopTimes; i++) {
      executorCompletionService.submit(getAdd(casBean), i);
    }
    for (int i = 0; i < loopTimes; i++) {
      executorCompletionService.take().get();
    }
    System.out.println("num=" + casBean.getNum());
  }

  public static void threadVer(AddBean casBean) throws InterruptedException {

    List<Thread> threads = Lists.newArrayList();
    for (int i = 0; i < loopTimes; i++) {
      Thread thread = new Thread(getAdd(casBean));
      thread.setDaemon(true);
      thread.start();
      threads.add(thread);
    }
    for (Thread thread : threads) {
      thread.join();
    }
    System.out.println("num=" + casBean.getNum());
  }

  private static Runnable getAdd(AddBean casBean) {
    return () -> {
      try {
        Thread.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      casBean.add();
    };
  }
}
