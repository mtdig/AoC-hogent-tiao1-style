package domein;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Optional;

public class Day02 implements Day {
    private enum Direction {
        UP,
        DOWN
    }

    static class RuleChecker {
        private static Optional<Integer> checkRule(List<Integer> numbers){
            var direction = numbers.get(0) > numbers.get(1) ? Direction.DOWN : Direction.UP;

            for (int i=0; i < numbers.size() - 1;i++){
                var n1 = numbers.get(i+1);
                var n0 = numbers.get(i);

                if (direction == Direction.UP) {
                    if (n1 - n0 <= 0 || n1 - n0 > 3) {
                        return Optional.of(i);
                    }
                } else {
                    if (n1 - n0 >= 0 || n0 - n1 > 3) {
                        return Optional.of(i);
                    }
                }
            }
            return Optional.empty();
        }
    }


    static class LineParser {
        private static List<Integer> parseLine(String line) {
            var numbers = Arrays.stream(line.split("\\s+"))
                    .map(Integer::parseInt).toList();


            System.out.printf("numbers: %s%n", numbers);
            return numbers;
        }
    }

    private Optional<Integer> problem_dampener(List<Integer> items, int faultIndex){
        // try removing the fault index, the one after it, and the one before it
        for (int offset = -1; offset <= 1; offset++) {
            int removeAt = faultIndex + offset;
            if (removeAt < 0 || removeAt >= items.size()) continue;

            var newItems = new ArrayList<>(items);
            newItems.remove(removeAt);

            if (RuleChecker.checkRule(newItems).isEmpty()) {
                return Optional.empty();
            }
        }
        // none of the removals fixed it
        return Optional.of(faultIndex);
    }


    @Override
    public String solvePart1(List<String> input) {
        var numberlines = input.stream().map(LineParser::parseLine).toList();
        System.out.printf("line: %s%n", numberlines);

        var safes = numberlines.stream()
                        .map(RuleChecker::checkRule)
                .peek((s) -> System.out.println("s?: " +  s))
                        .filter(Optional::isEmpty)
                        .count();

        System.out.printf("safes: %s%n", safes);

        return String.valueOf(safes);
    }

    @Override
    public String solvePart2(List<String> input) {
        int safes = 0, unsafes = 0;

        for ( var line : input){
            var lineNumbers = LineParser.parseLine(line);
            System.out.printf("lineNumbers: %s%n", lineNumbers);
            var checkSafety = RuleChecker.checkRule(lineNumbers);
            System.out.printf("checkSafety: %s%n", checkSafety);

            if (checkSafety.isEmpty()){
                safes++;
            } else {
                if (problem_dampener(lineNumbers, checkSafety.get()).isEmpty()){
                    safes++;
                } else {
                    unsafes++;
                }
            }
        }
        System.out.printf("unsafes: %d%n", unsafes);
        return String.valueOf(safes);
    }
}
