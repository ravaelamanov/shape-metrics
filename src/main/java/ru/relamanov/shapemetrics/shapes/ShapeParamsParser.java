package ru.relamanov.shapemetrics.shapes;

@FunctionalInterface
public interface ShapeParamsParser {
    Shape parse(String paramsLine);
}
