
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
                return opcode << 10;
            case "LDR":
                opcode = 0b000001;

                int r = Integer.parseInt(operands[0].trim());
                int ix = (operands.length > 1) ? Integer.parseInt(operands[1].trim()) : 0;
                int address = (operands.length > 2) ? Integer.parseInt(operands[2].trim()) : 0;
                int i = (operands.length > 3) ? Integer.parseInt(operands[3].trim()) : 0;

                // Building the final 16-bit word
                instruction = (opcode << 10) | (r << 8) | (ix << 6) | (i << 5) | (address & 0x1F);
                break;
            case "STR":
                break;
            case "LDA":
                break;
            case "LDX":
                break;
            case "STX":
                break;
            case "JZ":
                break;
            case "JNE":
                break;
            case "JCC":
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
