package com.guillermo.leif.inputReaders.caveMap;

import com.guillermo.leif.inputReaders.AllLineReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CaveMapHeightReader extends AllLineReader {

    public List<List<Integer>> readCaveHeights(String filePath) throws IOException {
        List<String> stringLines = readAllLinesFromFile(filePath);

        List<List<Integer>> heightMap = new ArrayList<>();

        for (String line : stringLines) {
            String[] lineArray = line.trim().split("");
            List<Integer> row = Arrays.stream(lineArray).map(Integer::parseInt).collect(Collectors.toList());
            heightMap.add(row);

        }

        return heightMap;
    }
}
