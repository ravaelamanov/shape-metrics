package ru.relamanov.shapemetrics.exceptions;

public class IOExceptionWrapper extends RuntimeException {
  public IOExceptionWrapper(String message, java.io.IOException cause) {
    super(message, cause);
  }
}
