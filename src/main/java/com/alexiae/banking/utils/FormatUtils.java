package com.alexiae.banking.utils;

public class FormatUtils {

  private FormatUtils() {
  }

  public static String generateOperationCode(int length, Long value) {
    String format = String.join("", "%0", String.valueOf(length), "d");

    return String.format(format, value.intValue());
  }
}
