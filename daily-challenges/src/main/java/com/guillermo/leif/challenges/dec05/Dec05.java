package com.guillermo.leif.challenges.dec05;

import com.guillermo.leif.challenges.Challenge;
import com.guillermo.leif.inputReaders.xyGraph.XyCoordinate;
import com.guillermo.leif.inputReaders.xyGraph.XyGraphReader;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.KeyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
@Challenge5
public class Dec05 implements Challenge {

    private final XyGraphReader xyGraphReader;
    private final OceanFloor oceanFloor;

    @Autowired
    public Dec05(XyGraphReader xyGraphReader, OceanFloor oceanFloor) {
        this.xyGraphReader = xyGraphReader;
        this.oceanFloor = oceanFloor;
    }

    @Override
    public void solveProblem() throws Exception {
        final String filePath = "daily-challenges/src/main/resources/inputFiles/day5/puzzleInput.txt";
        final String sampleFilePath = "daily-challenges/src/main/resources/inputFiles/day5/sample1.txt";

        List<KeyValue<XyCoordinate, XyCoordinate>> coordinates = xyGraphReader.readCoordinates(filePath);
        oceanFloor.mapVentLocations(coordinates);
//        log.info("xy coordinates:\n" + coordinates);
    }


}
