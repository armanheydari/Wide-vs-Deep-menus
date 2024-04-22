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
    
    public List<Integer> selectRandomItems(int n, List<Integer> branch_factors) {
        Random rand = new Random();
        Set<Integer> randomNumbers = new LinkedHashSet<>();
        while (randomNumbers.size() < n) {
            int r = rand.nextInt(64) + 1;
            if (branch_factors.get(randomNumbers.size())==4){
                r = r + 64;
            }
            randomNumbers.add(r);
        }
        return new ArrayList<>(randomNumbers);
    }

    public List<List<Integer>> selectRandomLevels() {
        List<Integer> list_1 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 2, 2, 3, 3));
        List<Integer> list_2 = new ArrayList<>(Arrays.asList(2, 2, 2, 2, 2, 2, 4, 4, 4, 4));
        List<Integer> indices = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        

        Collections.shuffle(indices);

        // Create new lists for the shuffled elements
        List<Integer> random_depths = new ArrayList<>();
        List<Integer> branch_factors = new ArrayList<>();

        // Add elements in the order of the shuffled indices
        for (int i : indices) {
            random_depths.add(list_1.get(i));
            branch_factors.add(list_2.get(i));
        }

        List<List<Integer>> lists = new ArrayList<>();
        lists.add(random_depths);
        lists.add(branch_factors);

        return lists;
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

    public List<String> possibleButtons(boolean is_last, List<String> clicked_path, int branch_factor) {
        List<String> buttons = new ArrayList<>();
        List<String> my_lines = new ArrayList<>();
        for(int i=1; i<65; i++){
            if(branch_factor==2){my_lines.add(lines.get(i));}
            else{my_lines.add(lines.get(i+64));}
        }
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

    public void saveRecord(String item_name, int depth, int mistakes_no, double duration, int branch_factor){
        String record = item_name + "," + depth + "," + duration + "," + mistakes_no + ',' + branch_factor;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output_path, true))) {
            writer.newLine();
            writer.write(record);
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
}
