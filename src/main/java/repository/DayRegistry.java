package repository;

import domein.Day;

import java.util.LinkedHashMap;
import java.util.SequencedMap;

public class DayRegistry {

    // Regex om bestanden te herkennen die overeenkomen met "Day01.class", "Day02.class", ...
    // De haakjes rond \\d{2} vormen een groep zodat we het dagnummer apart kunnen opvragen.
    private static final Pattern DAY_PATTERN = Pattern.compile("Day(\\d{2})\\.class");
    private static final int MAX_DAY = 25;

    // Zoek alle geïmplementeerde dagen op en geef ze terug als een geordende map:
    // dagnummer (1, 2, ...) → instantie van de bijhorende Day-klasse.
    // LinkedHashMap behoudt de invoegvolgorde, zodat de dagen in volgorde verschijnen in de GUI.
    // SequenceMap is een nieuwe interface, duidelijke bedoeling.
    public static SequencedMap<Integer, Day> findAll() {
        var days = new LinkedHashMap<Integer, Day>();

        // Lees de inhoud van de "domein"-map uit het classpath als een tekststroom van bestandsnamen.
        // try-with-resources zorgt ervoor dat de stream altijd netjes gesloten wordt.
        try (var stream = new BufferedReader(new InputStreamReader(
                DayRegistry.class.getClassLoader().getResourceAsStream("domein")))) {
            stream.lines()
                    .map(DAY_PATTERN::matcher)      // methode referentie: probeer elke bestandsnaam te matchen
                    .filter(m -> m.matches())        // lambda: houd alleen de matches over (Day01.class etc.)
                    .forEach(m -> {
                        int number = Integer.parseInt(m.group(1)); // haal het dagnummer uit de regex-groep
                        try {
                            // Laad de klasse dynamisch op basis van de naam, bv. "domein.Day01"
                            var clazz = Class.forName("domein.Day" + m.group(1));
                            // Controleer of de klasse ook echt de Day-interface implementeert
                            // introspectie: check of de klasse een subtype is van Day
                            if (Day.class.isAssignableFrom(clazz)) {
                                // Maak een instantie aan via de no-args constructor en sla die op
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

    // Geeft een leesbaar label terug voor de GUI, bv. dagnummer 3 → "Day 03"
    public static String label(int number) {
        return String.format("Day %02d", number);
    }

    // Geeft de naam van het invoerbestand terug zonder extensie, bv. 3 → "day03"
    // De InputReader voegt zelf ".txt" toe.
    public static String inputName(int number) {
        return String.format("day%02d", number);
    }
}
