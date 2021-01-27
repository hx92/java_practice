package observer;

import com.google.common.eventbus.EventBus;
import java.util.ArrayList;

public class GuavaEventTest {

  public static void main(String[] args) {
    //noinspection UnstableApiUsage
    EventBus eventBus = new EventBus("Async");
    System.out.println("main:" + Thread.currentThread().toString());
    eventBus.register(new GuavaEventListener());
    eventBus.register(new GuavaEventListener());
    eventBus.register(new GuavaEventListener());
    eventBus.post(new Object());
    eventBus.post(new ArrayList<>());
    System.out.println("main over");
  }

}
