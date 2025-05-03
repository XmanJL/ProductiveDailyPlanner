package com.example.pages;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewHistoryPage {

    public static void show(Stage stage) 
    {
        // Load the image
        Image image = new Image(ViewHistoryPage.class.getResourceAsStream("/images/ViewHistoryComponent.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(1200);
        imageView.setPreserveRatio(true);

        // Helper message
        Label helperMessage = new Label("Interface construction in-progress");
        helperMessage.setStyle("-fx-font-size: 24px; -fx-text-fill: black; -fx-font-weight: bold; -fx-padding: 10px;");
        helperMessage.setAlignment(Pos.CENTER);

        // Go Back button
        Button backButton = new Button("Go Back to Dashboard");
        backButton.setPrefWidth(220);
        applyHoverEffects(backButton, "#f8d7da", "#f5c6cb");
        backButton.setOnAction(e -> {
            new com.example.Dashboard().start(stage);
        });

        VBox layout = new VBox(5, imageView, helperMessage, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 1200, 650);
        stage.setScene(scene);
        stage.setTitle("View History");
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

