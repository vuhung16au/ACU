package com.acu.javafx.clockpanesdemo;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Launcher class for the ClockPane JavaFX application.
 * This class serves as the main entry point for the application.
 * 
 * Combines the DisplayClock application and the ClockPane control
 * into a single compilation unit without changing logic.
 * 
 * author ACU JavaFX Team
 * version 1.0
 */
public class Launcher {
    
    /**
     * Main method that launches the JavaFX application.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Application.launch(DisplayClock.class, args);
    }

    /**
     * DisplayClock class that demonstrates the ClockPane functionality.
     * This application creates a window with an analog clock and a digital time display.
     */
    public static class DisplayClock extends Application {
        
        @Override
        public void start(Stage primaryStage) {
            // Create a clock and a label
            ClockPane clock = new ClockPane();
            String timeString = clock.getHour() + ":" + clock.getMinute() 
                + ":" + clock.getSecond();
            Label lblCurrentTime = new Label(timeString);

            // Place clock and label in border pane
            BorderPane pane = new BorderPane();
            pane.setCenter(clock);
            pane.setBottom(lblCurrentTime);
            BorderPane.setAlignment(lblCurrentTime, Pos.TOP_CENTER);

            // Create a scene and place it in the stage
            Scene scene = new Scene(pane, 250, 250);
            primaryStage.setTitle("DisplayClock"); // Set the stage title
            primaryStage.setScene(scene); // Place the scene in the stage
            primaryStage.show(); // Display the stage
        }

        /**
         * The main method is only needed for the IDE with limited
         * JavaFX support. Not needed for running from the command line.
         */
        public static void main(String[] args) {
            launch(args);
        }
    }

    /**
     * ClockPane class that extends Pane to create a custom clock component.
     * This class draws an analog clock with hour, minute, and second hands.
     */
    public static class ClockPane extends Pane {
        private int hour;
        private int minute;
        private int second;
        
        /**
         * Construct a default clock with the current time
         */
        public ClockPane() {
            setCurrentTime();
        }

        /**
         * Construct a clock with specified hour, minute, and second
         * 
         * @param hour the hour (0-23)
         * @param minute the minute (0-59)
         * @param second the second (0-59)
         */
        public ClockPane(int hour, int minute, int second) {
            this.hour = hour;
            this.minute = minute;
            this.second = second;
        }

        /** Return hour */
        public int getHour() {
            return hour;
        }

        /** Set a new hour */
        public void setHour(int hour) {
            this.hour = hour;
            paintClock();
        }

        /** Return minute */
        public int getMinute() {
            return minute;
        }

        /** Set a new minute */
        public void setMinute(int minute) {
            this.minute = minute;
            paintClock();
        }

        /** Return second */
        public int getSecond() {
            return second;
        }

        /** Set a new second */
        public void setSecond(int second) {
            this.second = second;
            paintClock();
        }
        
        /** Set the current time for the clock */
        public void setCurrentTime() {
            // Construct a calendar for the current date and time
            Calendar calendar = new GregorianCalendar();

            // Set current hour, minute and second
            this.hour = calendar.get(Calendar.HOUR_OF_DAY);
            this.minute = calendar.get(Calendar.MINUTE);
            this.second = calendar.get(Calendar.SECOND);
            
            paintClock(); // Repaint the clock
        }
        
        /** Paint the clock with all its components */
        private void paintClock() {
            // Initialize clock parameters
            double clockRadius = 
                Math.min(getWidth(), getHeight()) * 0.8 * 0.5;
            double centerX = getWidth() / 2;
            double centerY = getHeight() / 2;

            // Draw circle
            Circle circle = new Circle(centerX, centerY, clockRadius);
            circle.setFill(Color.WHITE);
            circle.setStroke(Color.BLACK);
            Text t1 = new Text(centerX - 5, centerY - clockRadius + 12, "12");
            Text t2 = new Text(centerX - clockRadius + 3, centerY + 5, "9");
            Text t3 = new Text(centerX + clockRadius - 10, centerY + 3, "3");
            Text t4 = new Text(centerX - 3, centerY + clockRadius - 3, "6");
            
            // Draw second hand
            double sLength = clockRadius * 0.8;
            double secondX = centerX + sLength * 
                Math.sin(second * (2 * Math.PI / 60));
            double secondY = centerY - sLength * 
                Math.cos(second * (2 * Math.PI / 60));
            Line sLine = new Line(centerX, centerY, secondX, secondY);
            sLine.setStroke(Color.RED);

            // Draw minute hand
            double mLength = clockRadius * 0.65;
            double xMinute = centerX + mLength * 
                Math.sin(minute * (2 * Math.PI / 60));
            double minuteY = centerY - mLength * 
                Math.cos(minute * (2 * Math.PI / 60));
            Line mLine = new Line(centerX, centerY, xMinute, minuteY);
            mLine.setStroke(Color.BLUE);
            
            // Draw hour hand
            double hLength = clockRadius * 0.5;
            double hourX = centerX + hLength * 
                Math.sin((hour % 12 + minute / 60.0) * (2 * Math.PI / 12));
            double hourY = centerY - hLength *
                Math.cos((hour % 12 + minute / 60.0) * (2 * Math.PI / 12));
            Line hLine = new Line(centerX, centerY, hourX, hourY);
            hLine.setStroke(Color.GREEN);
            
            getChildren().clear(); // Clear the pane
            getChildren().addAll(circle, t1, t2, t3, t4, sLine, mLine, hLine);
        }
        
        @Override
        public void setWidth(double width) {
            super.setWidth(width);
            paintClock();
        }
        
        @Override
        public void setHeight(double height) {
            super.setHeight(height);
            paintClock();
        }
    }
}