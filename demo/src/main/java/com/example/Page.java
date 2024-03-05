package com.example;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Platform;


public class Page {
    private Stage stage;
    private int page_level, total_depth, mistakes_no;
    private boolean is_first = false, is_last=false;
    private double duration;
    private long start_time;
    private String answer;

    @SuppressWarnings("exports")
    public Page(Stage stage, int page_level, int total_depth, int mistakes_no, double duration, String answer) {
        this.stage = stage;
        this.page_level = page_level;
        this.total_depth = total_depth;
        this.mistakes_no = mistakes_no;
        if (page_level==1){
            this.is_first = true;
        }
        if (page_level==total_depth) {
            this.is_last = true;
        }
        this.duration = duration;
        this.answer = answer;
    }

    public void display() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        Label label = new Label("Order " + answer);
        label.getStyleClass().add("label");
        layout.getChildren().add(0, label);
        
        Button[] buttons = new Button[5];
        start_time = System.nanoTime();
        for (int i = 0; i < 5; i++) {
            String button_text = "Button " + (i + 1);
            buttons[i] = new Button(button_text);
            buttons[i].getStyleClass().add("button");
            buttons[i].setOnAction(event -> {
                if (button_text.equals(answer)){
                    duration = duration + (System.nanoTime() - start_time) / 1_000_000_000.0;
                    if (is_last){
                        Platform.exit();
                    }
                    else {
                        new Page(stage, page_level+1, total_depth, mistakes_no, duration, find_new_answer()).display();
                    }
                }
                else {
                    mistakes_no = mistakes_no + 1;
                }
                System.out.println("Duration: " + duration);
                System.out.println("Current level: " + page_level);
                System.out.println("Mistakes: " + mistakes_no);
                System.out.println("-------------------------");
            });
            layout.getChildren().add(buttons[i]);
        }

        if (!is_first){
            Button backButton = new Button("Back");
            backButton.getStyleClass().add("back");
            backButton.setOnAction(e -> {
                duration = duration + (System.nanoTime() - start_time) / 1_000_000_000.0;
                new Page(stage, page_level-1, total_depth, mistakes_no, duration, find_new_answer()).display();
            });
            layout.getChildren().add(backButton);
        }

        Scene scene = new Scene(layout, 960, 600);
        scene.getStylesheets().add(getClass().getResource("/com/example/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public String find_new_answer() {
        return answer;
    }
}
