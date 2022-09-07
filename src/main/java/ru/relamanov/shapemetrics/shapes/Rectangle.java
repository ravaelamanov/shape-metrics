package ru.relamanov.shapemetrics.shapes;

import ru.relamanov.shapemetrics.metrics.ShapeMetric;
import ru.relamanov.shapemetrics.utils.Strings;

import java.util.Collection;
import java.util.List;

public final class Rectangle extends Shape {
  private final double length;
  private final double width;

  private static final String DIAGONAL_KEY_TEXT = "Длина диагонали";
  private static final String LENGTH_KEY_TEXT = "Длина";
  private static final String WIDTH_KEY_TEXT = "Ширина";
  private static final int DIAGONAL_DISPLAY_ORDER = 3;
  private static final int LENGTH_DISPLAY_ORDER = 4;
  private static final int WIDTH_DISPLAY_ORDER = 5;

  public Rectangle(double a, double b) {
    super(Shapes.RECTANGLE);
    this.length = Math.max(a, b);
    this.width = Math.min(a, b);
    validate();
    afterPropertiesSet();
  }

  public static Rectangle fromParamsLine(String shapeParamsLine) {
    String[] splitParams = shapeParamsLine.split(" ");
    if (splitParams.length < 2) {
      throw new IllegalArgumentException("Для создания прямоугольника требуется хотя бы 2 параметра.");
    }
    double a = Strings.parseDouble(splitParams[0]);
    double b = Strings.parseDouble(splitParams[1]);
    return new Rectangle(a, b);
  }

  @Override
  protected double calculatePerimeter() {
    return (this.length + this.width) * 2;
  }

  @Override
  protected double calculateArea() {
    return this.length * this.width;
  }

  @Override
  protected Collection<ShapeMetric> additionalMetrics(String units) {
    ShapeMetric diagonal = ShapeMetric
            .from(DIAGONAL_KEY_TEXT, Strings.format(getDiagonal()), units, DIAGONAL_DISPLAY_ORDER);
    ShapeMetric length = ShapeMetric
            .from(LENGTH_KEY_TEXT, Strings.format(this.length), units, LENGTH_DISPLAY_ORDER);
    ShapeMetric width = ShapeMetric
            .from(WIDTH_KEY_TEXT, Strings.format(this.width), units, WIDTH_DISPLAY_ORDER);

    return List.of(diagonal, length, width);
  }

  private double getDiagonal() {
    return Math.sqrt(Math.pow(length, 2) + Math.pow(width, 2));
  }

  private void validate() {
    if (this.length < 0 || this.width < 0) {
      throw new IllegalStateException("Одна из сторон меньше нуля: {%f, %f}".formatted(length, width));
    }
  }
}
