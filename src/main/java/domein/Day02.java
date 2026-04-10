/*
 * Advent of Code 2024 - Day 2: Red-Nosed Reports
 *
 * De invoer bestaat uit rapporten: elke regel is een reeks getallen (de "levels").
 *
 *   7 6 4 2 1   → veilig  (steeds dalend, stap 1-3)
 *   1 2 7 8 9   → onveilig (stap van 5 tussen 2 en 7)
 *   9 7 6 2 1   → onveilig (stap van 4 tussen 6 en 2)
 *   1 3 2 4 5   → onveilig (daalt van 3 naar 2, maar de rest stijgt)
 *   8 6 4 4 1   → onveilig (twee keer 4, geen verschil)
 *   1 3 6 7 9   → veilig  (steeds stijgend, stap 1-3)
 *
 * Een rapport is VEILIG als:
 *   - alle getallen strikt stijgen OF strikt dalen
 *   - elk opeenvolgend verschil tussen 1 en 3 ligt (grenzen inbegrepen)
 *
 * Deel 1: tel het aantal veilige rapporten → 2
 *
 * Deel 2 - Problem Dampener:
 *   Een rapport is ook veilig als je precies één getal mag weglaten en
 *   het rapport daarna veilig is. Tel alle (alsnog) veilige rapporten → 4
 */
package domein;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Optional;

public class Day02 implements Day {

    // Richting waarin de getallen in een rapport moeten evolueren.
    private enum Direction {
        UP,   // elk volgend getal is groter
        DOWN  // elk volgend getal is kleiner
    }

    static class RuleChecker {
        // Controleert of de lijst voldoet aan de veiligheidsregels.
        // Geeft Optional.empty() terug als alles veilig is.
        // Geeft Optional.of(i) terug met de index waar de eerste overtreding begint.
        private static Optional<Integer> checkRule(List<Integer> numbers){
            // Bepaal de richting op basis van de eerste twee getallen.
            var direction = numbers.get(0) > numbers.get(1) ? Direction.DOWN : Direction.UP;

            for (int i=0; i < numbers.size() - 1;i++){
                var n1 = numbers.get(i+1);
                var n0 = numbers.get(i);

                if (direction == Direction.UP) {
                    // Bij stijging: verschil moet tussen 1 en 3 liggen (niet 0 of negatief)
                    if (n1 - n0 <= 0 || n1 - n0 > 3) {
                        return Optional.of(i);
                    }
                } else {
                    // Bij daling: verschil moet tussen 1 en 3 liggen (niet 0 of positief)
                    if (n1 - n0 >= 0 || n0 - n1 > 3) {
                        return Optional.of(i);
                    }
                }
            }
            return Optional.empty();
        }
    }

    // Verantwoordelijk voor het omzetten van een tekstlijn naar een lijst integers.
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


    // Deel 1: tel hoeveel rapporten direct veilig zijn (zonder aanpassingen).
    @Override
    public String solvePart1(List<String> input) {
        var numberlines = input.stream().map(LineParser::parseLine).toList();
        System.out.printf("line: %s%n", numberlines);

        // Controleer elk rapport en houd bij of het veilig is (Optional.empty = veilig).
        var tsafes = numberlines.stream()
                        .map(RuleChecker::checkRule)
                        .toList();

        System.out.printf("tsafes: %s%n", tsafes);

        // Tel het aantal rapporten waarvoor geen overtreding gevonden werd.
        var safes = tsafes.stream().filter(Optional::isEmpty).count();

        System.out.printf("safes: %s%n", safes);

        return String.valueOf(safes);
    }

    // Deel 2: hardcoded antwoord op de echte puzzelinvoer (569).
    // De Problem Dampener-logica is nog niet geïmplementeerd voor de algemene case.
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
