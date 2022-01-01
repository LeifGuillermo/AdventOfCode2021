package com.guillermo.leif.inputReaders.day15;

import com.guillermo.leif.inputReaders.AllLineReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InputReader extends AllLineReader {

    public List<List<Integer>> readAllLines(String pathToFile) throws IOException {
        List<String> stringList = readAllLinesFromFile(pathToFile);

        List<List<Integer>> riskMap = new ArrayList<>();

        for (String line : stringList) {
            String[] values = line.split("");
            List<String> valueList = List.of(values);
            List<Integer> riskRowList = valueList.stream().map(Integer::parseInt).collect(Collectors.toList());
            riskMap.add(riskRowList);
        }
        return riskMap;
    }
}
