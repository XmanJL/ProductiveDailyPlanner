package com.example.util;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class TaskCard {

    private VBox card;
    private Button doneButton;
    private Button detailsButton;

    public TaskCard(String dueDate, String title, String location) {
        // === Labels ===
        Label dateLabel = new Label(dueDate);
        dateLabel.setFont(Font.font("Comic Sans MS", 16));

        Label titleLabel = new Label("Title: " + title);
        titleLabel.setFont(Font.font("Comic Sans MS", 16));

        Label locationLabel = new Label("Location: " + (location.isEmpty() ? "N/A" : location));
        locationLabel.setFont(Font.font("Comic Sans MS", 16));

        // === Buttons ===
        detailsButton = new Button("Task Detail");
        doneButton = new Button("Mark as Done");

        styleDetailButton(detailsButton);
        styleDoneButton(doneButton);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox buttonRow = new HBox(20, detailsButton, spacer, doneButton);
        buttonRow.setAlignment(Pos.CENTER_LEFT);
        buttonRow.setPadding(new Insets(10, 0, 0, 0));

        // === Card Layout ===
        VBox textSection = new VBox(5, dateLabel, titleLabel, locationLabel);
        textSection.setAlignment(Pos.TOP_LEFT);

        VBox content = new VBox(10, textSection, buttonRow);
        content.setPadding(new Insets(10));

        card = new VBox(content);
        card.setPadding(new Insets(10));
        card.setStyle("-fx-background-color: linear-gradient(to right, #d3d3d3, #a9a9a9); " +
                      "-fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: gray;");
    }

    public VBox getCard() {
        return card;
    }

    public Button getDoneButton() {
        return doneButton;
    }

    public Button getDetailsButton() {
        return detailsButton;
    }

    private void styleDetailButton(Button button) {
        String base = "-fx-background-color: white; -fx-font-family: 'Comic Sans MS'; -fx-font-size: 14px; " +
                      "-fx-padding: 6 14; -fx-background-radius: 10;";
        button.setStyle(base);
        button.setCursor(Cursor.HAND);
        button.setOnMouseEntered(e -> button.setStyle(base + "-fx-effect: dropshadow(two-pass-box, gray, 4, 0, 0, 2);"));
        button.setOnMouseExited(e -> button.setStyle(base));
    }

    private void styleDoneButton(Button button) {
        String base = "-fx-background-color: #d4edda; -fx-font-family: 'Comic Sans MS'; -fx-font-size: 14px; " +
                      "-fx-padding: 6 14; -fx-background-radius: 10;";
        String hover = "-fx-background-color: #c3e6cb;";
        button.setStyle(base);
        button.setCursor(Cursor.HAND);
        button.setOnMouseEntered(e -> button.setStyle(base + hover));
        button.setOnMouseExited(e -> button.setStyle(base));
    }
}

