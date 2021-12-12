package com.guillermo.leif.challenges.dec09;

import com.guillermo.leif.challenges.Challenge;
import com.guillermo.leif.inputReaders.caveMap.CaveMapHeightReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Component
@Challenge9
public class Dec09 implements Challenge {

    private CaveMapHeightReader caveMapHeightReader;

    @Autowired
    public Dec09(CaveMapHeightReader caveMapHeightReader) {
        this.caveMapHeightReader = caveMapHeightReader;
    }

    @Override
    public void solveProblem() throws Exception {

        log.info("Day 9");

//        calculatePart1();
        calculatePart2();

    }

    private void calculatePart1() throws IOException {
        final String sampleFilePath = "daily-challenges/src/main/resources/inputFiles/day9/sample1.txt";
        final String filePath = "daily-challenges/src/main/resources/inputFiles/day9/puzzleInput.txt";

        log.info("Part 1");

        List<List<Integer>> heightMap = caveMapHeightReader.readCaveHeights(filePath);

        List<List<Location>> locationList = createLocationList(heightMap);

        List<Location> flatLocationsList = locationList.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        Integer sum = flatLocationsList.stream().map(Location::returnRiskLevelOrZeroIfNotLowPoint).reduce(0, Integer::sum);

        log.info("heightMap: " + sum);
    }

    private List<List<Location>> createLocationList(List<List<Integer>> heightMap) {
        List<List<Location>> locationList = new ArrayList<>();

        for (int y = 0; y < heightMap.size(); y++) {
            List<Location> row = new ArrayList<>();
            for (int x = 0; x < heightMap.get(0).size(); x++) {
                row.add(new Location(x, y, heightMap));
            }
            locationList.add(row);
        }
        return locationList;
    }

    private void calculatePart2() throws IOException {
        final String sampleFilePath = "daily-challenges/src/main/resources/inputFiles/day9/sample1.txt";
        final String filePath = "daily-challenges/src/main/resources/inputFiles/day9/puzzleInput.txt";
        log.info("Part 2");

        List<List<Integer>> heightMap = caveMapHeightReader.readCaveHeights(filePath);

        List<List<Location>> locationLists = createLocationList(heightMap);

        List<Integer> basinSizes = new ArrayList<>();
        for (List<Location> locationList : locationLists) {
            for (Location location : locationList) {
                Integer basinSize = location.findNumberOfBasinNodes(locationLists);
                basinSizes.add(basinSize);
            }
        }

        log.info("Number of basins: " + basinSizes.size());
        Collections.sort(basinSizes);

        Integer largest = basinSizes.get(basinSizes.size()-1);
        Integer secondLargest =  basinSizes.get(basinSizes.size()-2);
        Integer thirdLargest =  basinSizes.get(basinSizes.size()-3);

        Integer multiplied = largest * secondLargest * thirdLargest;

        log.info("Basins multiplied: " + multiplied);

    }


}
