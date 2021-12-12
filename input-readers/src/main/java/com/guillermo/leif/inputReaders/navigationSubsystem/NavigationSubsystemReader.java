package com.guillermo.leif.inputReaders.navigationSubsystem;

import com.guillermo.leif.inputReaders.AllLineReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NavigationSubsystemReader extends AllLineReader {

    public List<List<String>> collectSubsystemStrings(String filePath) throws IOException {
        List<String> lines = readAllLinesFromFile(filePath);
        return lines.stream().map(line -> List.of(line.split(""))).collect(Collectors.toList());
    }
}
