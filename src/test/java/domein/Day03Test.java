package domein;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day03Test {

  private final Day03 day = new Day03();

    @Test
  void part1_example() {
      String EXAMPLE1 = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))";
      assertEquals("161", day.solvePart1(EXAMPLE1));
  }

  @Test
  void part2_example() {
      String EXAMPLE2 = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))";
      assertEquals("48", day.solvePart2(EXAMPLE2));
  }

}
