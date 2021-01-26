package com.hx92.practice.singleton;

public class StaticSingleton {
  private final static StaticSingleton instance = new StaticSingleton();

  public static StaticSingleton getInstance() {
    return instance;
  }
}
