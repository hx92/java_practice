package com.hx92.practice.concurrency.demos;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueuePoisonPill {

  public static void main(String[] args) {
    int POISON_PILL = Integer.MAX_VALUE;
    int BOUND = 10;

    long PRODUCER_NUMBER = 3;
    long CONSUMER_NUMBER = Runtime.getRuntime().availableProcessors();
    long POISON_PILL_NUMBER_PER_PROD = CONSUMER_NUMBER / PRODUCER_NUMBER;
    long mod = CONSUMER_NUMBER % PRODUCER_NUMBER;

    BlockingQueue<Integer> pills = new ArrayBlockingQueue<>(BOUND);
    for (int i = 1; i < PRODUCER_NUMBER; i++) {
      new Thread(new PillProducer(pills, POISON_PILL, POISON_PILL_NUMBER_PER_PROD)).start();
    }

    for (int i = 0; i < CONSUMER_NUMBER; i++) {
      new Thread(new PillConsumer(pills, POISON_PILL)).start();
    }
    new Thread(new PillProducer(pills, POISON_PILL, POISON_PILL_NUMBER_PER_PROD + mod)).start();
  }

  private static class PillProducer implements Runnable {

    private final BlockingQueue<Integer> pills;
    private final Integer POISON_PILL;
    private final Long POISON_PILL_NUMBER;

    public PillProducer(BlockingQueue<Integer> pills, Integer POISON_PILL, Long POISON_PILL_NUMBER) {
      this.pills = pills;
      this.POISON_PILL = POISON_PILL;
      this.POISON_PILL_NUMBER = POISON_PILL_NUMBER;
    }


    @Override
    public void run() {
      try {

        for (int i = 0; i < 100; i++) {
          pills.put(i);
        }
        for (int i = 0; i < POISON_PILL_NUMBER; i++) {
          pills.put(POISON_PILL);
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private static class PillConsumer implements Runnable {

    private final BlockingQueue<Integer> pills;
    private final Integer POISON_PILL;

    public PillConsumer(BlockingQueue<Integer> pills, Integer POISON_PILL) {
      this.pills = pills;
      this.POISON_PILL = POISON_PILL;
    }

    @Override
    public void run() {
      try {
        while (true) {
          Integer take = pills.take();
          if (take.equals(POISON_PILL)) {
            System.out.println(Thread.currentThread() + ":POISON_PILL");
            return;
          }
          System.out.println("pill:" + take);
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

}
