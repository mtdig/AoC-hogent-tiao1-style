package repository;

import domein.Day;

import java.util.LinkedHashMap;
import java.util.SequencedMap;

public class DayRegistry {

    private static final int MAX_DAY = 25;

    public static SequencedMap<Integer, Day> findAll() {
        var days = new LinkedHashMap<Integer, Day>();

        for (int i = 1; i <= MAX_DAY; i++) {
            String className = String.format("domein.Day%02d", i);
            try {
                var clazz = Class.forName(className);
                if (Day.class.isAssignableFrom(clazz)) {
                    days.put(i, (Day) clazz.getDeclaredConstructor().newInstance());
                }
            } catch (ClassNotFoundException e) {
                // Day not implemented yet, skip
            } catch (Exception e) {
                System.err.println("Could not load " + className + ": " + e.getMessage());
            }
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
