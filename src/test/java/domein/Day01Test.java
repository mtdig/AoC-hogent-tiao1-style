package domein;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day01Test {

    private final Day01 day = new Day01();

    private final List<String> EXAMPLE = List.of(
            "3   4",
            "4   3",
            "2   5",
            "1   3",
            "3   9",
            "3   3"
    );

    @Test
    void part1_example() {
        assertEquals("11", day.solvePart1(EXAMPLE));
    }

    @Test
    void part2_example() {
        assertEquals("31", day.solvePart2(EXAMPLE));
    }


}
