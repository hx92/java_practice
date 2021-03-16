package com.hx92.practice.concurrency.demos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExchangerTest {

  public static void main(String[] args) {
    ExecutorService service = Executors.newCachedThreadPool();
    final Exchanger<String> exchanger = new Exchanger<>();
    service.execute(() -> {
      exchangeFunc(exchanger, "fund_price");
    });
    service.execute(() -> {
      exchangeFunc(exchanger, "fund_nav");
    });
    service.shutdown();
  }

  private static void exchangeFunc(Exchanger<String> exchanger, String dataName) {
    for (int i = 0; i < 2; i++) {
      try {
        String data1 = dataName;
        double v = Math.random() * 10;
        System.out.println(LocalTime.now()+"线程" + Thread.currentThread().getName() +
            "正在把数据##" + data1 + "##处理中,"+v);
        Thread.sleep((long) v * 1000);
        System.out.println(LocalTime.now()+"线程" + Thread.currentThread().getName() +
            "正在把数据##" + data1 + "##换出去,"+v);
        String data2 = exchanger.exchange(data1);
        dataName = data2;
        System.out.println(LocalTime.now()+"线程" + Thread.currentThread().getName() +
            "换回的数据为##" + data2);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
