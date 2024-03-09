package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Dataset {
    List<String> lines = new ArrayList<>();
    String output_path = "C:\\Usask\\wide-vs-deep-menu\\Output.csv";

    public Dataset(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        catch (IOException e) {e.printStackTrace();}
    }
    
    public List<Integer> selectRandomItems(int n) {
        Random rand = new Random();
        Set<Integer> randomNumbers = new HashSet<>();
        while (randomNumbers.size() < n) {
            randomNumbers.add(rand.nextInt(64) + 1);
        }
        return new ArrayList<>(randomNumbers);
    }

    public List<Integer> selectRandomLevels() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        Collections.shuffle(list);
        return list;
    }

    public List<String> filterLines(int level, String item, List<String> my_lines){
        List<String> filtered_lines = new ArrayList<>();
        for (String line:my_lines) {
            if (item.equals(line.split(",")[level])) {
                filtered_lines.add(line);
            }
        }
        return filtered_lines;
    }

    public List<String> possibleButtons(boolean is_last, List<String> clicked_path) {
        List<String> buttons = new ArrayList<>();
        List<String> my_lines = new ArrayList<>();
        my_lines.addAll(lines);
        my_lines.remove(0);
        for (int i=0; i<clicked_path.size(); i++) {
            my_lines = filterLines(i+1, clicked_path.get(i), my_lines);
        }

        for (String line:my_lines) {
            if (is_last){
                buttons.add(line.split(",")[0]);
            }
            else {
                buttons.add(line.split(",")[clicked_path.size()+1]);
            }
        }

        Set<String> uniqueButtons = new LinkedHashSet<>(buttons);
        buttons.clear();
        buttons.addAll(uniqueButtons);
        return buttons;
    }

    public void saveRecord(String item_name, int depth, int mistakes_no, double duration){
        String record = item_name + "," + depth + "," + duration + "," + mistakes_no;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output_path, true))) {
            writer.newLine();
            writer.write(record);
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
}
