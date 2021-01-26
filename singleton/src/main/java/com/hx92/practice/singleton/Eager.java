package com.hx92.practice.singleton;

public class Eager<T> {

  public static final Eager instance = new Eager();


  static {
    i = 10;
    System.out.println("static block:" + Eager.class.getSimpleName() + " static block ~~" + "instance.i=" + instance.i);
  }

  static int i = 1;

  Eager() {
    System.out.println("Eager class initialized ,i=" + i + ",eager constructed");
  }

  @Override
  public String toString() {
    return "instance=" + super.toString() + ",i=" + i;
  }
}
