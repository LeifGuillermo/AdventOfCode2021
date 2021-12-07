package com.guillermo.leif.challenges.dec08;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guillermo.leif.challenges.Challenge;
import com.guillermo.leif.inputReaders.crabLocation.CrabLocationReader;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
@Challenge8
public class Dec08 implements Challenge {

    private CrabLocationReader crabLocationReader;

    @Autowired
    public Dec08(CrabLocationReader crabLocationReader) {
        this.crabLocationReader = crabLocationReader;
    }

    @Override
    public void solveProblem() throws Exception {
        final String filePath = "daily-challenges/src/main/resources/inputFiles/day7/puzzleInput.txt";
        final String sampleFilePath = "daily-challenges/src/main/resources/inputFiles/day7/sample1.txt";

        log.info("Day 8");
    }

}
