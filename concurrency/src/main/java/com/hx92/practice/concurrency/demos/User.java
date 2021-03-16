package com.hx92.practice.concurrency.demos;

import java.util.Objects;

public class User implements Cloneable {

  private final String name;
  private final int age;

  public User(String name, int age) {
    this.name = name;
    this.age = age;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    User user = (User) o;

    if (age != user.age) {
      return false;
    }
    return Objects.equals(name, user.name);
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + age;
    return result;
  }

  public String toString() {
    return "{name:'" + name + "',age:" + age + "}";
  }

  public Object clone() {
    Object object = null;
    try {
      object = super.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    return object;
  }

  public String getName() {
    return name;
  }
} 
