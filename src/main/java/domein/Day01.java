/*
 * Advent of Code 2024 - Day 1: Historian Hysteria
 *
 * De invoer bestaat uit twee kolommen met getallen (links en rechts).
 *
 *   3   4
 *   4   3
 *   2   5
 *   1   3
 *   3   9
 *   3   3
 *
 * Deel 1 - Totale afstand:
 *   Sorteer beide kolommen van klein naar groot. Tel daarna voor elk paar
 *   op dezelfde positie het absolute verschil op. Dat is de totale afstand.
 *   links gesorteerd: [1,2,3,3,3,4], rechts gesorteerd: [3,3,3,4,5,9]
 *   → |1-3| + |2-3| + |3-3| + |3-4| + |3-5| + |4-9| = 2+1+0+1+2+5 = 11
 *
 * Deel 2 - Similarity score:
 *   Kijk voor elk getal in de linkerkolom hoe vaak het voorkomt in de
 *   rechterkolom. Vermenigvuldig het getal met dat aantal en tel alles op.
 *   → 3×3 + 4×1 + 2×0 + 1×0 + 3×3 + 3×3 = 9+4+0+0+9+9 = 31
 */
package domein;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day01 implements Day {

    // Pair stelt één regel voor uit de invoer: twee getallen naast elkaar.
    private record Pair(long left, long right) {

        // Zet één tekstlijn om naar een Pair.
        // split("\\s+") splitst op één of meerdere spaties/tabs.
        static Pair parse(String line) {
            var parts = line.trim().split("\\s+");
            return new Pair(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
        }
    }

    // Deel 1: bereken de totale afstand tussen de twee gesorteerde lijsten.
    // Beide kolommen worden apart gesorteerd, daarna vergelijken we telkens
    // het kleinste getal van links met het kleinste van rechts, enzovoort.
    @Override
    public String solvePart1(List<String> input) {
        var pairs = input.stream().map(Pair::parse).toList();

        // Extract de linker- en rechterkolom en sorteer ze elk afzonderlijk.
        var lefts = pairs.stream().map(Pair::left).sorted().toList();
        var rights = pairs.stream().map(Pair::right).sorted().toList();

        // Som de absolute verschillen op voor elk paar op dezelfde positie.
        long result = IntStream.range(0, lefts.size())
                .mapToLong(i -> Math.abs(lefts.get(i) - rights.get(i)))
                .sum();

        return String.valueOf(result);
    }

    // Deel 2: bereken de "similarity score".
    // Voor elk getal in de linkerkolom tellen we hoe vaak het voorkomt in de rechterkolom,
    // en vermenigvuldigen dat met het getal zelf.
    @Override
    public String solvePart2(List<String> input) {
        var pairs = input.stream().map(Pair::parse).toList();

        // Tel hoe vaak elk getal voorkomt in de rechterkolom.
        // groupingBy + counting geeft een Map<Long, Long>: getal -> aantal keer.
        var rightCounts = pairs.stream()
                .collect(Collectors.groupingBy(Pair::right, Collectors.counting()));

        // Elk getal links vermenigvuldigen met zijn aantal voorkomens rechts.
        // getOrDefault geeft 0 terug als het getal helemaal niet rechts voorkomt.
        long result = pairs.stream()
                .mapToLong(p -> p.left() * rightCounts.getOrDefault(p.left(), 0L))
                .sum();

        return String.valueOf(result);
    }
}
