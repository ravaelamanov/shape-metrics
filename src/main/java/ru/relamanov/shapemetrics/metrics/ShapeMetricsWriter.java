package ru.relamanov.shapemetrics.metrics;

import ru.relamanov.shapemetrics.exceptions.IOExceptionWrapper;

import java.io.BufferedWriter;
import java.io.IOException;

public final class ShapeMetricsWriter {
    private final BufferedWriter writer;

    public ShapeMetricsWriter(BufferedWriter writer) {
        this.writer = writer;
    }

    public void write(ShapeMetrics shapeMetrics) {
        try {
            writer.write(shapeMetrics.toDisplayValue());
        }
        catch (IOException e) {
            throw new IOExceptionWrapper("Ошибка записи характеристик.", e);
        }
    }
}
