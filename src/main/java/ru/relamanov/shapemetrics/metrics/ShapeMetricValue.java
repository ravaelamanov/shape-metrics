package ru.relamanov.shapemetrics.metrics;

public final class ShapeMetricValue<T> implements Displayable<String> {
    private final T value;
    private final String unit;

    private static final String DISPLAY_FORMAT = "%s %s";

    public ShapeMetricValue(T value, String unit) {
        this.value = value;
        this.unit = unit;
        validate();
    }

    @Override
    public String toDisplayValue() {
        return DISPLAY_FORMAT.formatted(value, unit).trim();
    }

    private void validate() {
        if (this.value == null) {
            throw new IllegalStateException("Значение характеристики равно null");
        }
    }
}
