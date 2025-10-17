package com.project.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


// import hardware components
import com.project.cpu.Cpu;
import com.project.loader.RomLoader;
import com.project.memory.Memory;
import com.project.util.InputParser;;

public class AssemblerSimulatorGUI {

    // === Class-level components ===
    public JTextArea cacheContent;
    public JTextArea printer;
    public JTextField consoleInput;
    public JTextField binaryOutput;
    public JTextField octalInput;
    public JTextField programFile;

    public LabeledTextField[] gprFields = new LabeledTextField[4];
    public LabeledTextField[] ixrFields = new LabeledTextField[3];
    public LabeledTextField pcField, marField, mbrField, irField;
    public LabeledTextField ccField, mfrField;

    // === Hardware Components ===
    public Memory mem;
    public Cpu cpu;


    public AssemblerSimulatorGUI() {
        mem = new Memory();
        cpu = new Cpu(mem);
        JFrame frame = new JFrame("Assembler Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLayout(new BorderLayout());

        // === Top panel for Registers ===
        JPanel registerPanel = new JPanel(new GridLayout(4, 1));
        registerPanel.setBorder(BorderFactory.createTitledBorder("Registers"));

        // GPRs
        JPanel gprPanel = new JPanel(new GridLayout(1, 4));
        gprPanel.setBorder(BorderFactory.createTitledBorder("GPR"));
        for (int i = 0; i < 4; i++) {
            gprFields[i] = new LabeledTextField("GPR " + i);
            gprPanel.add(gprFields[i]);
        }

        // IXRs
        JPanel ixrPanel = new JPanel(new GridLayout(1, 3));
        ixrPanel.setBorder(BorderFactory.createTitledBorder("IXR"));
        for (int i = 0; i < 3; i++) {
            ixrFields[i] = new LabeledTextField("IXR " + (i + 1));
            ixrPanel.add(ixrFields[i]);
        }

        // Control Registers
        JPanel controlRegPanel = new JPanel(new GridLayout(1, 4));
        controlRegPanel.setBorder(BorderFactory.createTitledBorder("Control Registers"));
        pcField = new LabeledTextField("PC");
        marField = new LabeledTextField("MAR");
        mbrField = new LabeledTextField("MBR");
        irField = new LabeledTextField("IR");
        controlRegPanel.add(pcField);
        controlRegPanel.add(marField);
        controlRegPanel.add(mbrField);
        controlRegPanel.add(irField);

        // CC and MFR
        JPanel miscPanel = new JPanel(new GridLayout(1, 2));
        ccField = new LabeledTextField("CC");
        mfrField = new LabeledTextField("MFR");
        miscPanel.add(ccField);
        miscPanel.add(mfrField);

        registerPanel.add(gprPanel);
        registerPanel.add(ixrPanel);
        registerPanel.add(controlRegPanel);
        registerPanel.add(miscPanel);

        frame.add(registerPanel, BorderLayout.NORTH);

        // === Center panel for memory, input/output, and buttons ===
        JPanel centerPanel = new JPanel(new BorderLayout());

        // Memory & Binary Panel
        JPanel memoryPanel = new JPanel(new GridLayout(1, 2));
        cacheContent = new JTextArea(10, 20);
        cacheContent.setBorder(BorderFactory.createTitledBorder("Cache Content"));
        memoryPanel.add(new JScrollPane(cacheContent));

        JPanel binaryPanel = new JPanel(new GridLayout(2, 1));
        octalInput = new JTextField();
        binaryOutput = new JTextField();
        octalInput.setBorder(BorderFactory.createTitledBorder("Octal Input"));
        binaryOutput.setBorder(BorderFactory.createTitledBorder("Binary Output"));
        binaryPanel.add(octalInput);
        binaryPanel.add(binaryOutput);
        memoryPanel.add(binaryPanel);

        centerPanel.add(memoryPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 4, 10, 10));

        JButton loadBtn = new JButton("Load");
        loadBtn.addActionListener(e -> onLoadClick());

        JButton loadPlusBtn = new JButton("Load+");
        loadPlusBtn.addActionListener(e -> onLoadPlusClick());

        JButton storeBtn = new JButton("Store");
        storeBtn.addActionListener(e -> onStoreClick());

        JButton storePlusBtn = new JButton("Store+");
        storePlusBtn.addActionListener(e -> onStorePlusClick());

        JButton runBtn = new JButton("Run");
        runBtn.addActionListener(e -> onRunClick());

        JButton stepBtn = new JButton("Step");
        stepBtn.addActionListener(e -> onStepClick());

        JButton haltBtn = new JButton("Halt");
        haltBtn.addActionListener(e -> onHaltClick());

        JButton iplBtn = new JButton("IPL");
        iplBtn.addActionListener(e -> onIPLClick());
        // Inside the constructor (already present, just showing for clarity)


        buttonPanel.add(loadBtn);
        buttonPanel.add(loadPlusBtn);
        buttonPanel.add(storeBtn);
        buttonPanel.add(storePlusBtn);
        buttonPanel.add(runBtn);
        buttonPanel.add(stepBtn);
        buttonPanel.add(haltBtn);
        buttonPanel.add(iplBtn);

        // Program file input
        programFile = new JTextField();
        programFile.setBorder(BorderFactory.createTitledBorder("Program File"));

        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.add(programFile, BorderLayout.NORTH);
        controlPanel.add(buttonPanel, BorderLayout.CENTER);

        centerPanel.add(controlPanel, BorderLayout.SOUTH);
        frame.add(centerPanel, BorderLayout.CENTER);

        // === Bottom: Printer and Console Input ===
        JPanel outputPanel = new JPanel(new GridLayout(1, 2));
        printer = new JTextArea(5, 20);
        printer.setBorder(BorderFactory.createTitledBorder("Printer"));
        consoleInput = new JTextField();
        consoleInput.setBorder(BorderFactory.createTitledBorder("Console Input"));
        outputPanel.add(new JScrollPane(printer));
        outputPanel.add(consoleInput);

        frame.add(outputPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    // === BUTTON ACTIONS ===
    public void onLoadClick() {
        cacheContent.setText("0");  // Example: set cache content to 0
        System.out.println("Load button clicked - Cache reset to 0");
    }

    public void onLoadPlusClick() {
        System.out.println("Load+ button clicked");
    }

    public void onStoreClick() {
        System.out.println("Store button clicked");
    }

    public void onStorePlusClick() {
        System.out.println("Store+ button clicked");
    }

    public void onRunClick() {
        System.out.println("Run button clicked");
    }

    public void onStepClick() {
        System.out.println("Step button clicked");
    }

    public void onHaltClick() {
        System.out.println("Halt button clicked");
    }

    public void onIPLClick() {
        System.out.println("IPL button clicked");
        String input_file_name = programFile.getText();
        List<Integer> binaryCodes = InputParser.getBinaryCodes(input_file_name);
        RomLoader.loadInstructionsInMemory(mem,binaryCodes);
        cacheContent.setText(InputParser.MemToString(mem.memoryCells));
        cpu.run();

    }

    // === LabeledTextField Inner Class ===
    class LabeledTextField extends JPanel {
        public final JTextField textField;

        LabeledTextField(String label) {
            setLayout(new BorderLayout());
            JLabel jLabel = new JLabel(label + ": ");
            textField = new JTextField(6);
            add(jLabel, BorderLayout.WEST);
            add(textField, BorderLayout.CENTER);
        }

        public String getText() {
            return textField.getText();
        }

        public void setText(String text) {
            textField.setText(text);
        }
    }
}
