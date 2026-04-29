
package domein;

import java.util.regex.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.util.Pair;

public class Day03 implements DayString {

    private long mul(long a, long b) {
      return a * b;
    }

  long eval(String mul) {
    Matcher m = Pattern.compile("(\\d+),(\\d+)").matcher(mul);
    m.find();
    return Long.parseLong(m.group(1)) * Long.parseLong(m.group(2));
  }

  @Override
  public String solvePart1(String input) {
    long total = Pattern.compile("mul\\((\\d+),(\\d+)\\)")
        .matcher(input)
        .results()
        .mapToLong(mr -> mul(Long.parseLong(mr.group(1)), Long.parseLong(mr.group(2))))
        .sum();
          
    return String.valueOf(total);
  }

  @Override
  public String solvePart2(String input) {
      Pattern p = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)|don't\\(\\)|do\\(\\)");

      Matcher m = p.matcher(input);
      boolean enabled = true;
      long total = 0;

      while (m.find()) {
        switch (m.group()) {
          case "don't()" -> enabled = false;
          case "do()"    -> enabled = true;
          default        -> { if (enabled) total += Long.parseLong(m.group(1)) * Long.parseLong(m.group(2)); }
        }
      }

    return String.valueOf(total);
  }
}
