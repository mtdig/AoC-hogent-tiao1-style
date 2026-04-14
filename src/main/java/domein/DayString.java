package domein;

import java.util.List;

public interface DayString extends Day {
    String solvePart1(String input);
    String solvePart2(String input);

    @Override
    default String solvePart1(List<String> input) {
        return solvePart1(String.join("\n", input));
    }

    @Override
    default String solvePart2(List<String> input) {
        return solvePart2(String.join("\n", input));
    }
}
