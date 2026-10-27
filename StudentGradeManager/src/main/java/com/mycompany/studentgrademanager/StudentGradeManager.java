package com.mycompany.studentgrademanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * JavaFX App
 */
public class StudentGradeManager extends Application {

    private static Scene scene;
    //attribute to store mode of window (dark or light)
    private static boolean isDarkModeActive = false; //light active

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("Login"), 800, 600);
        // connect with css file
        scene.getStylesheets().add(getClass().getResource("dashboard.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Student Management System");
        stage.show();
    }

    // Switch between application screens and preserve the active theme (Dark/Light mode)
    static void setRoot(String fxml) throws IOException {
        Parent root = loadFXML(fxml);
        scene.setRoot(root);

        // Apply dark mode style class if it was active
        if (isDarkModeActive) {
            root.getStyleClass().add("dark-mode");
        } else {
            root.getStyleClass().remove("dark-mode");
        }

        // Ensure stylesheet is always attached to the new scene root
        if (scene.getStylesheets().isEmpty()) {
            scene.getStylesheets().add(
                    StudentGradeManager.class.getResource("dashboard.css").toExternalForm()
            );
        }
    }

    // Global method to toggle program theme across all controllers
    public static void toggleProgramTheme(boolean makeDark) {
        isDarkModeActive = makeDark;
        Parent root = scene.getRoot();

        if (makeDark) {
            // bind it with css file
            if (!root.getStyleClass().contains("dark-mode")) {
                root.getStyleClass().add("dark-mode");
            }
        } else {
            // return to light mood
            root.getStyleClass().remove("dark-mode");
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StudentGradeManager.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
