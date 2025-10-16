package com.project.instruction;

public class Instruction {
    private int opcode;
    private int r;
    private int ix;
    private int i;
    private int address;

    public Instruction(int opcode, int r, int ix, int i, int address) {
        this.opcode = opcode;
        this.r = r;
        this.ix = ix;
        this.i = i;
        this.address = address;
    }

    public int getOpcode() {
        return opcode;
    }

    public int getR() {
        return r;
    }

    public int getIx() {
        return ix;
    }

    public int getI() {
        return i;
    }

    public int getAddress() {
        return address;
    }
}
