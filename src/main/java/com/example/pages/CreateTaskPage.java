package com.example.pages;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CreateTaskPage {

    private Scene scene;

    public CreateTaskPage(Stage primaryStage, Runnable returnToDashboard) {
        // === Header ===
        ImageView leftImage = new ImageView(new Image(getClass().getResourceAsStream("/images/favicon.png")));
        leftImage.setFitHeight(60);
        leftImage.setPreserveRatio(true);

        ImageView rightImage = new ImageView(new Image(getClass().getResourceAsStream("/images/profile.png")));
        rightImage.setFitHeight(60);
        rightImage.setPreserveRatio(true);

        Label header = new Label("Create Task");
        header.setFont(Font.font("Comic Sans MS", 30));
        header.setStyle("-fx-text-fill: black;");

        Region spacerLeft = new Region();
        Region spacerRight = new Region();
        HBox.setHgrow(spacerLeft, Priority.ALWAYS);
        HBox.setHgrow(spacerRight, Priority.ALWAYS);

        HBox headerBox = new HBox(10, leftImage, spacerLeft, header, spacerRight, rightImage);
        headerBox.setPadding(new Insets(10));
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setStyle("-fx-background-color: linear-gradient(to right, #a9c5f2, #528fd7);");

        // === Form Fields ===
        TextField titleField = new TextField();
        titleField.setPromptText("Type what you are planning to do …");
        titleField.setFont(Font.font("Comic Sans MS", 14));

        TextField timeField = new TextField();
        timeField.setPromptText("Type when you want this event to be done …");
        timeField.setFont(Font.font("Comic Sans MS", 14));

        TextField locationField = new TextField();
        locationField.setPromptText("(Optional) Type where you want this event occur …");
        locationField.setFont(Font.font("Comic Sans MS", 14));

        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("(Optional) Add additional notes about this event …");
        descriptionArea.setFont(Font.font("Comic Sans MS", 14));
        descriptionArea.setPrefRowCount(3);
        descriptionArea.setWrapText(true);

        VBox form = new VBox(10,
                createLabeledRow("1. *Title:", titleField),
                createLabeledRow("2. *Due Date:", timeField),
                createLabeledRow("3. Location:", locationField),
                createLabeledRow("4. Description:", descriptionArea)
        );
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color: #eeeeee; -fx-border-radius: 20; -fx-background-radius: 20; -fx-border-color: gray;");

        // === Buttons ===
        Button backButton = new Button("Go Back to Dashboard");
        Button cancelButton = new Button("Cancel Create");
        Button confirmButton = new Button("Confirm Create");

        int buttonWidth = 220;
        backButton.setPrefWidth(buttonWidth);
        cancelButton.setPrefWidth(buttonWidth);
        confirmButton.setPrefWidth(buttonWidth);

        backButton.setOnAction(e -> returnToDashboard.run());
        cancelButton.setOnAction(e -> handleCancel(titleField, timeField, locationField, descriptionArea));
        confirmButton.setOnAction(e -> handleConfirm(titleField, timeField, locationField, descriptionArea, returnToDashboard));

        applyHoverEffects(backButton, "#f8d7da", "#f5c6cb");
        applyHoverEffects(cancelButton, "#f8d7da", "#f5c6cb");
        applyHoverEffects(confirmButton, "#d4edda", "#c3e6cb");

        HBox actionButtons = new HBox(40, backButton, confirmButton, cancelButton);
        actionButtons.setAlignment(Pos.CENTER);
        actionButtons.setPadding(new Insets(20, 0, 30, 0));

        // === Final Layout ===
        VBox mainLayout = new VBox();
        mainLayout.getChildren().addAll(headerBox, form, actionButtons);
        mainLayout.setSpacing(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: white;");

        this.scene = new Scene(mainLayout, 1200, 650);
    }

    private HBox createLabeledRow(String labelText, Control inputField) {
        Label label = new Label(labelText);
        label.setFont(Font.font("Comic Sans MS", 16));
        label.setMinWidth(150);
        label.setAlignment(Pos.TOP_LEFT);

        inputField.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(inputField, Priority.ALWAYS);

        HBox row = new HBox(10, label, inputField);
        row.setAlignment(Pos.TOP_LEFT);
        row.setMaxWidth(Double.MAX_VALUE);

        if (inputField instanceof TextArea) {
            VBox.setVgrow(row, Priority.ALWAYS);
        }

        return row;
    }

    private void handleConfirm(TextField titleField, TextField timeField, TextField locationField,
                               TextArea descriptionArea, Runnable returnToDashboard) {
        String title = titleField.getText().trim();
        String time = timeField.getText().trim();
        String location = locationField.getText().trim();
        String description = descriptionArea.getText().trim();

        if (!title.isEmpty() && !time.isEmpty()) {
            try {
                File file = new File("ToDoList.txt");
                try (FileWriter fw = new FileWriter(file, true);
                     PrintWriter writer = new PrintWriter(fw)) {
                        // safer: %ovo% delimited, rather than a comma
                        writer.println(title + "%ovo%" + time + "%ovo%" + location + "%ovo%" + description);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            returnToDashboard.run();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Required Fields");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in both the Title and Due Date fields before confirming.");
            alert.showAndWait();
        }
    }

    private void handleCancel(TextField titleField, TextField timeField, TextField locationField, TextArea descriptionArea) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Task Creation");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("This will erase all entered content.");

        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(yesButton, noButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == yesButton) {
                titleField.clear();
                timeField.clear();
                locationField.clear();
                descriptionArea.clear();
            }
        });
    }

    private void applyHoverEffects(Button button, String baseColor, String hoverColor) {
        String common = "-fx-font-family: 'Comic Sans MS'; -fx-font-size: 16px; -fx-padding: 8 16;";
        button.setStyle("-fx-background-color: " + baseColor + ";" + common);
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: " + hoverColor + ";" + common + "-fx-scale-x: 1.05; -fx-scale-y: 1.05; -fx-cursor: hand;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: " + baseColor + ";" + common + "-fx-scale-x: 1.0; -fx-scale-y: 1.0;"));
    }

    public Scene getScene() {
        return scene;
    }
}

