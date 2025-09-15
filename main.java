import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class main {

    public static List<String> readAllLines(String filePath) throws IOException {
        return Files.readAllLines(Paths.get(filePath));
    }

    public static void main(String[] args) {
        try {
            List<String> lines = readAllLines("input.txt");

            List<String> outputLines = new ArrayList<>();

            for (String line : lines) {
                try {
                    int binaryCode = InputParser.parseLine(line);

                    if(binaryCode != -1) {
                        String binaryString = Integer.toBinaryString(binaryCode);
                        System.out.println(binaryString);
                        outputLines.add(binaryString);
                    }

                } catch (Exception e) {
                    System.err.println("Error parsing line: " + line);
                    System.err.println("  " + e.getMessage());
                }
            }

            // Write all processed lines to output.txt, overwriting the file
            Files.write(Paths.get("output.txt"), outputLines);
        } catch (IOException e) {
            System.err.println("Error reading/writing file: " + e.getMessage());
        }
    }
}