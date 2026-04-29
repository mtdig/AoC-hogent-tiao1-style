/*
Een dto, omdat het resultaat van een dag bestaat uit twee strings, en we willen dat netjes in één object verpakken voor de gui.
*/

package dto;

public record DayResultDto(String part1, String part2) {
}
