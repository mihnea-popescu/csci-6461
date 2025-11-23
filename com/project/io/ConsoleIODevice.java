package com.project.io;

import com.project.cpu.Cpu;

public class ConsoleIODevice extends IoDevice {

    @Override
    public String read(Cpu cpu) {
        cpu.printToGUI("ConsoleIODevice: Waiting for input...");
        return cpu.waitForInput(); // will wait until GUI console sends input
    }

    @Override
    public void write(String value) {
        // You can either print to standard console or redirect to CPU printer callback
        System.out.println("ConsoleIODevice output: " + value);
    }
}
