/*
De DomainController is de centrale klasse in de domeinlaag van deze applicatie.

*/

package domein;

import dto.DayResultDto;

import repository.DayRegistry;
import repository.InputReader;

import java.util.List;
import java.util.SequencedMap;

public class DomainController {

    private final SequencedMap<Integer, Day> days;

    public DomainController() {
        this.days = DayRegistry.findAll();
    }

    public List<Integer> getAvailableDays() {
        return List.copyOf(days.keySet());
    }

    public String getDayLabel(int dayNumber) {
        return DayRegistry.label(dayNumber);
    }

    public DayResultDto runDay(int dayNumber) {
        var day = days.get(dayNumber);
        if (day == null) throw new IllegalArgumentException("Day " + dayNumber + " not found");

        var input = InputReader.readLines(DayRegistry.inputName(dayNumber));
        return new DayResultDto(day.solvePart1(input), day.solvePart2(input));
    }
}
