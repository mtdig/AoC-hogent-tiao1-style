
// TODO: move to repository
/*
 helpers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import java.util.stream.Stream;

public class InputReader {

    public static List<String> readLines(String day) {
        try {
            var url = InputReader.class.getClassLoader().getResource(day + ".txt");
            if (url == null) throw new IllegalArgumentException("No resource: " + day + ".txt");
            return Files.readAllLines(Path.of(url.toURI()));
        } catch (Exception e) {
            throw new RuntimeException("Cannot read input for " + day, e);
        }
    }

    public static Stream<String> streamLines(String day) {
        return readLines(day).stream();
    }

    public static String readAll(String day) {
        try {
            var url = InputReader.class.getClassLoader().getResource(day + ".txt");
            if (url == null) throw new IllegalArgumentException("No resource: " + day + ".txt");
            return Files.readString(Path.of(url.toURI()));
        } catch (Exception e) {
            throw new RuntimeException("Cannot read input for " + day, e);
        }
    }
}
*/