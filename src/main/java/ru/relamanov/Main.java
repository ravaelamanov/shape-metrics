package ru.relamanov;

import ru.relamanov.shapemetrics.CLI;
import ru.relamanov.shapemetrics.ShapeController;
import ru.relamanov.shapemetrics.metrics.ShapeMetricsWriter;
import ru.relamanov.shapemetrics.shapes.Shape;
import ru.relamanov.shapemetrics.shapes.ShapeReader;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public class Main {
    public static void main(String[] args) {
        CLI cli = new CLI(args);

        try(BufferedReader reader = cli.getBufferedReader();
            BufferedWriter writer = cli.getBufferedWriter()) {
            ShapeReader shapeReader = new ShapeReader(reader);
            ShapeMetricsWriter shapeMetricsWriter = new ShapeMetricsWriter(writer);
            ShapeController controller = new ShapeController(shapeReader, shapeMetricsWriter);
            Shape shape = controller.readShape();
            controller.writeShapeMetrics(shape.metrics(cli.getUnits()));
        } catch (Exception e) {
            throw new RuntimeException("Возникла ошибка в ходе расчета характеристик фигуры.", e);
        }
    }

}
