package com.guillermo.leif.inputReaders.signalPatternNotes;

import com.guillermo.leif.inputReaders.AllLineReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SignalPatternNoteReader extends AllLineReader {

    public List<PatternOutputCouple> getAllPatternOutputCouples(String fileName) throws IOException {
        List<String> allLines = readAllLinesFromFile(fileName);

        List<PatternOutputCouple> outputs = new ArrayList<>();

        for (String line : allLines) {
            String[] splitLine = line.split(" \\| ");
            List<String> individualPatterns = List.of(splitLine[0].split(" "));
            List<String> individualOutputs = List.of(splitLine[1].split(" "));

            PatternOutputCouple patternOutputCouple = new PatternOutputCouple();
            patternOutputCouple.setPatterns(individualPatterns);
            patternOutputCouple.setOutputs(individualOutputs);
            outputs.add(patternOutputCouple);
        }
        return outputs;
    }

    public List<String> getAlLOutputs(String fileName) throws IOException {
        List<String> allLines = readAllLinesFromFile(fileName);

        List<String> outputs = new ArrayList<>();
        for (String line : allLines) {
            String[] splitLine = line.split(" \\| ");
            List<String> individualOutputs = List.of(splitLine[1].split(" "));
            outputs.addAll(individualOutputs);
        }

        return outputs;
    }


}
