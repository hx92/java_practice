package observer;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("UnstableApiUsage")
public class GuavaEventListener {

  private String listenerId;

  public GuavaEventListener(String listenerId) {
    this.listenerId = listenerId;
  }

  @AllowConcurrentEvents
  @Subscribe
  public void subscribeList(List<String> list) throws InterruptedException {
    System.out.println("GuavaEvent triggered");
    System.out.println("GuavaEvent:" + Thread.currentThread().toString() + ",list:" + list.size());
    Thread.sleep(1000);
    System.out.println("GuavaEvent trigger ended");
    System.out.println("-------");
  }

  @AllowConcurrentEvents
  @Subscribe
  public void sub(String str) throws InterruptedException {
    System.out.println("strGuavaEvent triggered");
    System.out.println("strGuavaEvent:" + Thread.currentThread().toString() + ",str:" + str);
    Thread.sleep(1000);
    System.out.println("strGuavaEvent trigger ended");
    System.out.println("-------");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    GuavaEventListener that = (GuavaEventListener) o;

    return Objects.equals(listenerId, that.listenerId);
  }

  @Override
  public int hashCode() {
    return listenerId != null ? listenerId.hashCode() : 0;
  }
}
