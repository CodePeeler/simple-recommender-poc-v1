package com.simon.util;

@FunctionalInterface
public interface Compare<T> {
  public int compare(T t, T r);

}