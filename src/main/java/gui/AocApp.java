package gui;

import domein.DomainController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AocApp extends Application {

    // De DomainController is de brug tussen de GUI en de logica (de Day-klassen).
    private final DomainController dc = new DomainController();

    // start() wordt automatisch aangeroepen door JavaFX na launch().
    // Hier bouwen we de volledige UI op en tonen we het venster.
    @Override
    public void start(Stage stage) {
        // Dropdown met alle geïmplementeerde dagen (Day01, Day02, ...).
        var daySelector = new ComboBox<Integer>();
        daySelector.getItems().addAll(dc.getAvailableDays());
        // Zorg dat de items en de geselecteerde waarde als "Day 01" getoond worden
        // in plaats van gewoon het getal.
        daySelector.setCellFactory(lv -> dayCell());
        daySelector.setButtonCell(dayCell());
        // Selecteer automatisch de eerste dag zodat de dropdown nooit leeg lijkt.
        if (!daySelector.getItems().isEmpty()) {
            daySelector.getSelectionModel().selectFirst();
        }

        // Niet-bewerkbare tekstvakken voor de uitvoer van deel 1 en deel 2.
        // Dit plakken we in de AOC website.
        var part1Output = new TextArea();
        part1Output.setEditable(false);
        part1Output.setPrefRowCount(3);

        var part2Output = new TextArea();
        part2Output.setEditable(false);
        part2Output.setPrefRowCount(3);

        // De Run-knop voert de geselecteerde dag uit en toont het resultaat.
        // setDefaultButton(true) zorgt dat Enter ook werkt als sneltoets.
        var runButton = new Button("Run");
        runButton.setDefaultButton(true);
        runButton.setOnAction(e -> {
            var selected = daySelector.getValue();
            if (selected == null) return;

            try {
                var result = dc.runDay(selected);
                part1Output.setText(result.part1());
                part2Output.setText(result.part2());
            } catch (Exception ex) {
                // Toon de foutmelding in het tekstvak zodat de app niet crasht.
                // Exception, niet RuntimeException, omdat we ook checked exceptions kunnen tegenkomen.
                // TODO: move deze fout naar een popup
                part1Output.setText("Error: " + ex.getMessage());
                part2Output.setText("");
            }
        });

        // setCancelButton(true) zorgt dat Escape ook werkt als sneltoets.
        var exitButton = new Button("Exit");
        exitButton.setCancelButton(true);
        exitButton.setOnAction(e -> stage.close());

        // Horizontale rij bovenaan: dropdown + knoppen naast elkaar.
        var buttons = new HBox(10, daySelector, runButton, exitButton);
        buttons.setAlignment(Pos.CENTER_LEFT);

        // Verticale layout voor het hele venster, van boven naar onder.
        var root = new VBox(10,
                buttons,
                new Label("Part 1:"), part1Output,
                new Label("Part 2:"), part2Output
        );
        root.setPadding(new Insets(15));
        // Laat de tekstvakken meegroeien als het venster vergroot wordt.
        VBox.setVgrow(part1Output, Priority.ALWAYS);
        VBox.setVgrow(part2Output, Priority.ALWAYS);

        stage.setTitle("Advent of Code 2024 hogent/TIAO1 style");
        stage.setScene(new Scene(root, 500, 400));
        stage.show();
    }

    // Maakt een cel aan die een dagnummer weergeeft als leesbaar label ("Day 01").
    // Wordt gebruikt voor zowel de items in de lijst als de geselecteerde waarde.
    private ListCell<Integer> dayCell() {
        return new ListCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : dc.getDayLabel(item));
            }
        };
    }

    // Startpunt van de applicatie. launch() initialiseert JavaFX en roept start() aan.
    public static void main(String[] args) {
        launch(args);
    }
}
