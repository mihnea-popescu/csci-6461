package com.project.cpu;

import com.project.instruction.Instruction;
import com.project.memory.exceptions.MemoryAccessException;

public class InstructionExecutor {
    public static void execute(Cpu cpu, Instruction instruction) {
        int opcode = instruction.getOpcode();

        System.out.println("INSTRUCTION OPCODE: " + instruction.getOpcode());

        switch(opcode) {
            case 0 -> cpu.halted = true;
            case 1 -> executeLDR(cpu, instruction);
            case 2 -> executeSTR(cpu, instruction);
            case 3 -> executeLDA(cpu, instruction);
            case 8 -> executeData(cpu, instruction);
            case 9 -> executeLoc(cpu, instruction);
            case 41 -> executeLDX(cpu, instruction);
            case 42 -> executeSTX(cpu, instruction);
            case 62 -> executeOUT(cpu, instruction);
            case 63 -> executeCHK(cpu, instruction);
            default -> cpu.triggerFault(1); // opcode is illegal
        }
    }

    // INSTRUCTIONS IMPLEMENTATIONS
    // loc
    private static void executeLoc(Cpu cpu, Instruction instruction){
        cpu.PC.setValue((short)((instruction.getR()<<8)+(instruction.getIx() << 6) + (instruction.getI() << 5)+ (instruction.getAddress())));
    }
    // data
    private static void executeData(Cpu cpu, Instruction instruction){
        try{
            cpu.mem.write(cpu.PC.getValue()-1,(short)((instruction.getR()<<8)+(instruction.getIx() << 6) + (instruction.getI() << 5)+ (instruction.getAddress())));
        }
        catch (MemoryAccessException e){
            System.out.println("mem access error");
        }
        
    }

    // Load Register from Memory
    private static void executeLDR(Cpu cpu, Instruction instruction) {
        int EA = cpu.computeEffectiveAddress(instruction.getAddress(), instruction.getIx(), instruction.isIndirect());
        int value = cpu.readUnsignedMemory(EA);
        System.out.println("Executing LDR: setting value " + value + " to R" + instruction.getR() + " from memory[" + EA + "]");
        cpu.GPR[instruction.getR()].setValue(value);
    }

    // Store Register to Memory
    private static void executeSTR(Cpu cpu, Instruction instruction) {
        int EA = cpu.computeEffectiveAddress(instruction.getAddress(), instruction.getIx(), instruction.isIndirect());
        int value = cpu.GPR[instruction.getR()].getValue();
        System.out.println("Executing STR: storing value " + value + " from R" + instruction.getR() + " to memory[" + EA + "]");
        cpu.writeMemory(EA, (short) value);
    }

    // Load Register with Address
    private static void executeLDA(Cpu cpu, Instruction instruction) {
        int EA = cpu.computeEffectiveAddress(instruction.getAddress(), instruction.getIx(), instruction.isIndirect());
        System.out.println("Executing LDA: loading effective address " + EA + " into R" + instruction.getR());
        cpu.GPR[instruction.getR()].setValue(EA);
    }

    // Load Index Register from Memory
    private static void executeLDX(Cpu cpu, Instruction instruction) {
        int EA = cpu.computeEffectiveAddress(instruction.getAddress(), 0, instruction.isIndirect());
        int value = cpu.readUnsignedMemory(EA);
        System.out.println("Executing LDX: loading value " + value + " into IX" + instruction.getIx() + " from memory[" + EA + "]");
        cpu.IXR[instruction.getIx() - 1].setValue(value);
    }

    // Store Index Register to Memory
    private static void executeSTX(Cpu cpu, Instruction instruction) {
        int EA = cpu.computeEffectiveAddress(instruction.getAddress(), instruction.getIx(), instruction.isIndirect());
        int value = cpu.IXR[instruction.getIx() - 1].getValue();
        System.out.println("Executing STX: storing IX" + instruction.getIx() + " value " + value + " to memory[" + EA + "]");
        cpu.writeMemory(EA, (short) value);
    }
}
