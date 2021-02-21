package observer;

import com.google.common.eventbus.EventBus;
import java.util.ArrayList;

/**
 *  guava消息总线测试demo（一次只能处理一个）
 */
public class GuavaEventTest {

  public static void main(String[] args) {
    //noinspection UnstableApiUsage
    EventBus eventBus = new EventBus("sync");    // 定义消息总线
    System.out.println("main:" + Thread.currentThread().toString());

    // 增加三个监听者，每个监听者都会处理事件
    eventBus.register(new GuavaEventListener("1"));
    eventBus.register(new GuavaEventListener("1"));
    eventBus.register(new GuavaEventListener("1"));
    eventBus.post(new Object());
    eventBus.post(new ArrayList<>());
    System.out.println("main over");
  }

}
