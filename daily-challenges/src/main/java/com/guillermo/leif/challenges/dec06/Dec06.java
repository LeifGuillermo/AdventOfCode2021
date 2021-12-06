package com.guillermo.leif.challenges.dec06;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guillermo.leif.challenges.Challenge;
import com.guillermo.leif.challenges.dec05.Challenge5;
import com.guillermo.leif.challenges.dec05.OceanFloor;
import com.guillermo.leif.inputReaders.xyGraphReader.XyGraphReader;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
@Challenge5
public class Dec06 implements Challenge {



    @Autowired
    public Dec06(XyGraphReader xyGraphReader, OceanFloor oceanFloor) {

    }

    @Override
    public void solveProblem() throws Exception {
        final String filePath = "daily-challenges/src/main/resources/inputFiles/day6/puzzleInput.txt";
        final String sampleFilePath = "daily-challenges/src/main/resources/inputFiles/day6/sample1.txt";


    }


}
