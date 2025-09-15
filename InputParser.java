import java.util.Map;
import java.util.HashMap;

public class InputParser {
    private static final Map<String, Integer> OPCODES = new HashMap<>();

    static {
        OPCODES.put("HLT", 0b000000);
    }

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

        if (!OPCODES.containsKey(mnemonic)) {
            throw new IllegalArgumentException("Unknown opcode: " + mnemonic);
        }

        int opcode = OPCODES.get(mnemonic);

        switch (mnemonic){
            case "HLT":
                return opcode << 10; // opcode in top 6 bits
            case "TRAP":
                break;
            case "LDR":
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
            case "MLT":
                break;
            case "DVD":
                break;
            case "TRR":
                break;
            

        }


        // Parse all the operands
        String[] operands = parts[1].split(",");
        int r = Integer.parseInt(operands[0].trim());
        int ix = (operands.length > 1) ? Integer.parseInt(operands[1].trim()) : 0;
        int address = (operands.length > 2) ? Integer.parseInt(operands[2].trim()) : 0;
        int i = (operands.length > 3) ? Integer.parseInt(operands[3].trim()) : 0;

        // Building the final 16-bit word
        int instruction = (opcode << 10) | (r << 8) | (ix << 6) | (i << 5) | (address & 0x1F);

        return instruction;
    }
}
