package com.hx92.practice.concurrency.demos;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CacheDemo {

  private Map<String, Object> cache = new HashMap<String, Object>();

  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

  private ReadWriteLock rwl = new ReentrantReadWriteLock();

  public Object getData(String key) {
    try {
      rwl.readLock().lock();
      Object value = cache.get(key);
      if (value == null) {
        rwl.readLock().unlock();
        rwl.writeLock().lock();
        try {
          value = cache.get(key);
          if (value == null) {
            value = queryDB();
          }
        } finally {
          rwl.writeLock().unlock();
        }
        rwl.readLock().lock();
      }
      return value;
    } finally {
      rwl.readLock().unlock();
    }
  }

  private String queryDB() {
    return "queryDB()";
  }
}
