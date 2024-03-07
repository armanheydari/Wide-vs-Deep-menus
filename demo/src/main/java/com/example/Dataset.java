package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Dataset {
    List<String> lines = new ArrayList<>();

    public Dataset(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        catch (IOException e) {e.printStackTrace();}
    }

    public void print_res() {
        for (String line : lines) {
            System.out.println(line);
        }
    }
    
}
