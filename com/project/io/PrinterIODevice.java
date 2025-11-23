package com.project.io;

import com.project.cpu.Cpu;

public class PrinterIODevice extends IoDevice {

    @Override
    public void write(String value) {
        // Print to CPU GUI printer if callback exists
        System.out.println("PrinterIODevice output: " + value);
    }

    @Override
    public String read(Cpu cpu) {
        // Output-only device, no input
        return null;
    }
}
