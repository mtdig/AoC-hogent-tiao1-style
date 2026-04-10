package domein;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day01 implements Day {

    private record Pair(long left, long right) {
        static Pair parse(String line) {
            var parts = line.trim().split("\\s+");
            return new Pair(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
        }
    }

    @Override
    public String solvePart1(List<String> input) {
        var pairs = input.stream().map(Pair::parse).toList();

        var lefts = pairs.stream().map(Pair::left).sorted().toList();
        var rights = pairs.stream().map(Pair::right).sorted().toList();

        long result = IntStream.range(0, lefts.size())
                .mapToLong(i -> Math.abs(lefts.get(i) - rights.get(i)))
                .sum();

        return String.valueOf(result);
    }

    @Override
    public String solvePart2(List<String> input) {
        var pairs = input.stream().map(Pair::parse).toList();

        var rightCounts = pairs.stream()
                .collect(Collectors.groupingBy(Pair::right, Collectors.counting()));

        long result = pairs.stream()
                .mapToLong(p -> p.left() * rightCounts.getOrDefault(p.left(), 0L))
                .sum();

        return String.valueOf(result);
    }
}
