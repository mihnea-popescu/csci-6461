package com.project;
import com.project.cpu.Cpu;
import com.project.loader.RomLoader;
import com.project.memory.Memory;
import com.project.util.InputParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class main {

    public static String inputFileName = "input.txt";
    public static String outputFileName = "output.txt";

    public static List<String> readAllLines(String filePath) throws IOException {
        return Files.readAllLines(Paths.get(filePath));
    }

    public static List<Integer> getBinaryCodes() {
        List<Integer> binaryCodes = new ArrayList<>();
        try {
            List<String> lines = readAllLines(inputFileName);

            List<String> outputLines = new ArrayList<>();

            for (String line : lines) {
                try {
                    int binaryCode = InputParser.parseLine(line);

                    if(binaryCode != -1) {
                        String binaryString = String.format("%16s", Integer.toBinaryString(binaryCode)).replace(' ', '0');
                        System.out.println(binaryString);
                        outputLines.add(
                                String.format("INPUT: %-70s OUTPUT: %s", line, binaryString)
                        );
                        binaryCodes.add(binaryCode);
                    }
                } catch (Exception e) {
                    System.err.println("Error parsing line: " + line);
                    System.err.println("  " + e.getMessage());
                }
            }

            Files.write(Paths.get(outputFileName), outputLines);
        } catch (IOException e) {
            System.err.println("Error reading/writing file: " + e.getMessage());
        }
        return binaryCodes;
    }

    public static void main(String[] args) {
        List<Integer> binaryCodes = main.getBinaryCodes();

        Memory memory = new Memory();

        RomLoader.loadInstructionsInMemory(memory,binaryCodes);

        Cpu cpu = new Cpu(memory);
        cpu.run();
    }
}