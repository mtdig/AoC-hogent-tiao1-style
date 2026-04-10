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

    private final DomainController dc = new DomainController();

    @Override
    public void start(Stage stage) {
        var daySelector = new ComboBox<Integer>();
        daySelector.getItems().addAll(dc.getAvailableDays());
        daySelector.setCellFactory(lv -> dayCell());
        daySelector.setButtonCell(dayCell());
        if (!daySelector.getItems().isEmpty()) {
            daySelector.getSelectionModel().selectFirst();
        }

        var part1Output = new TextArea();
        part1Output.setEditable(false);
        part1Output.setPrefRowCount(3);

        var part2Output = new TextArea();
        part2Output.setEditable(false);
        part2Output.setPrefRowCount(3);

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
                part1Output.setText("Error: " + ex.getMessage());
                part2Output.setText("");
            }
        });

        var exitButton = new Button("Exit");
        exitButton.setCancelButton(true);
        exitButton.setOnAction(e -> stage.close());

        var buttons = new HBox(10, daySelector, runButton, exitButton);
        buttons.setAlignment(Pos.CENTER_LEFT);

        var root = new VBox(10,
                buttons,
                new Label("Part 1:"), part1Output,
                new Label("Part 2:"), part2Output
        );
        root.setPadding(new Insets(15));
        VBox.setVgrow(part1Output, Priority.ALWAYS);
        VBox.setVgrow(part2Output, Priority.ALWAYS);

        stage.setTitle("Advent of Code 2024 hogent/TIAO1 style");
        stage.setScene(new Scene(root, 500, 400));
        stage.show();
    }

    private ListCell<Integer> dayCell() {
        return new ListCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : dc.getDayLabel(item));
            }
        };
    }

    public static void main(String[] args) {
        launch(args);
    }
}
