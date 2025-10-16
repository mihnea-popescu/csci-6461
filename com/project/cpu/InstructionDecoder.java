package com.project.cpu;

import com.project.instruction.Instruction;

// this is used to extract opcode, R, IX, I and address from an instruction
public class InstructionDecoder {
    public static Instruction decode(int word) {
        int opcode  = (word >> 10) & 0x3F;
        int r       = (word >> 8)  & 0x03;
        int ix      = (word >> 6)  & 0x03;
        int i       = (word >> 5)  & 0x01;
        int address =  word        & 0x1F;

        return new Instruction(opcode, r, ix, i, address);
    }
}
