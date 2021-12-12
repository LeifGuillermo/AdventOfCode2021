package com.guillermo.leif.challenges.dec08;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guillermo.leif.challenges.Challenge;
import com.guillermo.leif.inputReaders.signalPatternNotes.UniquePatternOutputCouple;
import com.guillermo.leif.inputReaders.signalPatternNotes.SignalPatternNoteReader;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
@Challenge8
public class Dec08 implements Challenge {

    private SignalPatternNoteReader signalPatternNoteReader;

    @Autowired
    public Dec08(SignalPatternNoteReader signalPatternNoteReader) {
        this.signalPatternNoteReader = signalPatternNoteReader;
    }

    @Override
    public void solveProblem() throws Exception {
        final String sampleFilePath = "daily-challenges/src/main/resources/inputFiles/day8/sample1.txt";
        final String filePath = "daily-challenges/src/main/resources/inputFiles/day8/puzzleInput.txt";

        log.info("Day 8");

//        calculatePart1(filePath);
        Integer sum = signalPatternNoteReader.getFourDigitValuesSum(filePath);
        log.info("SUM: " + sum);

    }

    private void calculatePart2(String filePath) throws IOException {
        List<UniquePatternOutputCouple> patternOutputCouples = signalPatternNoteReader.getAllPatternOutputCouples(filePath);

        // TODO
    }

    private void calculatePart1(String filePath) throws IOException {
        List<UniquePatternOutputCouple> patternOutputCouples = signalPatternNoteReader.getAllPatternOutputCouples(filePath);

        int totalCount = 0;
        for (UniquePatternOutputCouple patternOutputCouple : patternOutputCouples) {
            patternOutputCouple.setUniquePatternSizes();
            totalCount += patternOutputCouple.countUniquePatternSizesInOutput();
        }

        log.info("Easy digits: " + totalCount);
    }

}
