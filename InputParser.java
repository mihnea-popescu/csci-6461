
public class InputParser {

    public static int parseLine(String line) {
        // Remove comments
        int commentIndex = line.indexOf(";");
        if (commentIndex != -1) {
            line = line.substring(0, commentIndex);
        }
        line = line.trim();
        if (line.isEmpty()) {
            return -1; // there's nothing on this line
        }

        // Split line by spaces
        String[] parts = line.split("\\s+", 2);
        String mnemonic = parts[0].toUpperCase();

        int opcode;

        String[] operands = parts[1].split(",");

        int instruction = 0;

        switch (mnemonic){
            case "HLT":
                opcode = 0b000000;
                return opcode << 10; // opcode in top 6 bits
            // mihnea
            case "TRAP":
                opcode = 0b11110;
                return opcode << 10; // same 6 bits
            case "LDR":
                opcode = 0b000001;

                int r = Integer.parseInt(operands[0].trim());
                int ix = (operands.length > 1) ? Integer.parseInt(operands[1].trim()) : 0;
                int address = (operands.length > 2) ? Integer.parseInt(operands[2].trim()) : 0;
                int i = (operands.length > 3) ? Integer.parseInt(operands[3].trim()) : 0;

                instruction = (opcode << 10) | (r << 8) | (ix << 6) | (i << 5) | (address & 0x1F);
                break;
            case "STR":
                opcode = 0b00010;

                int r_str = Integer.parseInt(operands[0].trim());
                int ix_str = (operands.length > 1) ? Integer.parseInt(operands[1].trim()) : 0;
                int address_str = (operands.length > 2) ? Integer.parseInt(operands[2].trim()) : 0;
                int i_str = (operands.length > 3) ? Integer.parseInt(operands[3].trim()) : 0;

                instruction = (opcode << 10) | (r_str << 8) | (ix_str << 6) | (i_str << 5) | (address_str & 0x1F);
                break;
            case "LDA":
                opcode = 0b00011;

                int r_lda = Integer.parseInt(operands[0].trim());
                int ix_lda = (operands.length > 1) ? Integer.parseInt(operands[1].trim()) : 0;
                int address_lda = (operands.length > 2) ? Integer.parseInt(operands[2].trim()) : 0;
                int i_lda = (operands.length > 3) ? Integer.parseInt(operands[3].trim()) : 0;

                instruction = (opcode << 10) | (r_lda << 8) | (ix_lda << 6) | (i_lda << 5) | (address_lda & 0x1F);
                break;
            case "LDX":
                opcode = 0b100001;

                int ix_ldx = (operands.length > 1) ? Integer.parseInt(operands[0].trim()) : 0;
                int address_ldx = (operands.length > 2) ? Integer.parseInt(operands[1].trim()) : 0;
                int i_ldx = (operands.length > 3) ? Integer.parseInt(operands[2].trim()) : 0;

                instruction = (opcode << 10) | (ix_ldx << 6) | (i_ldx << 5) | (address_ldx & 0x1F);
                break;
            case "STX":
                opcode = 0b100010;

                int ix_stx = (operands.length > 1) ? Integer.parseInt(operands[0].trim()) : 0;
                int address_stx = (operands.length > 2) ? Integer.parseInt(operands[1].trim()) : 0;
                int i_stx = (operands.length > 3) ? Integer.parseInt(operands[2].trim()) : 0;

                instruction = (opcode << 10) | (ix_stx << 6) | (i_stx << 5) | (address_stx & 0x1F);
                break;
            case "JZ":
                opcode = 0b01010;

                int r_jz = Integer.parseInt(operands[0].trim());
                int ix_jz = (operands.length > 1) ? Integer.parseInt(operands[1].trim()) : 0;
                int address_jz = (operands.length > 2) ? Integer.parseInt(operands[2].trim()) : 0;
                int i_jz = (operands.length > 3) ? Integer.parseInt(operands[3].trim()) : 0;

                instruction = (opcode << 10) | (r_jz << 8) | (ix_jz << 6) | (i_jz << 5) | (address_jz & 0x1F);
                break;
            case "JNE":
                opcode = 0b01011;

                int r_jne = Integer.parseInt(operands[0].trim());
                int ix_jne = (operands.length > 1) ? Integer.parseInt(operands[1].trim()) : 0;
                int address_jne = (operands.length > 2) ? Integer.parseInt(operands[2].trim()) : 0;
                int i_jne = (operands.length > 3) ? Integer.parseInt(operands[3].trim()) : 0;

                instruction = (opcode << 10) | (r_jne << 8) | (ix_jne << 6) | (i_jne << 5) | (address_jne & 0x1F);
                break;
            case "JCC":
                opcode = 0b01100;

                int cc_jcc = Integer.parseInt(operands[0].trim());
                int ix_jcc = (operands.length > 1) ? Integer.parseInt(operands[1].trim()) : 0;
                int address_jcc = (operands.length > 2) ? Integer.parseInt(operands[2].trim()) : 0;
                int i_jcc = (operands.length > 3) ? Integer.parseInt(operands[3].trim()) : 0;

                instruction = (opcode << 10) | (cc_jcc << 8) | (ix_jcc << 6) | (i_jcc << 5) | (address_jcc & 0x1F);
                break;
                // james
            case "JMA":
                break;
            case "JSR":
                break;
            case "RFS":
                break;
            case "SOB":
                break;
            case "JGE":
                break;
            case "AMR":
                break;
            case "SMR":
                break;
            case "AIR":
                break;
            case "SIR":
                break;
                // apurva
            case "MLT":
                opcode = 0b101;
                        int rx = 0b010;
                int ry = 0b0000;

                instruction = (opcode << 12) | (rx <<  8) | (ry << 4);

                break;
            case "DVD":
                break;
            case "TRR":
                break;
            case "AND":
                break;
            case "ORR":
                break;
            case "NOT":
                break;
            case "SRC":
                break;
            case "RRC":
                break;
            case "IN":
                break;
                // sinchana
            case "OUT":
                break;
            case "CHK":
                break;
            case "FADD":
                break;
            case "FSUB":
                break;
            case "VADD":
                break;
            case "VSUB":
                break;
            case "CNVRT":
                break;
            case "LDFR":
                break;
            case "STFR":
                break;
            default:
                break;
        }


        // Parse all the operands


        return instruction;
    }
}
