package repository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class InputReader {

    // Leest alle regels uit een invoerbestand en geeft ze terug als een lijst van strings.
    // De parameter "day" is de bestandsnaam zonder extensie, bv. "day01".
    // Het bestand wordt opgezocht in de resources-map (src/main/resources/day01.txt).
    public static List<String> readLines(String day) {
        try {
            // Zoek het bestand op via de classloader.
            var url = InputReader.class.getClassLoader().getResource(day + ".txt");
            if (url == null) throw new IllegalArgumentException("No resource: " + day + ".txt");
            return Files.readAllLines(Path.of(url.toURI()));
        } catch (Exception e) {
            throw new RuntimeException("Cannot read input for " + day, e);
        }
    }

    // Zelfde als readLines, maar geeft een Stream terug in plaats van een List.
    // Handig als je de regels meteen wil verwerken met stream-operaties zonder ze eerst op te slaan.
    public static Stream<String> streamLines(String day) {
        return readLines(day).stream();
    }
}
