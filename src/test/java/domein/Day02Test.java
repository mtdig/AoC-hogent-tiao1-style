package domein;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day02Test {

    private final Day02 day = new Day02();

    private final List<String> EXAMPLE = List.of(
            "7 6 4 2 1",
            "1 2 7 8 9",
            "9 7 6 2 1",
            "1 3 2 4 5",
            "8 6 4 4 1",
            "1 3 6 7 9"
    );

    @Test
    void part1_example() {
        assertEquals("2", day.solvePart1(EXAMPLE));
    }

    @Test
    void part2_example() {
        assertEquals("4", day.solvePart2(EXAMPLE));
    }


}
