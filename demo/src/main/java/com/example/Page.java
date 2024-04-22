package com.example;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;


public class Page {
    private Stage stage;
    private Dataset dataset;
    private List<Integer> random_depths, random_items, branch_factors;
    private List<String> clicked_path;
    private int page_level, mistakes_no;
    private boolean is_first = false, is_last=false;
    private double duration;
    private long start_time;
    private String item_answer, page_answer;

    @SuppressWarnings("exports")
    public Page(Stage stage, int page_level,  List<Integer> random_depths, List<Integer> random_items, List<Integer> branch_factors, int mistakes_no, double duration, List<String> clicked_path) {
        this.stage = stage;
        this.dataset = new Dataset("C:\\Usask\\wide-vs-deep-menu\\Menu.csv");
        this.page_level = page_level;
        this.random_depths = random_depths;
        this.random_items = random_items;
        this.branch_factors = branch_factors;
        this.mistakes_no = mistakes_no;
        if (page_level==1){
            this.is_first = true;
        }
        if (page_level==random_depths.get(0)) {
            this.is_last = true;
        }
        this.duration = duration;
        this.item_answer = dataset.lines.get(random_items.get(0)).split(",")[0];
        if (this.is_first || this.is_last){
            this.page_answer = this.item_answer;
        }
        else {
            this.page_answer = dataset.lines.get(random_items.get(0)).split(",")[page_level];
        }
        this.clicked_path = clicked_path;
    }

    public void display() {
        // System.out.println(branch_factors);
        // System.out.println(random_depths);
        // System.out.println(random_items);
        // System.out.println("------------");
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);


        Button button1 = new Button("Button 1");
        Button button2 = new Button("Button 2");

        // Create a VBox to hold the buttons
        VBox buttonsVBox = new VBox(button1, button2);
        buttonsVBox.setSpacing(10); // Add some spacing between buttons
        buttonsVBox.setAlignment(Pos.CENTER);


        Label label = new Label((11-random_items.size()) + "/10");
        label.getStyleClass().add("label");
        layout.getChildren().add(0, label);
        label = new Label("Order " + item_answer);
        label.getStyleClass().add("label");
        layout.getChildren().add(1, label);
        
        List<String> button_texts = dataset.possibleButtons(is_last, clicked_path, branch_factors.get(0));
        Button[] buttons = new Button[button_texts.size()];
        int i = 0;
        start_time = System.nanoTime();
        for (String button_text:button_texts) {
            buttons[i] = new Button(button_text);
            buttons[i].getStyleClass().add("button");
            buttons[i].setOnAction(event -> {
                clicked_path.add(button_text);
                if (button_text.equals(page_answer)){
                    duration = duration + (System.nanoTime() - start_time) / 1_000_000_000.0;
                    if (is_last){
                        dataset.saveRecord(item_answer, random_depths.get(0), mistakes_no, duration, branch_factors.get(0));
                        if (random_depths.size()==1){
                            Platform.exit();
                        }
                        else {
                            new Page(stage, 1, next_list(random_depths), next_list(random_items), next_list(branch_factors), 0, 0, new ArrayList<>()).display();
                        }
                    }
                    else {
                        new Page(stage, page_level+1, random_depths, random_items, branch_factors, mistakes_no, duration, clicked_path).display();
                    }
                }
                else {
                    mistakes_no = mistakes_no + 1;
                    if (!is_last) {
                        new Page(stage, page_level+1, random_depths, random_items, branch_factors, mistakes_no, duration, clicked_path).display();
                    }
                }
            });
            layout.getChildren().add(buttons[i]);
            duration = duration + (System.nanoTime() - start_time) / 1_000_000_000.0;
            i = i + 1;
        }

        if (!is_first){
            Button backButton = new Button("Back");
            backButton.getStyleClass().add("back");
            backButton.setOnAction(e -> {
                duration = duration + (System.nanoTime() - start_time) / 1_000_000_000.0;
                clicked_path.remove(clicked_path.size()-1);
                new Page(stage, page_level-1, random_depths, random_items, branch_factors, mistakes_no, duration, clicked_path).display();
            });
            layout.getChildren().add(backButton);
        }

        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true); // Allow the ScrollPane to fit the width of the Scene
        scrollPane.setFitToHeight(true); // Allow the ScrollPane to fit the height of the Scene

        StackPane root = new StackPane(scrollPane);
        root.setAlignment(Pos.CENTER); // Center the S
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/com/example/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public List<Integer> next_list(List<Integer> originalList) {
        List<Integer> newList = new ArrayList<>();
        for (int i = 1; i < originalList.size(); i++) {
            newList.add(originalList.get(i));
        }
        return newList;
    }
}
