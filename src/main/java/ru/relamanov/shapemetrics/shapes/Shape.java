package ru.relamanov.shapemetrics.shapes;

import ru.relamanov.shapemetrics.metrics.ShapeMetric;
import ru.relamanov.shapemetrics.metrics.ShapeMetrics;
import ru.relamanov.shapemetrics.utils.Strings;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * Базовый класс фигуры. Инкапсулирует информацию общую для всех фигур.
 * Наследники могут расширить отображемые характеристики фигуры переопределив
 * метод {@link #additionalMetrics(String)}.
 */
public abstract class Shape {
    private final Shapes type;
    private double perimeter;
    private double area;

    private static final String SHAPE_TYPE_KEY_TEXT = "Тип фигуры";
    private static final String AREA_KEY_TEXT = "Площадь";
    private static final String PERIMETER_KEY_TEXT = "Периметр";
    private static final int SHAPE_TYPE_ORDER = 0;
    private static final int AREA_TYPE_ORDER = 1;
    private static final int PERIMETER_TYPE_ORDER = 2;

    /**
     * Базовый конструктор фигуры. Наследники должны вызывать метод
     * {@link #afterPropertiesSet()} после установки всех свойств.
     * @param type тип фигуры
     * @throws IllegalStateException если {@code type == null}
     * @see Shapes
     */
    public Shape(Shapes type) {
        this.type = type;
        validate();
    }

    protected abstract double calculatePerimeter();

    protected abstract double calculateArea();

    /**
     * @return {@link ShapeMetrics}, включающий общие для всех фигур метрики, а также метрики специфичные для наследников.
     */
    public ShapeMetrics metrics(String units) {
        List<ShapeMetric> metrics = Stream.concat(
                baseMetrics(units).stream(),
                additionalMetrics(units).stream()).toList();
        return new ShapeMetrics(metrics);
    }

    protected void afterPropertiesSet() {
        this.perimeter = calculatePerimeter();
        this.area = calculateArea();
    }

    private void validate() {
        if (this.type == null) {
            throw new IllegalStateException("Тип фигуры равен null!");
        }
    }

    private Collection<ShapeMetric> baseMetrics(String units) {
        ShapeMetric shapeType = ShapeMetric
                .from(SHAPE_TYPE_KEY_TEXT, type.toDisplayValue(), SHAPE_TYPE_ORDER);
        ShapeMetric area = ShapeMetric
                .from(AREA_KEY_TEXT, Strings.format(this.area), areaUnits(units), AREA_TYPE_ORDER);
        ShapeMetric perimeter = ShapeMetric
                .from(PERIMETER_KEY_TEXT, Strings.format(this.perimeter), units, PERIMETER_TYPE_ORDER);

        return List.of(shapeType, area, perimeter);
    }

    private static String areaUnits(String units) {
        return units.isBlank() ? units : "кв. %s".formatted(units);
    }

    protected Collection<ShapeMetric> additionalMetrics(String units) {
        return Collections.emptyList();
    }
}
