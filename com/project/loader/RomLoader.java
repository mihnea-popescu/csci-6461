package com.project.loader;

import com.project.memory.Memory;
import com.project.memory.exceptions.MemoryAccessException;
import com.project.cpu.Cpu;

import java.util.List;

public class RomLoader {
    public static void loadInstructionsInMemory(Cpu cpu, List<Integer> binaryCodes) {
        
        int address = 0;
        for( int i = 0; i < binaryCodes.size();i++) {
            try {
                int ins1 = binaryCodes.get(i);
                
                if (ins1 >> 10 == 63) { // LOC instruction
                    address = ins1 & 0b1111111111;
                    cpu.PC.setValue(address);
                }
                else if (ins1 >> 10 == 62) { // Data instruction
                    int data = ins1 & 0b1111111111;
                    cpu.mem.write(address, (short)(data));
                    address++;
                    cpu.PC.setValue(address);
                }
                else{
                    cpu.mem.write(address, (short) (int)binaryCodes.get(i));
                    address++;
                }
                
            } catch (MemoryAccessException e) {
                System.out.println("MEMORY ACCESS EXCEPTION!"); //Exception
            }
        }
    }
}
