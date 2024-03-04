package com.example;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    @SuppressWarnings("exports")
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Button Example");

        VBox vbox = new VBox();
        Label label = new Label("Find and click 'Button 4'");
        Button[] buttons = new Button[5];
        long start_time = System.nanoTime();
        for (int i = 0; i < 5; i++) {
            buttons[i] = new Button("Button " + (i + 1));
            int finalI = i;
            buttons[i].setOnAction(event -> {
                long end_time = System.nanoTime();
                double durationInSeconds = (end_time - start_time) / 1_000_000_000.0;
                if (finalI==3) {
                    System.out.println("You found Button " + (finalI + 1));
                }
                System.out.println("That took" + durationInSeconds + "seconds!\n");
                
            });
            vbox.getChildren().add(buttons[i]);
        }
        vbox.getChildren().add(label);
        Scene scene = new Scene(vbox, 960, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    public static void setRoot(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setRoot'");
    }
}
