package observer;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncGuavaEventTest {

  public static void main(String[] args) {
    ExecutorService executor = Executors.newFixedThreadPool(10);
    //noinspection UnstableApiUsage
    EventBus eventBus = new AsyncEventBus("Async", executor);
    System.out.println("main:" + Thread.currentThread().toString());
    eventBus.register(new GuavaEventListener());
    eventBus.register(new GuavaEventListener());
    eventBus.register(new GuavaEventListener());
    eventBus.post(new Object());
    eventBus.post(new ArrayList<>());
    eventBus.post("new Str()");
    System.out.println("main over");
    executor.shutdown();
  }


}
