package com.guillermo.leif.challenges.dec13;

import com.guillermo.leif.challenges.Challenge;
import com.guillermo.leif.inputReaders.xyOrigami.XyOrigamiReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;


@Slf4j
@Component
@Challenge13
public class Dec13 implements Challenge {

    private XyOrigamiReader xyOrigamiReader;

    @Autowired
    public Dec13(XyOrigamiReader xyOrigamiReader) {
        this.xyOrigamiReader = xyOrigamiReader;
    }

    @Override
    public void solveProblem() throws Exception {
        final String sampleFilePath1 = "daily-challenges/src/main/resources/inputFiles/day13/sample1.txt";
        final String filePath = "daily-challenges/src/main/resources/inputFiles/day13/puzzleInput.txt";
        log.info("Day 13");

//        calculatePart1(filePath);
        calculatePart2(filePath);
    }

    private void calculatePart1(String filePath) throws Exception {
        log.info("Part 1");
        List<Point> points = xyOrigamiReader.readCoordinates(filePath);
        log.info("Points: " + Arrays.toString(points.toArray()));
        List<AbstractMap.SimpleEntry<String, Integer>> instructions = xyOrigamiReader.getFoldInstructions(filePath);
        log.info("Instructions:\n" + Arrays.toString(instructions.toArray()));

        XyBoard xyBoard = new XyBoard(points);

        AbstractMap.SimpleEntry<String, Integer> instruction0 = instructions.get(0);

        xyBoard = xyBoard.foldBoard(instruction0.getKey(), instruction0.getValue());

        AbstractMap.SimpleEntry<String, Integer> instruction1 = instructions.get(1);

        xyBoard = xyBoard.foldBoard(instruction1.getKey(), instruction1.getValue());
    }

    private void calculatePart2(String filePath) throws Exception {
        log.info("Part 2");
        List<Point> points = xyOrigamiReader.readCoordinates(filePath);
        log.info("Points: " + Arrays.toString(points.toArray()));
        List<AbstractMap.SimpleEntry<String, Integer>> instructions = xyOrigamiReader.getFoldInstructions(filePath);
        log.info("Instructions:\n" + Arrays.toString(instructions.toArray()));

        XyBoard xyBoard = new XyBoard(points);

        for(AbstractMap.SimpleEntry<String, Integer> instruction : instructions) {
            xyBoard = xyBoard.foldBoard(instruction.getKey(), instruction.getValue());
        }
        xyBoard.drawBoard();

    }


}
