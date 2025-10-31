package com.project.gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

// import hardware components
import com.project.cpu.Cpu;
import com.project.cpu.InstructionDecoder;
import com.project.loader.RomLoader;
import com.project.memory.Memory;
import com.project.util.CacheToString;
import com.project.util.Constants;
import com.project.util.InputParser;

public class AssemblerSimulatorGUI {

    // === Class-level components ===
    public JTextArea memoryContent;
    public JTextArea cacheContent;
    public JTextArea printer;
    public JTextField consoleInput;
    public JTextField binaryOutput;
    public JTextField octalInput;
    public JTextField programFile;

    public LabeledTextField[] gprFields = new LabeledTextField[Constants.NUM_GPRS];
    public LabeledTextField[] ixrFields = new LabeledTextField[Constants.NUM_IXRS];
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
        frame.setSize(1000, 750);
        frame.setLayout(new BorderLayout());

        // === Top panel for Registers ===
        JPanel registerPanel = new JPanel(new GridLayout(4, 1));
        registerPanel.setBorder(BorderFactory.createTitledBorder("Registers"));

        // GPRs
        JPanel gprPanel = new JPanel(new GridLayout(1, 4));
        gprPanel.setBorder(BorderFactory.createTitledBorder("GPR"));
        for (int i = 0; i < Constants.NUM_GPRS; i++) {
            gprFields[i] = new LabeledTextField("GPR " + i);
            gprPanel.add(gprFields[i]);
        }

        // IXRs
        JPanel ixrPanel = new JPanel(new GridLayout(1, 3));
        ixrPanel.setBorder(BorderFactory.createTitledBorder("IXR"));
        for (int i = 0; i < Constants.NUM_IXRS; i++) {
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

        // === Center panel for memory, cache, input/output, and buttons ===
        JPanel centerPanel = new JPanel(new BorderLayout());

        // === Memory & Cache Panel ===
        JPanel memoryPanel = new JPanel(new GridLayout(2, 2, 5, 5));

        // Memory Content
        memoryContent = new JTextArea(10, 20);
        memoryContent.setBorder(BorderFactory.createTitledBorder("Memory Content"));
        memoryPanel.add(new JScrollPane(memoryContent));

        // Binary/Octal Panel
        JPanel binaryPanel = new JPanel(new GridLayout(2, 1));
        octalInput = new JTextField();
        binaryOutput = new JTextField();
        octalInput.setBorder(BorderFactory.createTitledBorder("Octal Input"));
        binaryOutput.setBorder(BorderFactory.createTitledBorder("Binary Output"));
        binaryPanel.add(octalInput);
        binaryPanel.add(binaryOutput);
        memoryPanel.add(binaryPanel);

        // Cache Content
        cacheContent = new JTextArea(10, 20);
        cacheContent.setBorder(BorderFactory.createTitledBorder("Cache Content"));
        cacheContent.setEditable(false);
        memoryPanel.add(new JScrollPane(cacheContent));

        // filler for grid balance
        memoryPanel.add(new JPanel());

        centerPanel.add(memoryPanel, BorderLayout.CENTER);

        // === Buttons ===
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
        printer.setEditable(false);
        consoleInput = new JTextField();
        consoleInput.setBorder(BorderFactory.createTitledBorder("Console Input"));
        consoleInput.addActionListener(e -> {
            String text = consoleInput.getText();
            consoleInput.setText("");
            printer.append("User input: " + text + "\n");
            cpu.provideInput(text); // send to CPU
        });
        outputPanel.add(new JScrollPane(printer));
        outputPanel.add(consoleInput);

        frame.add(outputPanel, BorderLayout.SOUTH);

        // Initialize display
        update_display();

        frame.setVisible(true);
    }

    private String toBinaryString(int value, int bits) {
        int mask = (1 << bits) - 1;
        return String.format("%" + bits + "s", Integer.toBinaryString(value & mask))
                     .replace(' ', '0');
    }

