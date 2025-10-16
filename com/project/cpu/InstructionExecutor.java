package com.project.cpu;

import com.project.instruction.Instruction;

public class InstructionExecutor {
    public static void execute(Cpu cpu, Instruction instruction) {
        int opcode = instruction.getOpcode();

        switch(opcode) {
            case 1 -> executeLDR(cpu, instruction);
            case 2 -> executeSTR(cpu, instruction);
            case 3 -> executeLDA(cpu, instruction);
            case 41 -> executeLDX(cpu, instruction);
            case 42 -> executeSTX(cpu, instruction);
            default -> cpu.triggerFault(1); // opcode is illegal
        }
    }

    // INSTRUCTIONS IMPLEMENTATIONS

    // Load Register from Memory
    private static void executeLDR(Cpu cpu, Instruction instruction) {

    }

    // Store Register to Memory
    private static void executeSTR(Cpu cpu, Instruction instruction) {

    }

    // Load Register with Address
    private static void executeLDA(Cpu cpu, Instruction instruction) {

    }

    // Load Index Register from Memory
    private static void executeLDX(Cpu cpu, Instruction instruction) {

    }

    // Store Index Register to Memory
    private static void executeSTX(Cpu cpu, Instruction instruction) {

    }
}
