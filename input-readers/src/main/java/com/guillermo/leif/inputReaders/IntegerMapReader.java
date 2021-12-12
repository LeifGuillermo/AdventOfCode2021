package com.guillermo.leif.inputReaders;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class IntegerMapReader extends AllLineReader {

    public List<List<Integer>> readMap(String filePath) throws IOException {
        List<String> lines = readAllLinesFromFile(filePath);
        return readMapFromLines(lines);
    }

    public List<List<Integer>> readMapFromLines(List<String> lines) {
        List<List<Integer>> map = new ArrayList<>();

        for (String line : lines) {
            String[] lineArray = line.trim().split("");
            List<Integer> row = Arrays.stream(lineArray).map(Integer::parseInt).collect(Collectors.toList());
            map.add(row);

        }

        return map;
    }
}
