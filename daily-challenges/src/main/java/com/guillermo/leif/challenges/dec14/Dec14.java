package com.guillermo.leif.challenges.dec14;

import com.guillermo.leif.challenges.Challenge;
import com.guillermo.leif.inputReaders.polymerTemplate.PolymerTemplateReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Slf4j
@Component
@Challenge14
public class Dec14 implements Challenge {
    private static final String day = "day14";

    private PolymerTemplateReader polymerTemplateReader;

    @Autowired
    public Dec14(PolymerTemplateReader polymerTemplateReader) {
        this.polymerTemplateReader = polymerTemplateReader;
    }

    @Override
    public void solveProblem() throws Exception {
        final String sampleFilePath1 = "daily-challenges/src/main/resources/inputFiles/" + day + "/sample1.txt";
        final String filePath = "daily-challenges/src/main/resources/inputFiles/" + day + "/puzzleInput.txt";
        log.info(day);

//        calculatePart1(sampleFilePath1);
        calculatePart2(filePath);
    }

    private void calculatePart1(String filePath) throws Exception {
        log.info("Part 1");
        String templateString = polymerTemplateReader.readPolymerTemplateString(filePath);
        Map<String, String> instructions = polymerTemplateReader.readTemplateInstructions(filePath);

        int steps = 10;
        for (int i = 0; i < steps; i++) {
            templateString = oneStep(templateString, instructions, i + 1);
        }

        int smallestCount = findNumberOfLeastCommonElement(templateString);
        int largestCount = findNumberOfMostCommonElement(templateString);

        log.info("smallest count: " + smallestCount);
        log.info("largest count: " + largestCount);
        log.info("difference: " + (largestCount - smallestCount));
    }

    private void calculatePart2(String filePath) throws Exception {
        log.info("Part 2");
        String templateString = polymerTemplateReader.readPolymerTemplateString(filePath);
        Map<String, String> instructions = polymerTemplateReader.readTemplateInstructions(filePath);

        int numSteps = 40;
        CharCounter counter = new CharCounter();
        counter.initializeCounts(templateString);
        for (int i = 1; i <= numSteps; i++) {
            counter.updatePairsAndChars(instructions, i);
        }
        counter.logCharCounts();
        counter.logPairs();
    }


    Set<String> createSetOfAllElements(String templateString) {
        String[] allElements = templateString.split("");
        return new HashSet<>(List.of(allElements));
    }

    private int findNumberOfLeastCommonElement(String templateString) {
        Set<String> elementSet = createSetOfAllElements(templateString);
        int smallestCount = Integer.MAX_VALUE;
        for (String character : elementSet) {
            int count = StringUtils.countMatches(templateString, character);
            if (count < smallestCount) {
                smallestCount = count;
            }
        }
        return smallestCount;
    }

    private int findNumberOfMostCommonElement(String templateString) {
        Set<String> elementSet = createSetOfAllElements(templateString);
        int largestCount = Integer.MIN_VALUE;
        for (String character : elementSet) {
            int count = StringUtils.countMatches(templateString, character);
            if (count > largestCount) {
                largestCount = count;
            }
        }
        return largestCount;
    }

    private String oneStep(String templateString, Map<String, String> instructions, int step) {
        String newString = templateString;
        for (int i = 0; i < templateString.length() - 1; i++) {
            String compareString = templateString.substring(i, i + 2);
            if (instructions.containsKey(compareString)) {
                newString = updateTemplate(newString, instructions.get(compareString), i + 1);
                i++;
            }
            templateString = newString;
        }
        log.info("Step " + step);
        log.info("String Length: " + templateString.length());
        return newString;
    }

    private String updateTemplate(String existingString, String replacement, int index) {
        return existingString.substring(0, index) + replacement + existingString.substring(index);
    }



}
