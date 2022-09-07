package ru.relamanov.shapemetrics.shapes;

import ru.relamanov.shapemetrics.metrics.ShapeMetric;
import ru.relamanov.shapemetrics.utils.Strings;

import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

public final class Triangle extends Shape {
  private final double a;
  private final double b;
  private final double c;
  private static final String KEY_TEXT_FORMAT = "Сторона %d / Противолежащий угол";
  private static final String VALUE_TEXT_FORMAT = "%s / %s°";
  private static final int DISPLAY_ORDER_OFFSET = 3;


  public Triangle(double a, double b, double c) {
    super(Shapes.TRIANGLE);
    this.a = a;
    this.b = b;
    this.c = c;
    validate();
    afterPropertiesSet();
  }

  public static Triangle fromParamsLine(String shapeParamsLine) {
    String[] splitParams = shapeParamsLine.split(" ");
    if (splitParams.length < 3) {
      throw new IllegalArgumentException("Для создания треугольника требуется хотя бы 3 параметра.");
    }
    double a = Strings.parseDouble(splitParams[0]);
    double b = Strings.parseDouble(splitParams[1]);
    double c = Strings.parseDouble(splitParams[2]);
    return new Triangle(a, b, c);
  }

  @Override
  protected double calculatePerimeter() {
    return a + b + c;
  }

  @Override
  protected double calculateArea() {
    double halfPerimeter = halfPerimeter();
    return Math.sqrt(halfPerimeter * (halfPerimeter - a) * (halfPerimeter - b) * (halfPerimeter - c));
  }

  @Override
  protected Collection<ShapeMetric> additionalMetrics(String units) {
    double aOppositeAngle = oppositeAngle(a, b, c);
    double bOppositeAngle = oppositeAngle(b, a, c);
    double cOppositeAngle = oppositeAngle(c, b, a);

    List<Pair<Double, Double>> pairs = List.of(
            new Pair<>(a, aOppositeAngle),
            new Pair<>(b, bOppositeAngle),
            new Pair<>(c, cOppositeAngle));

    return IntStream.range(0, pairs.size())
            .mapToObj(i -> {
              Pair<Double, Double> pair = pairs.get(i);
              return ShapeMetric.from(
                      KEY_TEXT_FORMAT.formatted(i + 1),
                      VALUE_TEXT_FORMAT.formatted(
                              Strings.format(pair.first),
                              Strings.format(pair.second)),
                      units,
                      DISPLAY_ORDER_OFFSET + i
              );
            }).toList();
  }

  private void validate() {
    if (a < 0 || b < 0 || c < 0) {
      throw new IllegalStateException("Одна из сторон меньше нуля: {%f, %f, %f}".formatted(a, b, c));
    }

    if (!triangleExists(a, b, c)) {
      throw new IllegalStateException("Не существует треугольника со сторонами: {%f, %f, %f}".formatted(a, b, c));
    }
  }

  private double halfPerimeter() {
    return calculatePerimeter() / 2;
  }

  private static boolean triangleExists(double a, double b, double c) {
    return a + b > c &&
            b + c > a &&
            a + c > b;
  }

  private static double oppositeAngle(double oppositeSide, double side1, double side2) {
    return Math.toDegrees(Math.acos((Math.pow(side1, 2) + Math.pow(side2, 2) - Math.pow(oppositeSide, 2)) / (2 * side1 * side2)));
  }

  static class Pair<T1, T2> {
    private T1 first;
    private T2 second;

    private Pair(T1 first, T2 second) {
      this.first = first;
      this.second = second;
    }
  }
}
