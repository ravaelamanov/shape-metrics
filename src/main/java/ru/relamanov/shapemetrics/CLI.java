package ru.relamanov.shapemetrics;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import ru.relamanov.shapemetrics.utils.IO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Optional;

/**
 * Осуществляет парсинг и доступ к аргументам командной строки.
 */
public final class CLI {
  private final CommandLine cmd;

  private static final String UTILITY_NAME = "shape-metrics";
  private static final String INPUT_OPTION = "i";
  private static final String INPUT_OPTION_LONG = "input";
  private static final String INPUT_OPTION_DESCRIPTION = "Input file path. Optional. Defaults to standard input if not specified.";
  private static final String OUTPUT_OPTION = "o";
  private static final String OUTPUT_OPTION_LONG = "output";
  private static final String OUTPUT_OPTION_DESCRIPTION = "Output file path. Optional. Defaults to standard output if not specified.";
  private static final String UNITS_OPTION = "u";
  private static final String UNITS_OPTION_LONG = "units";
  private static final String UNITS_OPTION_DESCRIPTION = "Units. Optional. Defaults to empty string if not specified.";


  /**
   * @param args аргументы командной строки
   * @throws IllegalArgumentException если произошла ошибка в ходе парсинга {@code args}
   */
  public CLI(String[] args) {
    Options options = new Options();

    Option inputOption = new Option(INPUT_OPTION, INPUT_OPTION_LONG, true, INPUT_OPTION_DESCRIPTION);
    inputOption.setRequired(false);
    options.addOption(inputOption);

    Option outputOption = new Option(OUTPUT_OPTION, OUTPUT_OPTION_LONG, true, OUTPUT_OPTION_DESCRIPTION);
    inputOption.setRequired(false);
    options.addOption(outputOption);

    Option unitsOption = new Option(UNITS_OPTION, UNITS_OPTION_LONG, true, UNITS_OPTION_DESCRIPTION);
    inputOption.setRequired(false);
    options.addOption(unitsOption);

    try {
      CommandLineParser parser = new DefaultParser();
      cmd = parser.parse(options, args);
    } catch (Throwable e) {
      HelpFormatter helper = new HelpFormatter();
      helper.printHelp(UTILITY_NAME, options);
      throw new IllegalArgumentException("Неверные аргументы командной строки.", e);
    }
  }

  /**
   * @return {@link BufferedWriter} для файлу, с путем переданным в опции {@code '-o'},
   * или для стандартного потока вывода, если опция не указана
   */
  public BufferedWriter getBufferedWriter() {
      return IO.getBufferedWriterOrDefault(
              getOutputOptionValue(),
              () -> new BufferedWriter(new OutputStreamWriter(System.out)));
  }

  /**
   * @return {@link BufferedReader} для файла, с путем переданным в опции {@code '-i'},
   * или для стандартного потока ввода, если опция не указана
   */
  public BufferedReader getBufferedReader() {
      return IO.getBufferedReaderOrDefault(
              getInputOptionValue(),
              () -> new BufferedReader(new InputStreamReader(System.in)));
  }

  public Optional<String> getOptionValue(String option) {
    return Optional.ofNullable(cmd.getOptionValue(option));
  }

  public String getNullableOptionValue(String option) {
    return getOptionValue(option).orElse(null);
  }

  public String getInputOptionValue() {
    return getNullableOptionValue(INPUT_OPTION);
  }

  public String getOutputOptionValue() {
    return getNullableOptionValue(OUTPUT_OPTION);
  }

  public String getUnits() {
    return getOptionValue(UNITS_OPTION)
            .map(String::trim)
            .orElse("");
  }
}
