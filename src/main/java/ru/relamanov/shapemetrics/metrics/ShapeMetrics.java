package ru.relamanov.shapemetrics.metrics;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

public final class ShapeMetrics implements Displayable<String> {
  private final Collection<ShapeMetric> shapeMetrics;

  public ShapeMetrics(Collection<ShapeMetric> shapeMetrics) {
    this.shapeMetrics = shapeMetrics.stream()
            .sorted(Comparator.comparing(ShapeMetric::getDisplayOrder))
            .toList();
  }

  @Override
  public String toDisplayValue() {
    return shapeMetrics.stream()
            .map(Displayable::toDisplayValue)
            .collect(Collectors.joining("\n"));
  }
}