    // === Display utility ===
    public void update_display() {
        memoryContent.setText(InputParser.MemToString(mem.memoryCells));
        // cache display
        cacheContent.setText(CacheToString.cacheToString(cpu.cache));

        for (int i = 0; i < Constants.NUM_GPRS; i++) {
            gprFields[i].setText(toBinaryString(cpu.GPR[i].getValue(), 16));
        }

        for (int i = 0; i < Constants.NUM_IXRS; i++) {
            ixrFields[i].setText(toBinaryString(cpu.IXR[i].getValue(), 16));
        }

        pcField.setText(toBinaryString(cpu.PC.getValue(), 12));
        ccField.setText(toBinaryString(cpu.CC.getValue(), 4));
        irField.setText(toBinaryString(cpu.IR.getValue(), 16));
        marField.setText(toBinaryString(cpu.MAR.getValue(), 12));
        mfrField.setText(toBinaryString(cpu.MFR.getValue(), 4));
    }

    // Only clear user input fields, not printer log
    public void clear() {
        octalInput.setText("");
        binaryOutput.setText("");
        consoleInput.setText("");
    }

    // === BUTTON ACTIONS ===
    public void onLoadClick() {
        clear();
        String instruction = consoleInput.getText();
        int bin_input = InputParser.parseLine(instruction);
        if (bin_input == -1) {
            return;
        }
        else{
            binaryOutput.setText(Integer.toBinaryString(bin_input));
            octalInput.setText(Integer.toOctalString(bin_input));
            cpu.execute(InstructionDecoder.decode(bin_input));
        }
        
        printer.append("Load button clicked, load instruction into the CPU\n");
    }

    public void onLoadPlusClick() {
        clear();
        for (int i = 0; i < Constants.NUM_GPRS; i++) {
            cpu.GPR[i].setValue(Integer.parseInt(gprFields[i].getText(), 2));
            gprFields[i].setText(toBinaryString(cpu.GPR[i].getValue(), 16));
        }
        printer.append("Load+ button clicked\n");
    }

    public void onStoreClick() {
        clear();
        printer.append("Store button clicked\n");
    }

    public void onStorePlusClick() {
        clear();
        printer.append("Store+ button clicked\n");
    }

    public void onRunClick() {
        clear();
        printer.append("Run button clicked\n");

        Thread cpuThread = new Thread(() -> {
            cpu.run();
            SwingUtilities.invokeLater(() -> {
                update_display();
                printer.append("Program execution finished.\n");
            });
        });
        cpuThread.start();
    }


    public void onStepClick() {
        clear();
        printer.append("Step button clicked\n");

        // Set PC from GUI before running
        cpu.PC.setValue(Integer.parseInt(pcField.getText(), 2));
        pcField.setText(toBinaryString(cpu.PC.getValue(), 12));

        // Run CPU step in a separate thread
        new Thread(() -> {
            if (!cpu.halted) {
                cpu.step();

                // Update GUI safely on EDT
                SwingUtilities.invokeLater(() -> {
                    printer.append("Step executed.\n");
                    update_display();
                });
            } else {
                SwingUtilities.invokeLater(() -> {
                    printer.append("Execution finished.\n");
                    update_display();
                });
            }
        }).start();
    }


    public void onHaltClick() {
        clear();
        cpu.halted = true;
        printer.append("Halt button clicked\n");
    }

    public void hardware_clear() {
        mem = new Memory();
        cpu = new Cpu(mem);
        update_display();
    }

    public void onIPLClick() {
        clear();
        hardware_clear();
        printer.append("IPL button clicked\n");
        String input_file_name = programFile.getText();
        List<Integer> binaryCodes = InputParser.getBinaryCodes(input_file_name);
        if (binaryCodes == null) {
            printer.append("File not found.\n");
            return;
        }
        RomLoader.loadInstructionsInMemory(cpu, binaryCodes);
        printer.append("Program loaded successfully.\n");
        update_display();
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
