package com.example;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application {

    @SuppressWarnings("exports")
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Menu Playground");
        
        Dataset dataset = new Dataset("C:\\Usask\\wide-vs-deep-menu\\Menu.csv");
        List<Integer> random_depths = dataset.selectRandomLevels();
        List<Integer> random_items = dataset.selectRandomItems(12);
        List<String> clicked_path = new ArrayList<>();
        
        Page page = new Page(primaryStage, 1, random_depths, random_items, 0, 0, clicked_path);
        page.display();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
