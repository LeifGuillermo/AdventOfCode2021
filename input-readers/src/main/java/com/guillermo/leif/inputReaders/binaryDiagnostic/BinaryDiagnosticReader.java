package com.guillermo.leif.inputReaders.binaryDiagnostic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.guillermo.leif.inputReaders.AllLineReader;

@Component
public class BinaryDiagnosticReader extends AllLineReader {

    public List<List<Integer>> get2dDiagnosticBitList(String fileName) throws IOException {
        List<String> lines = readAllLinesFromFile(fileName);
        return get2dDiagnosticBitList(lines);
    }

    private List<List<Integer>> get2dDiagnosticBitList(List<String> lines) {

        List<List<Integer>> resultList = new ArrayList<>();

        for (String line : lines) {
            String[] splitLine = line.trim().split("");
            List<String> listOfLineStrings = List.of(splitLine);
            List<Integer> integerList = listOfLineStrings.stream().map(Integer::parseInt).collect(Collectors.toList());
            resultList.add(integerList);
        }

        return resultList;
    }

}
