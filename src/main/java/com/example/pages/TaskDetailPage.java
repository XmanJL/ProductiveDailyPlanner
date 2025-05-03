package com.example.pages;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TaskDetailPage {

    public static void show(Stage stage) {
        show(stage, () -> new com.example.Dashboard().start(stage));
    }

    public static void show(Stage stage, Runnable returnToDashboard) {
        // Load the image
        Image image = new Image(TaskDetailPage.class.getResourceAsStream("/images/TaskDetailComponent.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(1200);
        imageView.setPreserveRatio(true);

        // Helper message
        Label helperMessage = new Label("Interface construction in-progress");
        helperMessage.setStyle("-fx-font-size: 24px; -fx-text-fill: black; -fx-font-weight: bold; -fx-padding: 10px;");
        helperMessage.setAlignment(Pos.CENTER);

        // Go Back to Dashboard button
        Button backButton = new Button("Go Back to Dashboard");
        backButton.setPrefWidth(200);
        applyHoverEffects(backButton, "#f8d7da", "#f5c6cb");
        backButton.setOnAction(e -> returnToDashboard.run());

        // Edit button
        Button editButton = new Button("Edit");
        editButton.setPrefWidth(100);
        editButton.setStyle("-fx-background-color: linear-gradient(to bottom, #fff9c4, #ffe082); " +
                            "-fx-font-family: 'Comic Sans MS'; -fx-font-size: 16px; -fx-padding: 8 16;" +
                            "-fx-background-radius: 12; -fx-border-radius: 12; -fx-border-color: #888;");
        editButton.setOnMouseEntered(e -> editButton.setStyle(
            "-fx-background-color: linear-gradient(to bottom, #fff59d, #ffd54f); " +
            "-fx-font-family: 'Comic Sans MS'; -fx-font-size: 16px; -fx-padding: 8 16;" +
            "-fx-background-radius: 12; -fx-border-radius: 12; -fx-border-color: #666;" +
            "-fx-scale-x: 1.05; -fx-scale-y: 1.05; -fx-cursor: hand;"));
        editButton.setOnMouseExited(e -> editButton.setStyle(
            "-fx-background-color: linear-gradient(to bottom, #fff9c4, #ffe082); " +
            "-fx-font-family: 'Comic Sans MS'; -fx-font-size: 16px; -fx-padding: 8 16;" +
            "-fx-background-radius: 12; -fx-border-radius: 12; -fx-border-color: #888;" +
            "-fx-scale-x: 1.0; -fx-scale-y: 1.0;"));
        editButton.setOnAction(e -> {
            EditTaskPage editPage = new EditTaskPage(stage, returnToDashboard, () -> TaskDetailPage.show(stage, returnToDashboard));
            stage.setScene(editPage.getScene());
        });

        // Layout for buttons
        HBox buttonBox = new HBox(30, backButton, editButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(5, imageView, helperMessage, buttonBox);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 1200, 650);
        stage.setScene(scene);
        stage.setTitle("Task Details");
    }

    private static void applyHoverEffects(Button button, String baseColor, String hoverColor) {
        String common = "-fx-font-family: 'Comic Sans MS'; -fx-font-size: 16px; -fx-padding: 8 16;";
        button.setStyle("-fx-background-color: " + baseColor + ";" + common);
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: " + hoverColor + ";" + common +
                "-fx-scale-x: 1.05; -fx-scale-y: 1.05; -fx-cursor: hand;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: " + baseColor + ";" + common +
                "-fx-scale-x: 1.0; -fx-scale-y: 1.0;"));
    }
}
