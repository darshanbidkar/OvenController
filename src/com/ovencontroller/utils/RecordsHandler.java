package com.ovencontroller.utils;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.ovencontroller.model.ProgramModel;
import com.ovencontroller.model.ProgramSettings;

public class RecordsHandler {

    private static final String SPLITTER   = "\t";
    private static final String SEPARATOR  = ",";
    private static final String INPUT_FILE = "./input.txt";

    public static ArrayList<ProgramSettings> getSavedSettings() {
        Scanner scanner = null;
        ArrayList<ProgramSettings> settings = new ArrayList<>();
        try {
            scanner = new Scanner(new FileReader(INPUT_FILE));
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                settings.add(getProgramSetting(line));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            // Exit gracefully. Input file not present.
        }
        return settings;
    }

    private static ProgramSettings getProgramSetting(String line) {
        String[] split = line.split(SPLITTER);
        String name = split[0];

        ArrayList<ProgramModel> models = new ArrayList<>();
        for (int i = 1; i < split.length; i++) {
            models.add(getProgramModel(split[i]));
        }
        return new ProgramSettings(name, models);
    }

    private static ProgramModel getProgramModel(String line) {
        String[] split = line.split(SEPARATOR);
        int startTemp = Integer.parseInt(split[0]);
        int endTemp = Integer.parseInt(split[1]);
        int duration = Integer.parseInt(split[2]);
        return new ProgramModel(startTemp, endTemp, duration);

    }
    
    public static void addProgramSettings(ProgramSettings settings) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(INPUT_FILE, true));
            writer.append(settings.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
