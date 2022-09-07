package ru.relamanov.shapemetrics.utils;

import java.text.DecimalFormat;

public final class Strings {
  private static final DecimalFormat doubleFormat = new DecimalFormat("0.##");

  private Strings() {
    throw new AssertionError();
  }

  public static String format(double value) {
    return doubleFormat.format(value);
  }

  public static double parseDouble(String strDouble) {
    try {
      return Double.parseDouble(strDouble);
    } catch (NumberFormatException ex) {
      throw new IllegalArgumentException("Неверный формат числа. Число: %s".formatted(strDouble), ex);
    }
  }
}
