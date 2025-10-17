package com.project.loader;

import com.project.memory.Memory;
import com.project.memory.exceptions.MemoryAccessException;

import java.util.List;

public class RomLoader {
    public static void loadInstructionsInMemory(Memory mem, List<Integer> binaryCodes) {
        int address = 0;

        for(int instruction : binaryCodes) {
            try {
                mem.write(address++, (short) instruction);
            } catch (MemoryAccessException e) {
                System.out.println("MEMORY ACCESS EXCEPTION!");
            }
        }
    }
}
