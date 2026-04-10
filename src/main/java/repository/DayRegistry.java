package repository;

import domein.Day;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.SequencedMap;
import java.util.regex.Pattern;

public class DayRegistry {

    private static final Pattern DAY_PATTERN = Pattern.compile("Day(\\d{2})\\.class");

    public static SequencedMap<Integer, Day> findAll() {
        var days = new LinkedHashMap<Integer, Day>();

        try (var stream = new BufferedReader(new InputStreamReader(
                DayRegistry.class.getClassLoader().getResourceAsStream("domein")))) {
            stream.lines()
                    .map(DAY_PATTERN::matcher)
                    .filter(m -> m.matches())
                    .forEach(m -> {
                        int number = Integer.parseInt(m.group(1));
                        try {
                            var clazz = Class.forName("domein.Day" + m.group(1));
                            if (Day.class.isAssignableFrom(clazz)) {
                                days.put(number, (Day) clazz.getDeclaredConstructor().newInstance());
                            }
                        } catch (Exception e) {
                            System.err.println("Could not load Day" + m.group(1) + ": " + e.getMessage());
                        }
                    });
        } catch (Exception e) {
            throw new RuntimeException("Cannot scan domain package", e);
        }

        return days;
    }

    public static String label(int number) {
        return String.format("Day %02d", number);
    }

    public static String inputName(int number) {
        return String.format("day%02d", number);
    }
}
