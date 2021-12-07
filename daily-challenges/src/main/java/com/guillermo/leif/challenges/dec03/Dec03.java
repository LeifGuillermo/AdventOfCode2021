package com.guillermo.leif.challenges.dec03;

import com.guillermo.leif.challenges.Challenge;
import com.guillermo.leif.inputReaders.binaryDiagnostic.BinaryDiagnosticReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@Challenge3
public class Dec03 implements Challenge {

    private BinaryDiagnosticReader binaryDiagnosticReader;

    @Autowired
    public Dec03(BinaryDiagnosticReader binaryDiagnosticReader) {
        this.binaryDiagnosticReader = binaryDiagnosticReader;
    }

    @Override
    public void solveProblem() throws Exception {
        int diagnosticColumns = 12;
        final String sampleFilePath = "daily-challenges/src/main/resources/inputFiles/day3/sample1.txt";
        final String filePath = "daily-challenges/src/main/resources/inputFiles/day3/diagnosticInput.txt";

        List<List<Integer>> bitList = binaryDiagnosticReader.get2dDiagnosticBitList(filePath);


        List<List<Integer>> oxygen = part2Oxygen(bitList, diagnosticColumns);
        List<List<Integer>> c02 = part2C02(bitList, diagnosticColumns);


        String oxygenString = Arrays.toString(oxygen.get(0).toArray());
        String co2String = Arrays.toString(c02.get(0).toArray());
        log.info("READABLE_OXYGEN_BITS: " + oxygenString);
        log.info("READABLE_CO2_BITS: " + co2String);

        String oxygenBitString = convertArrayStringOfBitsIntoBitString(oxygenString);
        String co2BitString = convertArrayStringOfBitsIntoBitString(co2String);

        log.info("OXYGEN_BITS: " + oxygenBitString);
        log.info("CO2_BITS: " + co2BitString);

        Integer oxygenInteger = Integer.parseInt(oxygenBitString, 2);
        Integer co2Integer = Integer.parseInt(co2BitString, 2);
        log.info("OXYGEN_INTEGER: " + oxygenInteger);
        log.info("CO2_INTEGER: " + co2Integer);

        log.info("LIFE_SUPPORT_RATING (oxygen * co2): " + oxygenInteger * co2Integer);

//        List<Integer> greatestValues = getGreatestValueArray(bitList, diagnosticColumns);
//        log.info("GREATEST VALUE ARRAY: " + Arrays.toString(greatestValues.toArray()));
    }

    private String convertArrayStringOfBitsIntoBitString(String arrayStringOfBits) {
        return arrayStringOfBits.replaceAll("\\[", "").replaceAll("]", "")
                .replaceAll(",", "").replaceAll(" ", "");
    }

    private List<List<Integer>> part2Oxygen(final List<List<Integer>> bitList, int diagnosticColumns) throws Exception {
        List<List<Integer>> currentValues = bitList;
        for (int criteriaColumn = 0; criteriaColumn < diagnosticColumns; criteriaColumn++) {
            List<Integer> greatestValues = getGreatestValueArray(currentValues, diagnosticColumns);
            int bit = greatestValues.get(criteriaColumn);
            currentValues = filterValuesWithBitInPosition(bit, criteriaColumn, currentValues);
            if (currentValues.size() == 1) {
                break;
            }
        }

        return currentValues;

    }

    private List<List<Integer>> part2C02(final List<List<Integer>> bitList, int diagnosticColumns) throws Exception {
        List<List<Integer>> currentValues = bitList;
        for (int criteriaColumn = 0; criteriaColumn < diagnosticColumns; criteriaColumn++) {
            List<Integer> greatestValues = getGreatestValueArray(currentValues, diagnosticColumns);
            List<Integer> invertedValuesForC02 = invertGreatestValues(greatestValues);
            int bit = invertedValuesForC02.get(criteriaColumn);
            currentValues = filterValuesWithBitInPosition(bit, criteriaColumn, currentValues);
            if (currentValues.size() == 1) {
                break;
            }
        }

        return currentValues;

    }

    private List<List<Integer>> filterValuesWithBitInPosition(int bit, int position, List<List<Integer>> bitList) {
        return bitList.stream().filter(innerList -> innerList.get(position) == bit).collect(Collectors.toList());
    }

    private List<Integer> getGreatestValueArray(List<List<Integer>> bitList, int diagnosticColumns) throws Exception {
        List<Integer> greatestValues = new ArrayList<>(); // 12 values, one for each column
        int greatest;
        for (int column = 0; column < diagnosticColumns; column++) {
            greatest = countZerosAndOnesReturnGreatest(bitList, column);
            greatestValues.add(greatest);
        }
        return greatestValues;
    }

    private List<Integer> invertGreatestValues(List<Integer> greatestValues) {
        List<Integer> invertedGreatestValues = new ArrayList<>();
        for (Integer integer : greatestValues) {
            if (integer.equals(0)) {
                invertedGreatestValues.add(1);
            } else {
                invertedGreatestValues.add(0);
            }
        }
        return invertedGreatestValues;
    }

    private int countZerosAndOnesReturnGreatest(List<List<Integer>> bitList, int column) throws Exception {
        int zeroCount = 0;
        int oneCount = 0;

        for (List<Integer> bits : bitList) {
            int value = bits.get(column);
            if (value == 0) {
                zeroCount++;
            } else if (value == 1) {
                oneCount++;
            } else { // they are equal
                throw new Exception("This shouldn't happen");
            }
        }

        if (zeroCount == oneCount) {
            return 1;
        } else if (zeroCount > oneCount) {
            return 0;
        } else {
            return 1;
        }
    }


}
