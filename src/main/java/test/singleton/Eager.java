package test.singleton;

public class Eager<T> {

  public static final Eager instance = new Eager();

  static {
    i = 10;
    System.out.println(Eager.class.getSimpleName() + " static block ~~");
  }


  static int i = 1;


  Eager() {
    System.out.println(i);
    System.out.println("eager constructed");
  }
}
