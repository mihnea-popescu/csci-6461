package com.project.cpu;

import com.project.instruction.Instruction;
import com.project.memory.Memory;
import com.project.memory.exceptions.MemoryAccessException;
import com.project.util.Constants;

import java.util.Arrays;

public class Cpu {
    // Setting up registers
    public Register PC = new Register(12), // program counter - address of the next instruction
            CC = new Register(4), // condition code - set when arithmetic/logic operations are done
            IR = new Register(16), // instruction register - instruction to be executed
            MAR = new Register(12), // address of the word to be fetched from the memory
            MFR = new Register(4); // id code of a machine fault

    public Register[] GPR = new Register[Constants.NUM_GPRS]; // used for computation
    public Register[] IXR = new Register[Constants.NUM_IXRS]; // used for addressing

    // Init memory
    public Memory mem;

    // indicates whether the cpu execution has been halted
    public boolean halted = false;

    public Cpu(Memory mem){
        // Set up GPR and IXR registers
        Arrays.setAll(GPR, i -> new Register(16));
        Arrays.setAll(IXR, i -> new Register(16));

        this.mem = mem;
    }

    private short readMemory(int addr) {
        try {
            return mem.read(addr);
        } catch (MemoryAccessException e) {
            this.handleMemoryError();
        }
        return -1;
    }

    private void writeMemory(int addr, short value) {
        try {
            mem.write(addr, value);
        } catch (MemoryAccessException e) {
            this.handleMemoryError();
        }
    }

    // reads the instruction from memory
    public void fetch() {
            MAR.setValue(PC.getValue());
            short instruction = readMemory(MAR.getValue()); // interpret the instruction and split it into R, IX, etc
            IR.setValue(instruction);
            PC.increment(); // move to next instruction
    }

    // extracts the values needed from the instruction
    public Instruction decode() {
        return InstructionDecoder.decode(IR.getValue());
    }

    public void execute(Instruction instruction) {
        InstructionExecutor.execute(this, instruction);
    }

    public void triggerFault(int val) {
       MFR.setValue(val);

       // setting the condition code fault bit to indicate a fault
       int ccVal = CC.getValue();
       ccVal = ccVal | 0x1; // set the first bit
       CC.setValue(ccVal);

       this.halted = true;

       System.out.println("MACHINE FAULT! CODE: " + val + ".");
       System.out.println("CPU Execution has been halted.");
    }

    private void handleMemoryError() {
//        MFR.setValue(val);

        // setting the condition code fault bit to indicate a fault
        int ccVal = CC.getValue();
        ccVal = ccVal | 0x1000; // set the fourth bit
        CC.setValue(ccVal);

        this.halted = true;

        System.out.println("MEMORY ERROR!");
        System.out.println("CPU Execution has been halted.");
    }
}
