package com.guillermo.leif.challenges.dec16;

import com.guillermo.leif.challenges.Challenge;
import com.guillermo.leif.inputReaders.xyOrigami.XyOrigamiReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@Challenge16
public class Dec16 implements Challenge {
    private static final String day = "day16";

    private XyOrigamiReader xyOrigamiReader;

    @Autowired
    public Dec16(XyOrigamiReader xyOrigamiReader) {
        this.xyOrigamiReader = xyOrigamiReader;
    }

    @Override
    public void solveProblem() throws Exception {
        final String sampleFilePath1 = "daily-challenges/src/main/resources/inputFiles/" + day + "/sample1.txt";
        final String filePath = "daily-challenges/src/main/resources/inputFiles/" + day + "/puzzleInput.txt";
        log.info(day);

        calculatePart1(sampleFilePath1);
//        calculatePart2(sampleFilePath1);
    }

    private void calculatePart1(String filePath) throws Exception {
        log.info("Part 1");

    }

    private void calculatePart2(String filePath) throws Exception {
        log.info("Part 2");


    }


}
