package test.singleton;

public class Testt {
  static int i = 1;
  static {
    i = 10;
    System.out.println(i);
  }
}
