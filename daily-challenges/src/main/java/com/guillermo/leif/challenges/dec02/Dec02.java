package com.guillermo.leif.challenges.dec02;

import com.guillermo.leif.challenges.Challenge;
import com.guillermo.leif.common.Submarine;
import com.guillermo.leif.inputReaders.AllLineReader;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.KeyValue;
import org.javatuples.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Challenge2
public class Dec02 implements Challenge {
    private AllLineReader allLineReader;

    @Autowired
    public Dec02(AllLineReader allLineReader) {
        this.allLineReader = allLineReader;
    }

    @Override
    public void solveProblem() throws Exception {
        final String sampleFilePath = "daily-challenges/src/main/resources/inputFiles/day2/sample1.txt";
        final String filePath = "daily-challenges/src/main/resources/inputFiles/day2/subInstructions.txt";
        List<Tuple> tuples = allLineReader.readAllLinesFromFileAsTuples(sampleFilePath);
        Submarine submarine = new Submarine();

        for (Tuple tuple : tuples) {
            submarine.callDirectionFromTuple((KeyValue) tuple);
            log.debug(submarine.toString());
        }

        log.info("SUBMARINE RESULTS: " + submarine.toString());
        log.info(String.format("Coordinates Multiplied: %s", submarine.getDepth() * submarine.getHorizontalPosition()));
    }


}
