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

public class EditTaskPage {

    private Scene scene;

    public EditTaskPage(Stage primaryStage, Runnable returnToDashboard, Runnable returnToDetail) {
        // Load the image
        Image image = new Image(EditTaskPage.class.getResourceAsStream("/images/EditTaskComponent.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(1200);
        imageView.setPreserveRatio(true);

        // Helper message
        Label helperMessage = new Label("Interface construction in-progress");
        helperMessage.setStyle("-fx-font-size: 24px; -fx-text-fill: black; -fx-font-weight: bold; -fx-padding: 10px;");
        helperMessage.setAlignment(Pos.CENTER);

        // Go Back to Task Detail button
        Button backToDetailButton = new Button("Go Back to Task Detail");
        backToDetailButton.setPrefWidth(220);
        applyHoverEffects(backToDetailButton, "#fff9c4", "#fff59d");
        backToDetailButton.setOnAction(e -> returnToDetail.run());

        // Layout for button
        HBox buttonBox = new HBox(backToDetailButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(5, imageView, helperMessage, buttonBox);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        this.scene = new Scene(layout, 1200, 650);
    }

    private static void applyHoverEffects(Button button, String baseColor, String hoverColor) {
        String common = "-fx-font-family: 'Comic Sans MS'; -fx-font-size: 16px; -fx-padding: 8 16;" +
                        "-fx-background-radius: 12; -fx-border-radius: 12; -fx-border-color: #888;";
        button.setStyle("-fx-background-color: " + baseColor + ";" + common);
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: " + hoverColor + ";" + common +
                "-fx-scale-x: 1.05; -fx-scale-y: 1.05; -fx-cursor: hand;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: " + baseColor + ";" + common +
                "-fx-scale-x: 1.0; -fx-scale-y: 1.0;"));
    }

    public Scene getScene() {
        return scene;
    }
}

