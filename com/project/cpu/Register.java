package com.project.cpu;

public class Register {
    // This class is used for both general purpose registers
    // and special purpose registers
    private int value;

    private final int size; // number of bits in this register

    public Register(int size) {
        if (size < 1 || size > 32) // size has to be (1-32)
            throw new IllegalArgumentException("Invalid register size!");
        this.size = size;
        this.value = 0;
    }

    // used for unsigned values
    public int getValue() {
        int mask = (1 << size) - 1;
        return value & mask;
    }

    // used for signed values
    // returns twos complement interpretation
    public int getSignedValue() {
        int mask = (1 << size) - 1;
        int val = value & mask;
        int signBit = 1 << (size - 1);
        return (val ^ signBit) - signBit;  // sign-extend to 32-bit int
    }

    public void setValue(int value) {
        // value is automatically masked to the bit size
        this.value = value & ((1 << size) - 1);
    }

    // used to increment PC register
    public void increment() {
        this.setValue(this.getValue() + 1);
    }

    public void clear() {
        this.value = 0;
    }

    @Override
    public String toString() {
        return Integer.toBinaryString(getValue());
    }
}
