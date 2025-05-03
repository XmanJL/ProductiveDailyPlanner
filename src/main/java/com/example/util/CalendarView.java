package com.example.util;

import java.time.LocalDate;
import java.time.YearMonth;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class CalendarView {
    private VBox calendarBox;
    private YearMonth currentMonth;
    private Label monthLabel;
    private GridPane calendarGrid;

    public CalendarView() {
        calendarBox = new VBox(0); // No vertical gap between header and weekdays
        calendarBox.setAlignment(Pos.TOP_CENTER);
        calendarBox.setPadding(Insets.EMPTY); // No extra padding

        currentMonth = YearMonth.now();
        monthLabel = new Label();
        monthLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button prev = new Button("← Previous");
        Button next = new Button("Next →");

        // hover effects for calendar navigation
        applyHoverEffects(prev, "#e2e3e5", "#d6d8db");
        applyHoverEffects(next, "#e2e3e5", "#d6d8db");

        prev.setOnAction(e -> changeMonth(-1));
        next.setOnAction(e -> changeMonth(1));

        // organize the heading of the calendar (previous, next, months)
        Region spacerLeft = new Region();
        Region spacerRight = new Region();
        HBox.setHgrow(spacerLeft, Priority.ALWAYS);
        HBox.setHgrow(spacerRight, Priority.ALWAYS);

        HBox heading = new HBox(10, prev, spacerLeft, monthLabel, spacerRight, next);
        heading.setAlignment(Pos.CENTER);
        heading.setPadding(new Insets(10, 20, 10, 20));

        calendarGrid = new GridPane();
        calendarGrid.setHgap(10);
        calendarGrid.setVgap(10);
        calendarGrid.setAlignment(Pos.CENTER);

        addDayHeaders();

        calendarBox.getChildren().addAll(heading, calendarGrid);
        VBox.setVgrow(calendarGrid, Priority.ALWAYS);

        updateCalendar();
    }

    private void addDayHeaders() {
        String[] weekdays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (int i = 0; i < weekdays.length; i++) {
            Label dayLabel = new Label(weekdays[i]);
            dayLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
            StackPane wrapper = new StackPane(dayLabel);
            wrapper.setPrefSize(50, 30); // Larger for visibility
            calendarGrid.add(wrapper, i, 0);
        }
    }

    private void changeMonth(int delta) {
        currentMonth = currentMonth.plusMonths(delta);
        updateCalendar();
    }

    private void updateCalendar() {
        calendarGrid.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > 0);

        LocalDate firstDay = currentMonth.atDay(1);
        int startDay = firstDay.getDayOfWeek().getValue() % 7;
        int daysInMonth = currentMonth.lengthOfMonth();
        monthLabel.setText(currentMonth.getMonth() + " " + currentMonth.getYear());

        LocalDate today = LocalDate.now();

        for (int i = 1; i <= daysInMonth; i++) {
            VBox cell = new VBox(2);
            cell.setPrefSize(50, 50); // Larger day cells
            cell.setAlignment(Pos.CENTER);

            Label dayNum = new Label(String.valueOf(i));
            dayNum.setStyle("-fx-font-size: 13px;");
            cell.getChildren().add(dayNum);

            if (today.getDayOfMonth() == i &&
                today.getMonth() == currentMonth.getMonth() &&
                today.getYear() == currentMonth.getYear()) {

                dayNum.setStyle("-fx-background-color: pink; -fx-padding: 5; -fx-border-color: red;");
                Label todayLabel = new Label("Today");
                todayLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: gray;");
                cell.getChildren().add(todayLabel);
            }

            int col = (startDay + i - 1) % 7;
            int row = ((startDay + i - 1) / 7) + 1;
            calendarGrid.add(cell, col, row);
            GridPane.setHalignment(cell, HPos.CENTER);
        }
    }

    public VBox getView() {
        return calendarBox;
    }

    // reusable hover effect method
    private void applyHoverEffects(Button button, String baseColor, String hoverColor) {
        String common = "-fx-font-family: 'Comic Sans MS'; -fx-font-size: 14px; -fx-padding: 6 12;";
        button.setStyle("-fx-background-color: " + baseColor + ";" + common);
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: " + hoverColor + ";" + common + "-fx-scale-x: 1.05; -fx-scale-y: 1.05; -fx-cursor: hand;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: " + baseColor + ";" + common + "-fx-scale-x: 1.0; -fx-scale-y: 1.0;"));
    }
}