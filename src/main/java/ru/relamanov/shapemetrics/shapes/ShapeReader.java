package ru.relamanov.shapemetrics.shapes;

import ru.relamanov.shapemetrics.utils.IO;

import java.io.BufferedReader;
import java.util.Arrays;

public final class ShapeReader {
  private final BufferedReader reader;

  public ShapeReader(BufferedReader reader) {
    this.reader = reader;
  }

  public Shape read() {
    Shapes type = readShapeType();
    String paramsLine = readParamsLine();
    return type.parse(paramsLine);
  }

  private Shapes readShapeType() {
    String stringType = IO.readLineAndTrim(reader);
    Shapes type;
    try {
      type = Shapes.valueOf(stringType.toUpperCase());
      return type;
    }
    catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException("Данная фигура не поддерживается: %s. Выберите одну из следующих: %s"
              .formatted(stringType, Arrays.toString(Shapes.values())));
    }
  }

  private String readParamsLine() {
    return IO.readLineAndTrim(reader);
  }
}
