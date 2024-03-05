package com.example;
import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application {

    @SuppressWarnings("exports")
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Menu Playground");
        Page page = new Page(primaryStage, 1, 3, 0, 0, "Button 4");
        page.display();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
