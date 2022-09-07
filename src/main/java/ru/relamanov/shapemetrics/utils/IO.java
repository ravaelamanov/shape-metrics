package ru.relamanov.shapemetrics.utils;


import ru.relamanov.shapemetrics.exceptions.IOExceptionWrapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Supplier;

public final class IO {
  private IO() {
    throw new AssertionError();
  }

  public static String readLineAndTrim(BufferedReader reader) {
    String line;
    try {
      line = reader.readLine().trim();
    } catch (IOException e) {
      throw new IOExceptionWrapper("Не удалось прочитать строку.", e);
    }
    return line;
  }

  /**
   * @param filePath путь до файла. Может быть равен null
   * @param defaultReader {@link BufferedReader} по умолчанию, если {@code filePath} равен null
   * @return {@link BufferedReader} к файлу {@code filePath} или {@code defaultReader}, если {@code filePath} равен null
   * @throws IOExceptionWrapper, если не удалось открыть файл на чтение
   */
  public static BufferedReader getBufferedReaderOrDefault(String filePath, Supplier<BufferedReader> defaultReader) {
    return Optional.ofNullable(filePath)
            .map(File::new)
            .map(file -> {
              try {
                return new FileReader(file);
              } catch (FileNotFoundException e) {
                throw new IOExceptionWrapper("Файл не найден. Путь: %s".formatted(filePath), e);
              }
            })
            .map(BufferedReader::new)
            .orElseGet(defaultReader);
  }

  /**
   * @param filePath путь до файла. Может быть равен null
   * @param defaultWriter {@link BufferedWriter} по умолчанию, если {@code filePath} равен null
   * @return {@link BufferedWriter} к файлу {@code filePath} или {@code defaultWriter}, если {@code filePath} равен null
   * @throws IOExceptionWrapper, если не удалось открыть файл на запись
   */
  public static BufferedWriter getBufferedWriterOrDefault(String filePath, Supplier<BufferedWriter> defaultWriter) {
    return Optional.ofNullable(filePath)
            .map(File::new)
            .map(file -> {
              try {
                return new FileWriter(file);
              } catch (IOException e) {
                throw new IOExceptionWrapper("Не удалось открыть файл на запись. Путь: %s".formatted(filePath), e);
              }
            })
            .map(BufferedWriter::new)
            .orElseGet(defaultWriter);
  }
}
