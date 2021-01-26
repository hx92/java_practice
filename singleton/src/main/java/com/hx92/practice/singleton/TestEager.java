package com.hx92.practice.singleton;

public class TestEager {

  public static void main(String[] args) throws Exception {
    System.out.println("----step: begin");
    System.out.println("----step: class :" + Eager.class);
    System.out.println("----step: class toGenericString:" + Eager.class.toGenericString());

    System.out.println("----step: before load class ");

    TestEager.class.getClassLoader().loadClass("com.hx92.practice.singleton.Eager");
    System.out.println("----step: before Class.forName ");
    Class.forName("com.hx92.practice.singleton.Eager");

    System.out.println("----step: before instance ");
    System.out.println("----step: instance :" + Eager.instance.toString());
  }
}
