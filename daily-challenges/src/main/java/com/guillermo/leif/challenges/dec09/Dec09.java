package com.guillermo.leif.challenges.dec09;

import com.guillermo.leif.challenges.Challenge;
import com.guillermo.leif.inputReaders.signalPatternNotes.SignalPatternNoteReader;
import com.guillermo.leif.inputReaders.signalPatternNotes.UniquePatternOutputCouple;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;


@Slf4j
@Component
@Challenge9
public class Dec09 implements Challenge {

    private SignalPatternNoteReader signalPatternNoteReader;

    @Autowired
    public Dec09(SignalPatternNoteReader signalPatternNoteReader) {
        this.signalPatternNoteReader = signalPatternNoteReader;
    }

    @Override
    public void solveProblem() throws Exception {
        final String sampleFilePath = "daily-challenges/src/main/resources/inputFiles/day89/sample1.txt";
        final String filePath = "daily-challenges/src/main/resources/inputFiles/day9/puzzleInput.txt";

        log.info("Day 9");

        calculatePart1(sampleFilePath);
        calculatePart2(sampleFilePath);

    }

    private void calculatePart2(String filePath) throws IOException {

        log.info("Part 2");
    }

    private void calculatePart1(String filePath) throws IOException {

        log.info("Part 1");
    }

}
