package observer;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * guava 异步消息总线
 */
public class AsyncGuavaEventTest {

  public static void main(String[] args) {
    ExecutorService executor = Executors.newFixedThreadPool(10);
    //noinspection UnstableApiUsage
    EventBus eventBus = new AsyncEventBus("Async", executor);
    System.out.println("main:" + Thread.currentThread().toString());
    // 注册监听者
    eventBus.register(new GuavaEventListener("1"));
    eventBus.register(new GuavaEventListener("1"));
    eventBus.register(new GuavaEventListener("1"));
    eventBus.post(new Object());
    eventBus.post(new ArrayList<>());
    eventBus.post("new Str()");
    System.out.println("main over");
    executor.shutdown();
  }


}
