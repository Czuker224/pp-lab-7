package app.lab7;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main extends Application {

    private TextField directoryPathField;
    private TextField searchField;
    private TextArea resultArea;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("File Browser and Search");

        directoryPathField = new TextField();
        directoryPathField.setPromptText("Enter directory path");

        searchField = new TextField();
        searchField.setPromptText("Enter search phrase");

        resultArea = new TextArea(); // Initialized TextArea
        resultArea.setPrefHeight(400); // Set preferred height

        Button browseButton = new Button("Browse");
        browseButton.setOnAction(event -> browseDirectory());

        Button searchButton = new Button("Search");
        searchButton.setOnAction(event -> searchFiles());

        HBox hBox = new HBox(10, directoryPathField, browseButton);
        VBox vBox = new VBox(10, hBox, searchField, searchButton, resultArea); // Added TextArea to VBox

        Scene scene = new Scene(vBox, 600, 200);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void browseDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory");
        File selectedDirectory = directoryChooser.showDialog(null);

        if (selectedDirectory != null) {
            directoryPathField.setText(selectedDirectory.getAbsolutePath());
        }
    }

    private void searchFiles() {
        String directoryPath = directoryPathField.getText();
        if (directoryPath.isEmpty()) {
            resultArea.setText("Please provide a directory path.");
            return;
        }

        File directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            resultArea.setText("The provided path is not a directory.");
            return;
        }

        StringBuilder results = new StringBuilder();
        String searchPhrase = searchField.getText();

        searchInDirectory(directory, results, searchPhrase);
        resultArea.setText(results.toString());
    }

    private void searchInDirectory(File directory, StringBuilder results, String searchPhrase) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                try {
                    if (containsPhrase(file, searchPhrase)) {
                        results.append(file.getName()).append("\n");
                    }
                } catch (IOException e) {
                    results.append("Error reading file: ").append(file.getName()).append("\n");
                }
            }
        }
    }

    private boolean containsPhrase(File file, String searchPhrase) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(searchPhrase)) {
                    return true;
                }
            }
        } catch (IOException e) {
            throw new IOException();
        }
        return false;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
