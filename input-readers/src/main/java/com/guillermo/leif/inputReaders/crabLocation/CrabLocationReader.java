package com.guillermo.leif.inputReaders.crabLocation;

import com.guillermo.leif.inputReaders.AllLineReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CrabLocationReader extends AllLineReader {

    public List<Integer> getCrabLocations(String fileName) throws IOException {
        return Stream.of(readAllLinesFromFile(fileName).get(0).split(",")).map(Integer::parseInt).collect(Collectors.toList());
    }
}
