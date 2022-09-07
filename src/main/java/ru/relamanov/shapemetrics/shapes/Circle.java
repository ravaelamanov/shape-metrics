package ru.relamanov.shapemetrics.shapes;

import ru.relamanov.shapemetrics.metrics.ShapeMetric;
import ru.relamanov.shapemetrics.utils.Strings;

import java.util.Collection;
import java.util.List;

public final class Circle extends Shape {
  private final double radius;

  private static final String RADIUS_KEY_TEXT = "Радиус";
  private static final String DIAMETER_KEY_TEXT = "Диаметер";
  private static final int RADIUS_DISPLAY_ORDER =  3;
  private static final int DIAMETER_DISPLAY_ORDER =  4;

  public Circle(double radius) {
    super(Shapes.CIRCLE);
    this.radius = radius;
    validate();
    afterPropertiesSet();
  }

  public static Circle fromParamsLine(String shapeParamsLine) {
    String[] splitParams = shapeParamsLine.split(" ");
    if (splitParams.length < 1) {
      throw new IllegalArgumentException("Для создания круга требуется хотя бы 1 параметр.");
    }
    double radius = Strings.parseDouble(splitParams[0]);
    return new Circle(radius);
  }

  @Override
  protected double calculatePerimeter() {
    return 2 * Math.PI * radius;
  }

  @Override
  protected double calculateArea() {
    return Math.PI * Math.pow(radius, 2);
  }

  @Override
  protected Collection<ShapeMetric> additionalMetrics(String units) {
    ShapeMetric radius = ShapeMetric.from(RADIUS_KEY_TEXT, Strings.format(this.radius), units, RADIUS_DISPLAY_ORDER);
    ShapeMetric diameter = ShapeMetric.from(DIAMETER_KEY_TEXT, Strings.format(getDiameter()), units, DIAMETER_DISPLAY_ORDER);
    return List.of(radius, diameter);
  }

  private double getDiameter() {
    return this.radius * 2;
  }

  private void validate() {
    if (this.radius < 0) {
      throw new IllegalStateException("Радиус не должен быть отриацтельным: %f".formatted(this.radius));
    }
  }
}
