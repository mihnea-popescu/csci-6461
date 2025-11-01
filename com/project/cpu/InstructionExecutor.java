package com.project.cpu;

import com.project.instruction.Instruction;
import com.project.memory.exceptions.MemoryAccessException;
import com.project.util.CacheToString;

public class InstructionExecutor {
    public static void execute(Cpu cpu, Instruction instruction) {
        int opcode = instruction.getOpcode();

        System.out.println("INSTRUCTION OPCODE: " + instruction.getOpcode());

        switch (opcode) {
            case 0 -> cpu.halted = true;
            case 1 -> executeLDR(cpu, instruction);
            case 2 -> executeSTR(cpu, instruction);
            case 3 -> executeLDA(cpu, instruction);
            case 4 -> executeAMR(cpu, instruction);
            case 5 -> executeSMR(cpu, instruction);
            case 6 -> executeAIR(cpu, instruction);
            case 7 -> executeSIR(cpu, instruction);
            case 8 -> executeJZ(cpu, instruction);
            case 9 -> executeJNE(cpu, instruction);
            case 10 -> executeJCC(cpu, instruction);
            case 11 -> executeJMA(cpu, instruction);
            case 12 -> executeJSR(cpu, instruction);
            case 13 -> executeRFS(cpu, instruction);
            case 14 -> executeSOB(cpu, instruction);
            case 15 -> executeJGE(cpu, instruction);
            case 25 -> executeSRC(cpu, instruction);
            case 26 -> executeRRC(cpu, instruction);
            case 33 -> executeLDX(cpu, instruction);
            case 34 -> executeSTX(cpu, instruction);
            case 49 -> executeIN(cpu, instruction);
            case 50 -> executeOUT(cpu, instruction);
            case 51 -> executeCHK(cpu, instruction);
            case 56 -> executeMLT(cpu, instruction);
            case 57 -> executeDVD(cpu, instruction);
            case 58 -> executeTRR(cpu, instruction);
            case 59 -> executeAND(cpu, instruction);
            case 60 -> executeORR(cpu, instruction);
            case 61 -> executeNOT(cpu, instruction);
            // fake opcodes
            case 62 -> executeData(cpu, instruction); // fake opcode
            case 63 -> executeLoc(cpu, instruction); // fake opcode
            // opcode not found
            default -> cpu.triggerFault(1);
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

    // Add Memory to Register
    private static void executeAMR(Cpu cpu, Instruction instruction) {
        int EA = cpu.computeEffectiveAddress(instruction.getAddress(), instruction.getIx(), instruction.isIndirect());
        int memVal = cpu.readUnsignedMemory(EA);
        int regVal = cpu.GPR[instruction.getR()].getValue();
        int result = regVal + memVal;
        System.out.println("Executing AMR: R" + instruction.getR() + " = " + regVal + " + " + memVal + " = " + result);
        cpu.GPR[instruction.getR()].setValue(result);
    }

    // Subtract Memory from Register
    private static void executeSMR(Cpu cpu, Instruction instruction) {
        int EA = cpu.computeEffectiveAddress(instruction.getAddress(), instruction.getIx(), instruction.isIndirect());
        int memVal = cpu.readUnsignedMemory(EA);
        int regVal = cpu.GPR[instruction.getR()].getValue();
        int result = regVal - memVal;
        System.out.println("Executing SMR: R" + instruction.getR() + " = " + regVal + " - " + memVal + " = " + result);
        cpu.GPR[instruction.getR()].setValue(result);
    }

    // Add Immediate to Register
    private static void executeAIR(Cpu cpu, Instruction instruction) {
        int immed = instruction.getAddress();
        int regVal = cpu.GPR[instruction.getR()].getValue();
        int result = regVal + immed;
        System.out.println("Executing AIR: R" + instruction.getR() + " = " + regVal + " + " + immed + " = " + result);
        cpu.GPR[instruction.getR()].setValue(result);
    }

    // Subtract Immediate from Register
    private static void executeSIR(Cpu cpu, Instruction instruction) {
        int immed = instruction.getAddress();
        int regVal = cpu.GPR[instruction.getR()].getValue();
        int result = regVal - immed;
        System.out.println("Executing SIR: R" + instruction.getR() + " = " + regVal + " - " + immed + " = " + result);
        cpu.GPR[instruction.getR()].setValue(result);
    }

    // Multiply Register
    private static void executeMLT(Cpu cpu, Instruction instruction) {
        int Rx = instruction.getR();
        int Ry = instruction.getIx();
        if (Rx % 2 != 0 || Ry % 2 != 0) {
            System.out.println("MLT has to use even numbered registers");
            cpu.triggerFault(2);
        }
        int valX = cpu.GPR[Rx].getValue();
        int valY = cpu.GPR[Ry].getValue();
        int result = valX * valY;
        short high = (short) ((result >> 16) & 0xFFFF);
        short low = (short) (result & 0xFFFF);
        System.out.println("Executing MLT: (" + Rx + "," + (Rx + 1) + ") = " + valX + " * " + valY);
        cpu.GPR[Rx].setValue(low);
        cpu.GPR[Rx + 1].setValue(high);
    }

    // Divide Register
    private static void executeDVD(Cpu cpu, Instruction instruction) {
        int Rx = instruction.getR();
        int Ry = instruction.getIx();
        if (Rx % 2 != 0 || Ry % 2 != 0) {
            System.out.println("DVD must have even numbered registers");
            cpu.triggerFault(2); // invalid register pairing
        }
        int valX = cpu.GPR[Rx].getValue();
        int valY = cpu.GPR[Ry].getValue();
        if (valY == 0) {
            System.out.println("Divide by zero - fault");
            cpu.triggerFault(3);
            return;
        }
        int quotient = valX / valY;
        int remainder = valX % valY;
        System.out.println("Executing DVD: R" + Rx + "=" + quotient + ", R" + (Rx + 1) + "=" + remainder);
        cpu.GPR[Rx].setValue(quotient);
        cpu.GPR[Rx + 1].setValue(remainder);
    }

    // Test Equality of Registers
    private static void executeTRR(Cpu cpu, Instruction instruction) {
        int Rx = instruction.getR();
        int Ry = instruction.getIx();
        boolean equal = cpu.GPR[Rx].getValue() == cpu.GPR[Ry].getValue();
        System.out.println("Executing TRR: R" + Rx + " == R" + Ry + " ? " + equal);
        if (equal) cpu.CC.setValue(cpu.CC.getValue() | 0b0001);
    }

    // Logical AND
    private static void executeAND(Cpu cpu, Instruction instruction) {
        int Rx = instruction.getR();
        int Ry = instruction.getIx();
        int result = cpu.GPR[Rx].getValue() & cpu.GPR[Ry].getValue();
        System.out.println("Executing AND: R" + Rx + " = R" + Rx + " AND R" + Ry + " = " + result);
        cpu.GPR[Rx].setValue(result);
    }

    // Logical OR
    private static void executeORR(Cpu cpu, Instruction instruction) {
        int Rx = instruction.getR();
        int Ry = instruction.getIx();
        int result = cpu.GPR[Rx].getValue() | cpu.GPR[Ry].getValue();
        System.out.println("Executing ORR: R" + Rx + " = R" + Rx + " OR R" + Ry + " = " + result);
        cpu.GPR[Rx].setValue(result);
    }

    // Logical NOT
    private static void executeNOT(Cpu cpu, Instruction instruction) {
        int Rx = instruction.getR();
        int result = ~cpu.GPR[Rx].getValue();
        System.out.println("Executing NOT: R" + Rx + " = NOT R" + Rx + " = " + result);
        cpu.GPR[Rx].setValue(result);
    }

    // Shift Register
    private static void executeSRC(Cpu cpu, Instruction instruction) {
        int count = instruction.getAddress() & 0xF;
        int lr = (instruction.getIx() >> 1) & 1;  // Left(1)/Right(0)
        int al = instruction.getIx() & 1;         // Arithmetic(1)/Logical(0)
        int r = instruction.getR();

        int val = cpu.GPR[r].getValue();
        int result;
        if (lr == 1) result = val << count;
        else if (al == 1) result = val >> count;
        else result = (val & 0xFFFF) >>> count;

        System.out.println("Executing SRC: shift R" + r + " by " + count + " (" +
                (lr == 1 ? "left" : "right") + ", " + (al == 1 ? "arith" : "logic") + ")");
        cpu.GPR[r].setValue(result);
    }

    // Rotate Register
    private static void executeRRC(Cpu cpu, Instruction instruction) {
        int count = instruction.getAddress() & 0xF;
        int lr = (instruction.getIx() >> 1) & 1;  // Left(1)/Right(0)
        int r = instruction.getR();

        int val = cpu.GPR[r].getValue() & 0xFFFF;
        int result;
        if (lr == 1)
            result = ((val << count) | (val >>> (16 - count))) & 0xFFFF;
        else
            result = ((val >>> count) | (val << (16 - count))) & 0xFFFF;

        System.out.println("Executing RRC: rotate R" + r + " by " + count + " (" +
                (lr == 1 ? "left" : "right") + ")");
        cpu.GPR[r].setValue(result);
    }

    // Jump if Zero
    private static void executeJZ(Cpu cpu, Instruction instruction) {
        int rVal = cpu.GPR[instruction.getR()].getValue();
        int EA = cpu.computeEffectiveAddress(instruction.getAddress(), instruction.getIx(), instruction.isIndirect());
        if (rVal == 0) {
            System.out.println("Executing JZ: R" + instruction.getR() + " == 0, jumping to " + EA);
            cpu.PC.setValue(EA);
        }
    }

    // Jump if Not Equal (nonzero)
    private static void executeJNE(Cpu cpu, Instruction instruction) {
        int rVal = cpu.GPR[instruction.getR()].getValue();
        int EA = cpu.computeEffectiveAddress(instruction.getAddress(), instruction.getIx(), instruction.isIndirect());
        if (rVal != 0) {
            System.out.println("Executing JNE: R" + instruction.getR() + " != 0, jumping to " + EA);
            cpu.PC.setValue(EA);
        }
    }

    // Jump if Condition Code bit set
    private static void executeJCC(Cpu cpu, Instruction instruction) {
        int ccBit = instruction.getR();
        int EA = cpu.computeEffectiveAddress(instruction.getAddress(), instruction.getIx(), instruction.isIndirect());
        if (((cpu.CC.getValue() >> ccBit) & 1) == 1) {
            System.out.println("Executing JCC: CC bit " + ccBit + " set, jumping to " + EA);
            cpu.PC.setValue(EA);
        }
    }

    // Unconditional Jump
    private static void executeJMA(Cpu cpu, Instruction instruction) {
        int EA = cpu.computeEffectiveAddress(instruction.getAddress(), instruction.getIx(), instruction.isIndirect());
        System.out.println("Executing JMA: jumping to " + EA);
        cpu.PC.setValue(EA);
    }

    // Jump to Subroutine
    private static void executeJSR(Cpu cpu, Instruction instruction) {
        int EA = cpu.computeEffectiveAddress(instruction.getAddress(), instruction.getIx(), instruction.isIndirect());
        cpu.GPR[3].setValue(cpu.PC.getValue());
        System.out.println("Executing JSR: saving PC=" + cpu.PC.getValue() + " and jumping to " + EA);
        cpu.PC.setValue(EA);
    }

    // Return From Subroutine
    private static void executeRFS(Cpu cpu, Instruction instruction) {
        int immed = instruction.getAddress();
        cpu.GPR[0].setValue(immed);
        cpu.PC.setValue(cpu.GPR[3].getValue());
        System.out.println("Executing RFS: returning to PC=" + cpu.PC.getValue() + " with R0=" + immed);
    }

    // Subtract One and Branch
    private static void executeSOB(Cpu cpu, Instruction instruction) {
        int r = instruction.getR();
        int EA = cpu.computeEffectiveAddress(instruction.getAddress(), instruction.getIx(), instruction.isIndirect());
        int val = cpu.GPR[r].getValue() - 1;
        cpu.GPR[r].setValue(val);
        if (val > 0) {
            System.out.println("Executing SOB: R" + r + " > 0, jumping to " + EA);
            cpu.PC.setValue(EA);
        }
    }

    // Jump if Greater or Equal
    private static void executeJGE(Cpu cpu, Instruction instruction) {
        int rVal = cpu.GPR[instruction.getR()].getValue();
        int EA = cpu.computeEffectiveAddress(instruction.getAddress(), instruction.getIx(), instruction.isIndirect());
        if (rVal >= 0) {
            System.out.println("Executing JGE: R" + instruction.getR() + " >= 0, jumping to " + EA);
            cpu.PC.setValue(EA);
        }
    }

    // Input from device
    private static void executeIN(Cpu cpu, Instruction instruction) {
        int r = instruction.getR();
        cpu.printToGUI("CPU waiting for input...");
        System.out.println("Executing IN: waiting for input from GUI...");

        String input = cpu.waitForInput();

        if (input != null && !input.isEmpty()) {
            int value;
            try {
                value = Integer.parseInt(input); // try numeric
            } catch (NumberFormatException e) {
                value = input.charAt(0); // fallback to first char
            }
            cpu.GPR[r].setValue(value);

            cpu.printToGUI("Received: " + input);
            System.out.println("GUI input -> R" + r + " = " + value);
        } else {
            cpu.printToGUI("No input received.");
            System.out.println("No input received.");
        }
    }


    // Output to device
    private static void executeOUT(Cpu cpu, Instruction instruction) {
        int r = instruction.getR();
        int o = (cpu.GPR[r].getValue() & 0xFF);
        String out = Integer.toString(o);

        cpu.printToGUI("Executing OUT: outputting '" + out + "' from R" + r);
        System.out.println("Executing OUT: outputting '" + out + "' from R" + r);
        System.out.println(cpu.GPR[r].getValue());
    }

    // Check device status (stub)
    private static void executeCHK(Cpu cpu, Instruction instruction) {
        System.out.println("Executing CHK: device ready flag set (simulated)");
    }
}
