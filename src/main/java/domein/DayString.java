/*
Ik had een DayString interface nodig omdat sommige dagen hun invoer liever als één grote string willen ontvangen in plaats van een lijst van regels.
In plaats van dat elke dag zelf de lijst van regels moet samenvoegen tot een string, kunnen ze gewoon deze interface implementeren en de
 default methoden zorgen voor de conversie van List<String> naar String.

*/
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
