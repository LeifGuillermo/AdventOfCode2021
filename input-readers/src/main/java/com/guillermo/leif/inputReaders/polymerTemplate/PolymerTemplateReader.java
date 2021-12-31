package com.guillermo.leif.inputReaders.polymerTemplate;

import com.guillermo.leif.inputReaders.AllLineReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PolymerTemplateReader extends AllLineReader {

    public String readPolymerTemplateString(String filePath) throws IOException {
        List<String> lines = readAllLinesFromFile(filePath);
        return lines.get(0);
    }

    public Map<String, String> readTemplateInstructions(String filePath) throws IOException {
        List<String> lines = readAllLinesFromFile(filePath);
        List<String> instructionLines = lines.subList(2, lines.size());

        Map<String, String> templateInstructions = new HashMap<>();
        for (String instruction : instructionLines) {
            String[] splitInstruction = instruction.split(" -> ");
            templateInstructions.put(splitInstruction[0], splitInstruction[1]);
        }

        return templateInstructions;
    }
}
