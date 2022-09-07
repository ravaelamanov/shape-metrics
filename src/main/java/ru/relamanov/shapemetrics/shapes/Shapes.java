package ru.relamanov.shapemetrics.shapes;

import ru.relamanov.shapemetrics.metrics.Displayable;

public enum Shapes implements Displayable<String>, ShapeParamsParser {
    CIRCLE("Круг", Circle::fromParamsLine),
    RECTANGLE("Прямоугольник", Rectangle::fromParamsLine),
    TRIANGLE("Треугольник", Triangle::fromParamsLine),
    ;

    private final String displayName;
    private final ShapeParamsParser parser;

    Shapes(String displayName, ShapeParamsParser parser) {
        this.displayName = displayName;
        this.parser = parser;
    }

    @Override
    public String toDisplayValue() {
        return displayName;
    }

    @Override
    public Shape parse(String paramsLine) {
        return parser.parse(paramsLine);
    }
}
