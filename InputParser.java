
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
                int opcode = 0b01110000;  
                    int rx = 0b10;          
                    int ry = 0b00;        
                   
                    int instruction = (opcode << 8) | (rx << 6) | (ry << 4);
                break;
            case "DVD":
                int opcode = 0b01110001;  
                    int rx = 0b10;          
                    int ry = 0b00;         
                   
                    int instruction = (opcode << 8) | (rx << 6) | (ry << 4);

                break;
            case "TRR":
                int opcode = 0b01110010;  
                    int rx = 0b10;          
                    int ry = 0b00;         
                    
                    int instruction = (opcode << 8) | (rx << 6) | (ry << 4);

                break;
            case "AND":
                int opcode = 0b01001001;  
                    int rx = 0b0010;       
                    int ry = 0b0011;     
                    
                    int instruction = (opcode << 8) | (rx << 4) | ry;

                break;
            case "ORR":
                int opcode = 0b01001010;  
                    int rx = 0b0010;        
                    int ry = 0b0011;       
                    
                    int instruction = (opcode << 8) | (rx << 4) | ry;

                break;
            case "NOT":
                int opcode = 0b01001011;  
                    int rx = 0b0010;       
                    
                    int instruction = (opcode << 8) | (rx << 4);
                break;
            case "SRC":
                int opcode = 0b00011111;  
                    int r = 0b0010;          
                    int count = 0b0011;       
                    int lr = 0b1;             
                        
                    int instruction = (opcode << 8) | (r << 4) | (lr << 3) | (al << 2) | count;
                break;
            case "RRC":
                int opcode = 0b00100000;  
                    int r = 0b0010;         
                    int count = 0b0011;
                    int al = 0b1;           
                    
                    int instruction = (opcode << 8) | (r << 4) | (lr << 3) | (al << 2) | count;
                break;
            case "IN":
                int opcode = 0b00111101;  
                    int r = 0b0010;           
                    int devid = 0b0101;       
                    
                    int instruction = (opcode << 8) | (r << 4) | devid;
                break;
                // sinchana
            case "OUT":
                opcode = 0b111110;  // 62
                int r_out = Integer.parseInt(operands[0].trim());      // register r
                int devid_out = Integer.parseInt(operands[1].trim());  // device id

                instruction = (opcode << 10) | (r_out << 8) | (devid_out & 0x1F);
                break;
            case "CHK":
                opcode = 0b111111;  // 63
                int r_chk = Integer.parseInt(operands[0].trim());      // register r
                int devid_chk = Integer.parseInt(operands[1].trim());  // device id

                instruction = (opcode << 10) | (r_chk << 8) | (devid_chk & 0x1F);
                break;
            case "FADD":
                opcode = 0b100001;  // 33
                int fr_fadd = Integer.parseInt(operands[0].trim());   // floating register (0 or 1)
                int ix_fadd = (operands.length > 1) ? Integer.parseInt(operands[1].trim()) : 0;
                int addr_fadd = (operands.length > 2) ? Integer.parseInt(operands[2].trim()) : 0;
                int i_fadd = (operands.length > 3) ? Integer.parseInt(operands[3].trim()) : 0;

                instruction = (opcode << 10) | (fr_fadd << 8) | (ix_fadd << 6) | (i_fadd << 5) | (addr_fadd & 0x1F);
                break;
            case "FSUB":
                opcode = 0b100010;  // 34
                int fr_fsub = Integer.parseInt(operands[0].trim());   // floating register (0 or 1)
                int ix_fsub = (operands.length > 1) ? Integer.parseInt(operands[1].trim()) : 0;
                int addr_fsub = (operands.length > 2) ? Integer.parseInt(operands[2].trim()) : 0;
                int i_fsub = (operands.length > 3) ? Integer.parseInt(operands[3].trim()) : 0;

                instruction = (opcode << 10) | (fr_fsub << 8) | (ix_fsub << 6) | (i_fsub << 5) | (addr_fsub & 0x1F);              
                break;
            case "VADD":
                opcode = 0b100011;  // 35
                int fr_vadd = Integer.parseInt(operands[0].trim());   // vector length register (0 or 1)
                int ix_vadd = (operands.length > 1) ? Integer.parseInt(operands[1].trim()) : 0;
                int addr_vadd = (operands.length > 2) ? Integer.parseInt(operands[2].trim()) : 0;
                int i_vadd = (operands.length > 3) ? Integer.parseInt(operands[3].trim()) : 0;

                instruction = (opcode << 10) | (fr_vadd << 8) | (ix_vadd << 6) | (i_vadd << 5) | (addr_vadd & 0x1F);
                break;
            case "VSUB":
                opcode = 0b100100;  // 36
                int fr_vsub = Integer.parseInt(operands[0].trim());   // vector length register (0 or 1)
                int ix_vsub = (operands.length > 1) ? Integer.parseInt(operands[1].trim()) : 0;
                int addr_vsub = (operands.length > 2) ? Integer.parseInt(operands[2].trim()) : 0;
                int i_vsub = (operands.length > 3) ? Integer.parseInt(operands[3].trim()) : 0;

                instruction = (opcode << 10) | (fr_vsub << 8) | (ix_vsub << 6) | (i_vsub << 5) | (addr_vsub & 0x1F);
                break;
            case "CNVRT":
                opcode = 0b100101;  // 37
                int r_cnvrt = Integer.parseInt(operands[0].trim());
                int ix_cnvrt = (operands.length > 1) ? Integer.parseInt(operands[1].trim()) : 0;
                int addr_cnvrt = (operands.length > 2) ? Integer.parseInt(operands[2].trim()) : 0;
                int i_cnvrt = (operands.length > 3) ? Integer.parseInt(operands[3].trim()) : 0;

                instruction = (opcode << 10) | (r_cnvrt << 8) | (ix_cnvrt << 6) | (i_cnvrt << 5) | (addr_cnvrt & 0x1F);
                break;
            case "LDFR":
                opcode = 0b110010;  // 50
                int fr_ldfr = Integer.parseInt(operands[0].trim());   // floating register (0 or 1)
                int ix_ldfr = (operands.length > 1) ? Integer.parseInt(operands[1].trim()) : 0;
                int addr_ldfr = (operands.length > 2) ? Integer.parseInt(operands[2].trim()) : 0;
                int i_ldfr = (operands.length > 3) ? Integer.parseInt(operands[3].trim()) : 0;

                instruction = (opcode << 10) | (fr_ldfr << 8) | (ix_ldfr << 6) | (i_ldfr << 5) | (addr_ldfr & 0x1F);
                break;
            case "STFR":
                opcode = 0b110011;  // 51
                int fr_stfr = Integer.parseInt(operands[0].trim());   // floating register (0 or 1)
                int ix_stfr = (operands.length > 1) ? Integer.parseInt(operands[1].trim()) : 0;
                int addr_stfr = (operands.length > 2) ? Integer.parseInt(operands[2].trim()) : 0;
                int i_stfr = (operands.length > 3) ? Integer.parseInt(operands[3].trim()) : 0;

                instruction = (opcode << 10) | (fr_stfr << 8) | (ix_stfr << 6) | (i_stfr << 5) | (addr_stfr & 0x1F);
                break;
            default:
                break;
        }


        // Parse all the operands


        return instruction;
    }
}
