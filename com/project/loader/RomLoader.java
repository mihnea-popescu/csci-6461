package com.project.loader;

import com.project.memory.Memory;
import com.project.memory.exceptions.MemoryAccessException;
import com.project.cpu.Cpu;

import java.util.List;

public class RomLoader {
    public static void loadInstructionsInMemory(Cpu cpu, List<Integer> binaryCodes) {
        int ins1 = binaryCodes.get(0);
        int a = 0;
        int address = 0;
        if (ins1 >> 10 == 63){
            a = 1;
            address = ins1 & 0b1111111111;
            cpu.PC.setValue(address);
        }
        for( int i = a; i < binaryCodes.size();i++) {
            try {
                cpu.mem.write(address++, (short) (int)binaryCodes.get(i));
            } catch (MemoryAccessException e) {
                System.out.println("MEMORY ACCESS EXCEPTION!");
            }
        }
    }
}
