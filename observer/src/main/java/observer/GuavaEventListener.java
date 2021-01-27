package observer;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class GuavaEventListener {

  @AllowConcurrentEvents
  @Subscribe
  public void subscribeList(List<String> list) throws InterruptedException {
    System.out.println("GuavaEvent triggered");
    System.out.println("GuavaEvent:" + Thread.currentThread().toString());
    System.out.println(list.size());
    Thread.sleep(1000);
    System.out.println("GuavaEvent trigger ended");
  }

  @AllowConcurrentEvents
  @Subscribe
  public void sub(String str) throws InterruptedException {
    System.out.println("strGuavaEvent triggered");
    System.out.println("strGuavaEvent:" + Thread.currentThread().toString());
    System.out.println(str);
    Thread.sleep(1000);
    System.out.println("strGuavaEvent trigger ended");
  }
}
