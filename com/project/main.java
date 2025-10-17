package com.project;
import com.project.cpu.Cpu;
import com.project.loader.RomLoader;
import com.project.memory.Memory;
import com.project.util.InputParser;
import com.project.gui.AssemblerSimulatorGUI;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;

public class main {

    public static String inputFileName = "input.txt";
    

    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AssemblerSimulatorGUI());
        

        // Memory memory = new Memory();

        // RomLoader.loadInstructionsInMemory(memory,binaryCodes);

        // Cpu cpu = new Cpu(memory);
        // cpu.run();
    }
}