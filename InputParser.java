
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
        int r = 0;
        int ix = 0;
        int address = 0;
        int i = 0;

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

                r = Integer.parseInt(operands[0].trim());
                ix = (operands.length > 1) ? Integer.parseInt(operands[1].trim()) : 0;
                address = (operands.length > 2) ? Integer.parseInt(operands[2].trim()) : 0;
                i = (operands.length > 3) ? Integer.parseInt(operands[3].trim()) : 0;

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
                opcode = 0b001011;
                r = 0;
                if (operands.length < 2){
                    System.out.println("JMA input error");
                    return -1;
                }
                ix = Integer.parseInt(operands[0].trim());
                address = Integer.parseInt(operands[1].trim());
                // if length > 2 there is an i else default to 0
                i = (operands.length > 2) ? Integer.parseInt(operands[2].trim()) : 0b0;
                instruction = (opcode << 10) | (r << 8) | (ix << 6) | (i << 5) | (address & 0x1F);
                break;
            case "JSR":
                opcode = 0b001100;
                if (operands.length < 2){
                    System.out.println("JSR input error");
                    return -1;
                }
                ix = Integer.parseInt(operands[0].trim());
                address = Integer.parseInt(operands[1].trim());
                // if length > 2 there is an i else default to 0
                i = (operands.length > 2) ? Integer.parseInt(operands[2].trim()) : 0b0;
                instruction = (opcode << 10) | (ix << 6) | (i << 5) | (address & 0x1F);
                break;
            case "RFS":
                opcode = 0b001101;
                if (operands.length < 1){
                    System.out.println("RFS input error");
                    return -1;
                }
                address = Integer.parseInt(operands[0].trim());
                instruction = (opcode << 10) | (address & 0x1F);
                break;
            case "SOB":
                opcode = 0b001110;
                if (operands.length<3){
                    System.out.println("SOB input error");
                    return -1;
                }
                address = Integer.parseInt(operands[2].trim());
                r = Integer.parseInt(operands[0].trim());
                ix = Integer.parseInt(operands[1].trim());
                i = (operands.length > 3) ? Integer.parseInt(operands[3].trim()) : 0b0;
                instruction = (opcode << 10) | (r << 8) | (ix << 6) | (i << 5) | (address & 0x1F);
                break;
            case "JGE":
                opcode = 0b001111;
                if (operands.length < 3){
                    System.out.println("JGE input error");
                    return -1;
                }
                address = Integer.parseInt(operands[2].trim());
                r = Integer.parseInt(operands[0].trim());
                ix = Integer.parseInt(operands[1].trim());
                i = (operands.length > 3) ? Integer.parseInt(operands[3].trim()) : 0b0;
                instruction = (opcode << 10) | (r << 8) | (ix << 6) | (i << 5) | (address & 0x1F);
                break;
            case "AMR":
                opcode = 0b100;
                if (operands.length < 3){
                    System.out.println("AMR input error");
                    return -1;
                }
                address = Integer.parseInt(operands[2].trim());
                r = Integer.parseInt(operands[0].trim());
                ix = Integer.parseInt(operands[1].trim());
                i = (operands.length > 3) ? Integer.parseInt(operands[3].trim()) : 0b0;
                instruction = (opcode << 10) | (r << 8) | (ix << 6) | (i << 5) | (address & 0x1F);
                break;
            case "SMR":
                opcode = 0b101;
                if (operands.length < 3){
                    System.out.println("SMR input error");
                    return -1;
                }
                address = Integer.parseInt(operands[2].trim());
                r = Integer.parseInt(operands[0].trim());
                ix = Integer.parseInt(operands[1].trim());
                i = (operands.length > 3) ? Integer.parseInt(operands[3].trim()) : 0b0;
                instruction = (opcode << 10) | (r << 8) | (ix << 6) | (i << 5) | (address & 0x1F);
                break;
            case "AIR":
                opcode = 0b110;
                if (operands.length < 2){
                    System.out.println("AIR input error");
                    return -1;
                }
                address = Integer.parseInt(operands[1].trim());
                r = Integer.parseInt(operands[0].trim());
                ix = 0;
                i = 0;
                instruction = (opcode << 10) | (r << 8) | (ix << 6) | (i << 5) | (address & 0x1F);
                break;
            case "SIR":
                opcode = 0b111;
                if (operands.length < 2){
                    System.out.println("SIR input error");
                    return -1;
                }
                address = Integer.parseInt(operands[1].trim());
                r = Integer.parseInt(operands[0].trim());
                instruction = (opcode << 10) | (r << 8) | (address & 0x1F);
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
