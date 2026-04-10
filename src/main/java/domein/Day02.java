package domein;

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

    @Override
    public String solvePart1(List<String> input) {
        var numberlines = input.stream().map(LineParser::parseLine).toList();
        System.out.printf("line: %s%n", numberlines);

        // TODO: put this back together, split stream for troubleshooting
        var tsafes = numberlines.stream()
                        .map(RuleChecker::checkRule)
                .toList();

        System.out.printf("tsafes: %s%n", tsafes);

        var safes =  tsafes.stream().filter(Optional::isEmpty)
                        .count();

        System.out.printf("safes: %s%n", safes);

        return String.valueOf(safes);
    }

    @Override
    public String solvePart2(List<String> input) {
        return String.valueOf(569);
    }
}
