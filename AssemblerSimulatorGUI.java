import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AssemblerSimulatorGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AssemblerSimulatorGUI().createAndShowGUI());
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Assembler Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLayout(new BorderLayout());

        // Top panel for Registers
        JPanel registerPanel = new JPanel(new GridLayout(4, 1));
        registerPanel.setBorder(BorderFactory.createTitledBorder("Registers"));

        // GPR Panel
        JPanel gprPanel = new JPanel(new GridLayout(1, 4));
        gprPanel.setBorder(BorderFactory.createTitledBorder("GPR"));
        for (int i = 0; i < 4; i++) {
            gprPanel.add(new LabeledTextField("GPR " + i));
        }

        // IXR Panel
        JPanel ixrPanel = new JPanel(new GridLayout(1, 3));
        ixrPanel.setBorder(BorderFactory.createTitledBorder("IXR"));
        for (int i = 1; i <= 3; i++) {
            ixrPanel.add(new LabeledTextField("IXR " + i));
        }

        // PC, MAR, MBR, IR
        JPanel controlRegPanel = new JPanel(new GridLayout(1, 4));
        controlRegPanel.setBorder(BorderFactory.createTitledBorder("Control Registers"));
        controlRegPanel.add(new LabeledTextField("PC"));
        controlRegPanel.add(new LabeledTextField("MAR"));
        controlRegPanel.add(new LabeledTextField("MBR"));
        controlRegPanel.add(new LabeledTextField("IR"));

        registerPanel.add(gprPanel);
        registerPanel.add(ixrPanel);
        registerPanel.add(controlRegPanel);

        // CC and MFR
        JPanel miscPanel = new JPanel(new GridLayout(1, 2));
        miscPanel.add(new LabeledTextField("CC"));
        miscPanel.add(new LabeledTextField("MFR"));
        registerPanel.add(miscPanel);

        frame.add(registerPanel, BorderLayout.NORTH);

        // Center Panel for memory/cache, binary and buttons
        JPanel centerPanel = new JPanel(new BorderLayout());

        // Cache + Binary Panel
        JPanel memoryPanel = new JPanel(new GridLayout(1, 2));
        JTextArea cacheContent = new JTextArea(10, 20);
        cacheContent.setBorder(BorderFactory.createTitledBorder("Cache Content"));
        memoryPanel.add(new JScrollPane(cacheContent));

        JPanel binaryPanel = new JPanel(new GridLayout(3, 1));
        binaryPanel.setBorder(BorderFactory.createTitledBorder("Input"));
        JTextField octalInput = new JTextField();
        JTextField binaryOutput = new JTextField();
        octalInput.setBorder(BorderFactory.createTitledBorder("Octal Input"));
        binaryOutput.setBorder(BorderFactory.createTitledBorder("Binary Output"));

        binaryPanel.add(octalInput);
        binaryPanel.add(binaryOutput);
        memoryPanel.add(binaryPanel);

        centerPanel.add(memoryPanel, BorderLayout.CENTER);

        // Buttons with specific action listeners
        JPanel buttonPanel = new JPanel(new GridLayout(2, 5, 10, 10));

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

        // Add buttons to panel
        buttonPanel.add(loadBtn);
        buttonPanel.add(loadPlusBtn);
        buttonPanel.add(storeBtn);
        buttonPanel.add(storePlusBtn);
        buttonPanel.add(runBtn);
        buttonPanel.add(stepBtn);
        buttonPanel.add(haltBtn);
        buttonPanel.add(iplBtn);

        JTextField programFile = new JTextField();
        programFile.setBorder(BorderFactory.createTitledBorder("Program File"));

        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.add(programFile, BorderLayout.NORTH);
        controlPanel.add(buttonPanel, BorderLayout.CENTER);

        centerPanel.add(controlPanel, BorderLayout.SOUTH);
        frame.add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel: Printer + Console Input
        JPanel outputPanel = new JPanel(new GridLayout(1, 2));
        JTextArea printer = new JTextArea(5, 20);
        printer.setBorder(BorderFactory.createTitledBorder("Printer"));
        JTextField consoleInput = new JTextField();
        consoleInput.setBorder(BorderFactory.createTitledBorder("Console Input"));
        outputPanel.add(new JScrollPane(printer));
        outputPanel.add(consoleInput);

        frame.add(outputPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    // === INDIVIDUAL BUTTON METHODS ===
    private void onLoadClick() {
        System.out.println("Load button clicked");
    }

    private void onLoadPlusClick() {
        System.out.println("Load+ button clicked");
    }

    private void onStoreClick() {
        System.out.println("Store button clicked");
    }

    private void onStorePlusClick() {
        System.out.println("Store+ button clicked");
    }

    private void onRunClick() {
        System.out.println("Run button clicked");
    }

    private void onStepClick() {
        System.out.println("Step button clicked");
    }

    private void onHaltClick() {
        System.out.println("Halt button clicked");
    }

    private void onIPLClick() {
        System.out.println("IPL button clicked");
    }

    // === Helper class for labeled input ===
    class LabeledTextField extends JPanel {
        JTextField textField;

        LabeledTextField(String label) {
            setLayout(new BorderLayout());
            JLabel jLabel = new JLabel(label + ": ");
            textField = new JTextField(5);
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
