/*
Interface voor de dagen van de Advent of Code. Elke dag implementeert deze interface.

Ze voorziet twee methoden, solvePart1 en solvePart2, die een lijst van strings als invoer
verwachten en een string teruggeven als resultaat.

De GUI roept deze methoden aan om de oplossingen van elke dag te tonen.
 */
package domein;

import java.util.List;

public interface Day {
    String solvePart1(List<String> input);
    String solvePart2(List<String> input);
}
