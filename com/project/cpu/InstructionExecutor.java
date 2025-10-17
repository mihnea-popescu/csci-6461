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
        int EA = cpu.computeEffectiveAddress(instruction.getAddress(), instruction.getIx(), instruction.isIndirect());
        int value = cpu.readUnsignedMemory(EA);
        cpu.GPR[instruction.getR()].setValue(value);
    }

    // Store Register to Memory
    private static void executeSTR(Cpu cpu, Instruction instruction) {
        int EA = cpu.computeEffectiveAddress(instruction.getAddress(), instruction.getIx(), instruction.isIndirect());
        int value = cpu.GPR[instruction.getR()].getValue();
        cpu.writeMemory(EA, (short) value);
    }

    // Load Register with Address
    private static void executeLDA(Cpu cpu, Instruction instruction) {
        int EA = cpu.computeEffectiveAddress(instruction.getAddress(), instruction.getIx(), instruction.isIndirect());
        cpu.GPR[instruction.getR()].setValue(EA);
    }

    // Load Index Register from Memory
    private static void executeLDX(Cpu cpu, Instruction instruction) {
        int EA = cpu.computeEffectiveAddress(instruction.getAddress(), 0, instruction.isIndirect());
        int value = cpu.readUnsignedMemory(EA);
        cpu.IXR[instruction.getIx() - 1].setValue(value);
    }

    // Store Index Register to Memory
    private static void executeSTX(Cpu cpu, Instruction instruction) {
        int EA = cpu.computeEffectiveAddress(instruction.getAddress(), instruction.getIx(), instruction.isIndirect());
        int value = cpu.IXR[instruction.getIx() - 1].getValue();
        cpu.writeMemory(EA, (short) value);
    }
}
