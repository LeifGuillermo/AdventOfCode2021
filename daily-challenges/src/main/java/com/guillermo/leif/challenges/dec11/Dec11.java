package com.guillermo.leif.challenges.dec11;

import com.guillermo.leif.challenges.Challenge;
import com.guillermo.leif.inputReaders.IntegerMapReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Slf4j
@Component
@Challenge11
public class Dec11 implements Challenge {

    Integer openBrackets = 0;
    private IntegerMapReader integerMapReader;

    @Autowired
    public Dec11(IntegerMapReader integerMapReader) {
        this.integerMapReader = integerMapReader;
    }

    @Override
    public void solveProblem() throws Exception {
        final String sampleFilePath = "daily-challenges/src/main/resources/inputFiles/day11/sample1.txt";
        final String filePath = "daily-challenges/src/main/resources/inputFiles/day11/puzzleInput.txt";
        log.info("Day 11");

        calculatePart1(filePath);
        calculatePart2(filePath);

    }

    private void calculatePart1(String filePath) throws IOException {
        log.info("Part 1");
    }

    private void calculatePart2(String filePath) throws IOException {
        log.info("Part 2");

    }
}
