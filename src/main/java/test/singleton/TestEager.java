package test.singleton;

public class TestEager {

  public static void main(String[] args) {
    System.out.println("----step: begin");
    System.out.println("----step: class :" + Eager.class);
    System.out.println("----step: class toGenericString:" + Eager.class.toGenericString());
    System.out.println("----step: before instance ");
    System.out.println("----step: instance :" + Eager.instance);
  }
}
