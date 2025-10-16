package com.project.memory;

import com.project.util.Constants;

public class Memory {
    // we know from documentation that
    // words are 16bit so we use the short primitive data type
    private short[] memoryCells = new short[Constants.MEMORY_SIZE];

    // read a word
    public short read(int addr) {
        checkAddress(addr);
        return memoryCells[addr];
    }

    // write a word
    public void write(int addr, short value) {
        checkAddress(addr);
        memoryCells[addr] = value;
    }

    // reset everything in the memmory
    public void reset() {
        for (int i = 0; i < Constants.MEMORY_SIZE; i++) memoryCells[i] = 0;
    }

    // validates an address inside the memory
    private void checkAddress(int addr) {
        if (addr < 0 || addr >= Constants.MEMORY_SIZE)
            throw new IllegalArgumentException("MEMORY ADDRESS IS OUT OF BOUNDS!");
    }

    // reads the value inside
    // an address as an unsigned integer
    public int readUnsigned(int addr) {
        return read(addr) & 0xFFFF;
    }
}
