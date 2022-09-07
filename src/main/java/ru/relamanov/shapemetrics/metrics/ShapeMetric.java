package ru.relamanov.shapemetrics.metrics;

public final class ShapeMetric implements Displayable<String> {
    private final String displayName;
    private final ShapeMetricValue<?> value;
    private final int displayOrder;

    private static final String DISPLAY_FORMAT = "%s: %s";

    public ShapeMetric(String displayName, ShapeMetricValue<?> value, int displayOrder) {
        this.displayName = displayName;
        this.value = value;
        this.displayOrder = displayOrder;
        validate();
    }

    public static <T> ShapeMetric from(String displayKeyText, T value, String units, int displayOrder) {
      return new ShapeMetric(
              displayKeyText,
              new ShapeMetricValue<>(value, units),
              displayOrder);
    }

    public static <T> ShapeMetric from(String displayKeyText, T value, int displayOrder) {
        return from(displayKeyText, value, "", displayOrder);
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    @Override
    public String toDisplayValue() {
        return DISPLAY_FORMAT.formatted(displayName, value.toDisplayValue());
    }

    private void validate() {
        if (displayName == null || displayName.isBlank()) {
            throw new IllegalStateException("Название характеристики равно null или пустое!");
        }
    }
}
