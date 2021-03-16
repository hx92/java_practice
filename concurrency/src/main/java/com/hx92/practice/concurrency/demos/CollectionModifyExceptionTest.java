package com.hx92.practice.concurrency.demos;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollectionModifyExceptionTest {

  public static void main(String[] args) {
    Collection<User> users = new CopyOnWriteArrayList<>();

    //new ArrayList();
    users.add(new User("张三", 28));
    User user1 = new User("李四", 25);
    users.add(user1);
    users.add(new User("王五", 31));
    for (User user : users) {

      if ("李四".equals(user.getName())) {
        users.remove(user);
      } else {
        System.out.println(user);
      }
      users.clear();
    }
  }
}	 
