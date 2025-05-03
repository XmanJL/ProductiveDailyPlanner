package com.example;

import com.example.pages.CreateTaskPage;
import com.example.pages.ViewHistoryPage;
import com.example.pages.TaskDetailPage;
import com.example.util.CalendarView;
import com.example.util.TaskCard;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Cursor;

public class Dashboard extends Application {

    private CalendarView calendar;
    private VBox taskList;
    private Stage primaryStage;
    private Scene dashboardScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // === Header with icons and welcome label ===
        ImageView leftImage = new ImageView(new Image(getClass().getResourceAsStream("/images/favicon.png")));
        leftImage.setFitHeight(60);
        leftImage.setPreserveRatio(true);

        ImageView rightImage = new ImageView(new Image(getClass().getResourceAsStream("/images/profile.png")));
        rightImage.setFitHeight(60);
        rightImage.setPreserveRatio(true);

        Label headerLabel = new Label("Welcome staying Productive another day, JL_ovo_!");
        headerLabel.setFont(Font.font("Comic Sans MS", 24));
        headerLabel.setStyle("-fx-text-fill: black;");
        headerLabel.setAlignment(Pos.CENTER);

        Region spacerLeft = new Region();
        Region spacerRight = new Region();
        HBox.setHgrow(spacerLeft, Priority.ALWAYS);
        HBox.setHgrow(spacerRight, Priority.ALWAYS);

        HBox headerBox = new HBox(10, leftImage, spacerLeft, headerLabel, spacerRight, rightImage);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setPadding(new Insets(10));
        headerBox.setStyle("-fx-background-color: linear-gradient(to right, #a9c5f2, #528fd7);");

        // === Sidebar with spaced buttons ===
        Button createTask = new Button("Create Task");
        applyHoverEffects(createTask, "#d4edda", "#c3e6cb");
        createTask.setPrefWidth(120);
        createTask.setOnAction(e -> openCreateTaskPage());

        Button viewHistory = new Button("View History");
        applyHoverEffects(viewHistory, "#fff3cd", "#ffe8a1");
        viewHistory.setPrefWidth(120);
        viewHistory.setOnAction(e -> ViewHistoryPage.show((Stage) viewHistory.getScene().getWindow()));

        Button logout = new Button("Log Out");
        applyHoverEffects(logout, "#f8d7da", "#f5c6cb");
        logout.setPrefWidth(120);

        Region spacerTop = new Region();
        Region spacerBottom = new Region();
        VBox.setVgrow(spacerTop, Priority.ALWAYS);
        VBox.setVgrow(spacerBottom, Priority.ALWAYS);

        VBox sidebar = new VBox(100); // adds spacing between buttons
        sidebar.setPadding(new Insets(15));
        sidebar.setAlignment(Pos.TOP_CENTER);
        sidebar.setStyle("-fx-background-color: linear-gradient(to bottom, #ffe29f, #ffa99f);");
        sidebar.getChildren().addAll(spacerTop, createTask, viewHistory, logout, spacerBottom);

        // === Task Section ===
        Label taskTitle = new Label("All Tasks");
        taskTitle.setPadding(new Insets(15));
        taskTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-alignment: center;");
        taskTitle.setMaxWidth(Double.MAX_VALUE);
        taskTitle.setAlignment(Pos.CENTER);

        taskList = new VBox(10);
        taskList.setPadding(new Insets(10));

        ScrollPane scrollPane = new ScrollPane(taskList);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        VBox taskSection = new VBox(10, taskTitle, scrollPane);
        taskSection.setPadding(new Insets(0, 20, 20, 20));
        taskSection.setPrefWidth(650);
        taskSection.setPrefHeight(500); // limit height so scroll appears when needed
        taskSection.setStyle("-fx-background-color: linear-gradient(to bottom, #e0eafc, #cfdef3);");

        // === Calendar Section ===
        calendar = new CalendarView();
        Region calendarRegion = calendar.getView();
        HBox.setHgrow(calendarRegion, Priority.ALWAYS);

        // === Main Content Layout ===
        HBox centerLayout = new HBox(20, taskSection, calendarRegion);
        centerLayout.setPadding(new Insets(20, 15, 15, 15));
        HBox.setHgrow(taskSection, Priority.ALWAYS);
        HBox.setHgrow(calendarRegion, Priority.ALWAYS);

        // === Root Layout ===
        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(headerBox);
        mainLayout.setLeft(sidebar);
        mainLayout.setCenter(centerLayout);

        dashboardScene = new Scene(mainLayout, 1200, 650);
        primaryStage.setTitle("Productive Dashboard");
        primaryStage.setScene(dashboardScene);
        primaryStage.show();

        checkForNewTask();
    }

    private void applyHoverEffects(Button button, String baseColor, String hoverColor) {
        String common = "-fx-font-family: 'Comic Sans MS'; -fx-font-size: 14px; -fx-padding: 8 16;";
        button.setStyle("-fx-background-color: " + baseColor + ";" + common);
        button.setCursor(Cursor.HAND);
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: " + hoverColor + ";" + common + "-fx-scale-x: 1.05; -fx-scale-y: 1.05;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: " + baseColor + ";" + common + "-fx-scale-x: 1.0; -fx-scale-y: 1.0;"));
    }

    private void openCreateTaskPage() {
        CreateTaskPage createPage = new CreateTaskPage(primaryStage, this::returnToDashboard);
        primaryStage.setScene(createPage.getScene());
    }

    private void returnToDashboard() {
        checkForNewTask();
        primaryStage.setScene(dashboardScene);
    }

    private void checkForNewTask() {
        File file = new File("ToDoList.txt");
        taskList.getChildren().clear();

        if (file.exists()) {
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    // notice this is now "%ovo%" delimited
                    String[] parts = line.split("%ovo%", 4);
                    if (parts.length >= 3) {
                        TaskCard task = new TaskCard(parts[1], parts[0], parts[2]);
                        addMarkAsCompleteHandler(task, line);

                        // Add TaskDetailPage launch on Details button
                        Button detailsButton = task.getDetailsButton();
                        detailsButton.setOnAction(e -> {
                            TaskDetailPage.show(primaryStage);
                        });

                        taskList.getChildren().add(task.getCard());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addMarkAsCompleteHandler(TaskCard taskCard, String originalLine) {
        Button doneButton = taskCard.getDoneButton();
        doneButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mark Task as Done");
            alert.setHeaderText("Are you sure?");
            alert.setContentText("This will permanently remove the task from your list.");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    deleteTaskFromFile(originalLine);
                    taskList.getChildren().remove(taskCard.getCard());
                }
            });
        });
    }

    private void deleteTaskFromFile(String lineToRemove) {
        File file = new File("ToDoList.txt");
        if (!file.exists()) return;

        try {
            java.util.List<String> lines = java.nio.file.Files.readAllLines(file.toPath());
            lines.removeIf(line -> line.equals(lineToRemove));
            java.nio.file.Files.write(file.toPath(), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
