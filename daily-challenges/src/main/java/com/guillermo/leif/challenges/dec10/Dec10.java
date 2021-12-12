package com.guillermo.leif.challenges.dec10;

import com.guillermo.leif.challenges.Challenge;
import com.guillermo.leif.inputReaders.navigationSubsystem.NavigationSubsystemReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;


@Slf4j
@Component
@Challenge10
public class Dec10 implements Challenge {

    Integer openBrackets = 0;
    private NavigationSubsystemReader navigationSubsystemReader;

    @Autowired
    public Dec10(NavigationSubsystemReader navigationSubsystemReader) {
        this.navigationSubsystemReader = navigationSubsystemReader;
    }

    @Override
    public void solveProblem() throws Exception {
        final String sampleFilePath = "daily-challenges/src/main/resources/inputFiles/day10/sample1.txt";
        final String filePath = "daily-challenges/src/main/resources/inputFiles/day10/puzzleInput.txt";
        log.info("Day 10");

        calculatePart1(filePath);
        calculatePart2(filePath);

    }

    private void calculatePart1(String filePath) throws IOException {
        log.info("Part 1");
        List<List<String>> subSystemOutput = navigationSubsystemReader.collectSubsystemStrings(filePath);
        log.info("Subsystem Output: " + Arrays.toString(subSystemOutput.toArray()));
        log.info("Sum of errors: " + getSumOfErrors(subSystemOutput));
    }

    public long getSumOfErrors(List<List<String>> subSystemOutput) {
        List<String> currentLine;
        long sumOfErrors = 0L;
        for (List<String> line : subSystemOutput) {
            currentLine = new ArrayList<>();
            for (String currentChar : line) {
                Integer valueOfError = addCharacterToCurrentLineAndCheck(currentLine, currentChar);
                if (valueOfError > 0) {
                    sumOfErrors += valueOfError;
                    break;
                }
            }
        }
        return sumOfErrors;
    }

    public Integer addCharacterToCurrentLineAndCheck(List<String> currentLine, String character) {
        if (Objects.equals(character, ")")) {
            if (!currentLine.get(currentLine.size() - 1).equals("(")) {
                log.info(String.format("Expected %s but found %s", currentLine.get(currentLine.size() - 1), character));
                return 3;
            } else {
                currentLine.remove(currentLine.size() - 1);
                return 0;
            }
        } else if (Objects.equals(character, "]")) {
            if (!currentLine.get(currentLine.size() - 1).equals("[")) {
                log.info(String.format("Expected %s but found %s", currentLine.get(currentLine.size() - 1), character));
                return 57;
            } else {
                currentLine.remove(currentLine.size() - 1);
                return 0;
            }
        } else if (Objects.equals(character, "}")) {
            if (!currentLine.get(currentLine.size() - 1).equals("{")) {
                log.info(String.format("Expected %s but found %s", currentLine.get(currentLine.size() - 1), character));
                return 1197;
            } else {
                currentLine.remove(currentLine.size() - 1);
                return 0;
            }
        } else if (Objects.equals(character, ">")) {
            if (!currentLine.get(currentLine.size() - 1).equals("<")) {
                log.info(String.format("Expected %s but found %s", currentLine.get(currentLine.size() - 1), character));
                return 25137;
            } else {
                currentLine.remove(currentLine.size() - 1);
                return 0;
            }
        }
        currentLine.add(character);
        return 0;
    }

    private void calculatePart2(String filePath) throws IOException {
        log.info("Part 2");
        List<List<String>> subSystemOutput = navigationSubsystemReader.collectSubsystemStrings(filePath);
        log.info("Subsystem Output: " + Arrays.toString(subSystemOutput.toArray()));


        long sumOfErrors = 0L;
        List<List<String>> incompleteLines = filterIncompleteLines(subSystemOutput);
        log.info("Incomplete lines: " + Arrays.toString(incompleteLines.toArray()));
        log.info("Incomplete lines size: " + incompleteLines.size());
        long middleScore = calculateIncompleteLineScoreSum(incompleteLines);
        log.info("Middle Score: " + middleScore);
    }

    private Long calculateIncompleteLineScoreSum(List<List<String>> incompleteLines) {

        List<Long> lineScores = new ArrayList<>();
        for(List<String> line : incompleteLines) {
            lineScores.add(calculateSingleIncompleteLineScore(line));
        }

        Collections.sort(lineScores);
        int middleIndex = lineScores.size() / 2;
        return lineScores.get(middleIndex);
    }

    private Long calculateSingleIncompleteLineScore(List<String> line) {
        Long sum = 0L;
        for (int i = line.size() - 1; i >= 0; i--) {
            sum *= 5;
            String characterToClose = line.get(i);
            if (characterToClose.equals("(")) {
                sum += 1;
            }
            if (characterToClose.equals("[")) {
                sum += 2;
            }
            if (characterToClose.equals("{")) {
                sum += 3;
            }
            if (characterToClose.equals("<")) {
                sum += 4;
            }
        }
        return sum;
    }

    private List<List<String>> filterIncompleteLines(List<List<String>> subSystemOutput) {
        List<String> currentLine;
        List<List<String>> incompleteLines = new ArrayList<>();

        for (List<String> line : subSystemOutput) {
            currentLine = new ArrayList<>();
            int nonErrorCount = 0;
            for (String currentChar : line) {
                Integer valueOfError = addCharacterToCurrentLineAndCheck(currentLine, currentChar);
                if (valueOfError > 0) {
                    break;
                } else {
                    nonErrorCount++;
                }
            }
            if (line.size() == nonErrorCount) {
                incompleteLines.add(currentLine);
            }
        }
        return incompleteLines;
    }


}
