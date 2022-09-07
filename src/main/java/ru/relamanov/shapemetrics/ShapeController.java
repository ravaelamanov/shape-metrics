package ru.relamanov.shapemetrics;

import ru.relamanov.shapemetrics.metrics.ShapeMetrics;
import ru.relamanov.shapemetrics.metrics.ShapeMetricsWriter;
import ru.relamanov.shapemetrics.shapes.Shape;
import ru.relamanov.shapemetrics.shapes.ShapeReader;

public final class ShapeController {
  private final ShapeReader reader;
  private final ShapeMetricsWriter writer;

  public ShapeController(ShapeReader reader, ShapeMetricsWriter writer) {
    this.reader = reader;
    this.writer = writer;
  }

  public Shape readShape() {
    return reader.read();
  }

  public void writeShapeMetrics(ShapeMetrics metrics) {
    writer.write(metrics);
  }
}
